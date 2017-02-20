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
        this.KrinkelService.getVerbondenList().then((results) => {
            this.verbonden = results;
            // console.log('VERBONDEN');
            // console.log(this.verbonden);
            //
            // this.filterVerbonden();
            //
            // console.log('VERBONDEN AFTER FILTERING');
            // console.log(this.verbonden);
        });
        this.user = this.AuthService.getLoggedinUser();
        this.userRole = this.user.role;
        this.userRoles = this.user.roles;

        // console.log('USER ROLE')
        // console.log(this.userRole);
        // console.log('USER ROLES');
        // console.log(this.userRoles);

    }

    filterVerbonden(){
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
        if(this.userRole === 'ADMIN'){ // TODO: here maybe + NATIONAAL
            return true;
        }
        var thiz = this;
        console.log('###############################')
        console.log('#### cansee('+verbondNr+') ####');
        console.log('###############################')
        if( Object.prototype.toString.call( this.userRoles ) === '[object Array]' ){
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
                if(nr.substring(0,nrLetters) === verbondNr.substring(0,nrLetters)){
                    console.log('### TRUE groep met nr: ' + nr);
                    return true;
                } else {
                    console.log('### FALSE groep met nr: ' + nr);
                    return false;}
                // return (nr.substring(0,nrLetters) === verbondNr.substring(0,nrLetters)) ? true : false;
                break;
            default:
                return false;
        }
        return false;
    }

    redirectToGewesten(lol, verbondNaam){
        //console.log('Tried to redirect via javascript to gewesten with verbondStamNummer: ' + lol);

        this.$location.path('/gewesten/'+ lol +'/'+ verbondNaam);

        // console.log(this.$location.url);
        // console.log(this.$location.path('/gewesten/'+verbondStamNummer));

        // this.$http.get(`/gewesten/stamNummerTest`).then((resp) => {
        //
        // });

        // this.$http.get(`/gewesten/${verbondStamNummer}`).then((resp) => {
        //
        // });
        // this.$location.path('/gewesten/${verbondStamNummer}');

    }
}

export var VerbondenComponent = {
    template: require('./verbonden.html'),
    controller: VerbondenController
};



VerbondenComponent.$inject = ['KrinkelService', 'AuthService', '$route', '$location', '$http'];

