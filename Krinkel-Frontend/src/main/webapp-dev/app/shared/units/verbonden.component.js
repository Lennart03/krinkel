/**
 * Created by JCPBB69 on 15/12/2016.
 */
class VerbondenController {
    constructor(KrinkelService, AuthService, $route, $location, $http, $q) {
        this.KrinkelService = KrinkelService;
        this.AuthService = AuthService;
        this.$route = $route;
        this.$location = $location;
        this.$http = $http;
        this.$q = $q;
    }

    checkIfNationaal(stamnummer){
        return this.nationaalStamnummers.indexOf(stamnummer) > -1;
    }
    checkIfInternationaal(stamnummer){
        return this.internationaalStamnummer === stamnummer;
    }

    $onInit() {
        this.gettingData = true;

        this.nationaalStamnummers = ['4AF', '4AG', '4AL', '4CF', '4CR', '4KL', '4WB', '4WJ', '4WK', '5AA', '5CA', '5CC', '5CD', '5CG', '5CJ', '5CL', '5CP', '5CV', '5DI', '5IG', '5IP', '5IT', '5KA', '5PA', '5PG', '5PM', '5PP', '5PV', '5RA', '5RD', '5RI', '5RP', '5RV', '5RW', '5SB', '5UG', '5UK', '5UL', '6KV', '7WD', '7WH', '7WK', '7WO', '7WW', '8BB', '8BC', '8BH', '8BR', '8BZ', '8DB', '8HD', '8HH', '8HK', '8HO', '8HW', '9KO'];
        this.internationaalStamnummer = '5DI';

        this.user = this.AuthService.getLoggedinUser();
        this.userRole = this.user.role;

        // TODO TODO TODO
        this.userRoles = this.user.roles;

        this.promiseList = [];

        var self = this;

        for(var i = 0; i < this.userRoles.length; i++){
            if(this.userRoles[i].role === 'groep'){
                this.promiseList.push(this.KrinkelService.getChiroGroepGewestVerbondByGroepStamNummer(this.userRoles[i].adNumber, i));
            }
        }


        this.$q.all(this.promiseList).then(function(value) {
            console.log('Result from promiseList');
            console.log(value);

            for(var j = 0; j < value.length; j++){
                if (typeof value[j].groepstamnummer !== null) {
                    self.userRoles[value[j].index].chiroGroepGewestVerbond = value[j];
                }
            }

            // self.userRoles = self.user.roles;
            self.canSeeDeelnemersAantallen = self.checkCanSeeAantallen();
            self.canSeeMedewerkersAantallen = self.checkCanSeeAantallen();
            console.log('===== VERBONDEN ====')
            console.log('userRoles');
            console.log(self.userRoles);
            // console.log('USER ROLE')
            // console.log(self.userRole);

            self.KrinkelService.getVerbondenList().then((results) => {
                self.verbonden = results;
                // console.log('VERBONDEN');
                // console.log(self.verbonden);
                //
                self.filterVerbonden();
                //
                // console.log('VERBONDEN AFTER FILTERING');
                // console.log(self.verbonden);
                // console.log('self.verbonden.length ' + self.verbonden.length);
                if(self.verbonden.length > 0) {
                    // console.log(' > 0 ');
                    self.lastVerbond = self.verbonden[self.verbonden.length - 1];
                    // console.log('lastVerbond');
                    // console.log(self.lastVerbond);
                    // console.log(self.lastVerbond.stamnummer);
                } else {
                    // console.log(' <= 0 ');
                    self.gettingData = false;
                }
            });
        });
    }

    checkForLastVerbond(verbondNr){
        if(verbondNr === this.lastVerbond.stamnummer){
            this.gettingData = false;
            var tableVerbonden = document.getElementById("tableVerbonden");
            tableVerbonden.style.display = "inline";
        }
        return true;
    }

    checkCanSeeAantallen2(verbondNr){
        if(this.userRole === 'ADMIN'){
            return true;
        }
        for(var i = 0; i < this.userRoles.length; i++){
            // Verbond role kan enkel zijn verbond zien dus hiermee ook aantallen van zijn verbond laten zien!!!
            if( this.checkIfNationaal(this.userRoles[i].adNumber)
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
            if(this.checkIfNationaal(this.userRoles[i].adNumber) || this.userRoles[i].role === 'verbond'){
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
                //TODO => mss nog aparte check voor '4WK' en '5DI' hier of in de default checken of voor dat ge gaat filteren
                var chiroGroepGewestVerbond = roleAndAdNumber.chiroGroepGewestVerbond;
                // if undefined => hoort bij others
                if((verbondNr === 'OTHERS' && typeof chiroGroepGewestVerbond === 'undefined')
                    || this.checkForOthers(nr, verbondNr)
                    || chiroGroepGewestVerbond.verbondstamnummer === verbondNr
                    || (verbondNr === 'INT' && this.checkIfInternationaal(nr))
                    || this.checkIfNationaal(nr)){
                // if(nr.substring(0,nrLetters) === verbondNr.substring(0,nrLetters) || this.checkForOthers(nr, verbondNr)){
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

    checkForOthers(nr, verbondNr){
        if(verbondNr === 'OTHERS') {
            var normalBeginnings = ['AG', 'AJ', 'AM',
                'BG', 'BJ', 'BM', 'INT', 'KG', 'KJ', 'KM', 'LEG', 'LEJ', 'LEM',
                'LG', 'LJ', 'LM', 'MG', 'MJ', 'MM', 'NAT', 'OG', 'OJ', 'OM', 'WG', 'WJ', 'WM'];
            if (!normalBeginnings.includes(nr.substr(0, 2)) && !this.checkIfNationaal(nr) && !this.checkIfInternationaal(nr)) {
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



VerbondenComponent.$inject = ['KrinkelService', 'AuthService', '$route', '$location', '$http', '$q'];

