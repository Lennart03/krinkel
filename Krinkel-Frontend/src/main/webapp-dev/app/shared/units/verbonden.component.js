/**
 * Created by JCPBB69 on 15/12/2016.
 */
class VerbondenController {
    constructor(KrinkelService, AuthService, $route, $location, $http) {
        this.KrinkelService = KrinkelService;
        this.AuthService = AuthService;
        this.$route = $route;
        this.$location = $location;
        this.$http = $http;
    }

    $onInit() {
        this.user = this.AuthService.getLoggedinUser();
        this.userRole = this.user.role;
        this.userRoles = this.user.roles;
        this.canSeeDeelnemersAantallen = this.checkCanSeeAantallen();
        this.canSeeMedewerkersAantallen = this.checkCanSeeAantallen();
        console.log('===== VERBONDEN ====')
        console.log('userRoles');
        console.log(this.userRoles);

        this.gettingData = true;
        this.KrinkelService.getVerbondenList().then((results) => {
            this.verbonden = results;
            // console.log('VERBONDEN');
            // console.log(this.verbonden);
            //
            this.filterVerbonden();
            //
            // console.log('VERBONDEN AFTER FILTERING');
            // console.log(this.verbonden);
        });
        setTimeout(console.log('finished timeout'), 2000);
        this.gettingData = false;

        // console.log('USER ROLE')
        // console.log(this.userRole);
    }

    checkCanSeeAantallen2(verbondNr){
        if(this.userRole === 'ADMIN'){
            return true;
        }
        for(var i = 0; i < this.userRoles.length; i++){
            // Verbond role kan enkel zijn verbond zien dus hiermee ook aantallen van zijn verbond laten zien!!!
            if(this.userRoles[i].adNumber.substring(0,3) === 'NAT'
                || (this.userRoles[i].role === 'verbond' && this.userRoles[i].adNumber === verbondNr)){
                return true;
            }
        }
        return false;
    }

    checkCanSeeAantallen(){
        if(this.userRole === 'ADMIN'){
            return true;
        }
        for(var i = 0; i < this.userRoles.length; i++){
            // Verbond role kan enkel zijn verbond zien dus hiermee ook aantallen van zijn verbond laten zien!!!
            if(this.userRoles[i].adNumber.substring(0,3) === 'NAT' || this.userRoles[i].role === 'verbond'){
                return true;
            }
        }
        return false;
    }

    filterVerbonden(){
        if(this.userRole === 'ADMIN'){
            return;
        }
        var temp = [];
        for(var i = 0; i < this.verbonden.length; i++){
            if(this.canSee(this.verbonden[i].stamnummer)) {
                temp.push(this.verbonden[i]);
            }
        }
        this.verbonden = [];
        for(var j = 0; j < temp.length; j++){
            this.verbonden.push(temp[j]);
        }
    }

    logVerbonden(){
        console.log('VERBONDEN');
        console.log(this.verbonden)
    }

    canSee(verbondNr){
        var thiz = this;
        console.log('######## Verbond #########')
        console.log('#### cansee('+verbondNr+') ####');
        console.log('##########################')
        if( Object.prototype.toString.call( this.userRoles ) === '[object Array]' ){ // array check
            for(var i = 0; i < this.userRoles.length; i++){
                var roleAndAdNumber = this.userRoles[i];
                console.log('* ' + roleAndAdNumber.role + ' : ' + roleAndAdNumber.adNumber);
                if(this.checkForRoleAndAdNumber(roleAndAdNumber, verbondNr)){
                    return true;
                }
            }
        }
        return false;
    }

    checkForRoleAndAdNumber(roleAndAdNumber, verbondNr){
        var nr = roleAndAdNumber.adNumber;
        // Nr of letters to check => in case of leuven it is 3, otherwise 2
        //      ==> want to check the first letters by which the verbond can be identified
        var nrLetters = (verbondNr.substring(0,3) === 'LEG') ? 3 : 2;
        switch (roleAndAdNumber.role){
            case 'verbond':
                if(nr === verbondNr){
                    console.log('### TRUE verbond met nr: ' + nr);
                    return true;
                } else{
                    console.log('### FALSE verbond met nr: ' + nr);
                    return false;
                }
                // return (nr === verbondNr) ? true : false;
                break;
            case 'gewest':
                if(nr.substring(0,nrLetters) === verbondNr.substring(0,nrLetters)){
                    console.log('### TRUE gewest met nr: ' + nr);
                    return true;
                } else {
                    console.log('### FALSE gewest met nr: ' + nr);
                    return false;}
                // return (nr.substring(0,nrLetters) === verbondNr.substring(0,nrLetters)) ? true : false;
                break;
            case 'groep':
                if(nr.substring(0,nrLetters) === verbondNr.substring(0,nrLetters) || this.checkForOthers(nr, verbondNr)){
                    console.log('### TRUE groep met nr: ' + nr);
                    return true;
                } else {
                    console.log('### FALSE groep met nr: ' + nr);
                    return false;}
                // return (nr.substring(0,nrLetters) === verbondNr.substring(0,nrLetters)) ? true : false;
                break;
            default:
                if(nr.substring(0,3) === 'NAT'){
                    console.log('### TRUE Nationaal met nr: ' + nr);
                    return true;
                }
                return false;
        }
        return false;
    }

    checkForOthers(nr, verbondNr){
        if(verbondNr === 'OTHERS') {
            var normalBeginnings = ['AG', 'AJ', 'AM',
                'BG', 'BJ', 'BM', 'INT', 'KG', 'KJ', 'KM', 'LEG', 'LEJ', 'LEM',
                'LG', 'LJ', 'LM', 'MG', 'MJ', 'MM', 'NAT', 'OG', 'OJ', 'OM', 'WG', 'WJ', 'WM'];
            if (!normalBeginnings.includes(nr.substr(0, 2))) {
                return true; // Groepnr is part of others
            }
        }
        return false;
    }

    redirectToGewesten(lol, verbondNaam){
        //console.log('Tried to redirect via javascript to gewesten with verbondStamNummer: ' + lol);
        this.$location.path('/gewesten/'+ lol +'/'+ verbondNaam);
    }
}

export var VerbondenComponent = {
    template: require('./verbonden.html'),
    controller: VerbondenController
};



VerbondenComponent.$inject = ['KrinkelService', 'AuthService', '$route', '$location', '$http'];

