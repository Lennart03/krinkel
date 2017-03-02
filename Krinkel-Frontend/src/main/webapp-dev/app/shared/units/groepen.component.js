class GroepenController {
    constructor(KrinkelService, $route, AuthService, $location, $routeParams) {
        this.KrinkelService = KrinkelService;
        this.$route = $route;
        this.AuthService = AuthService;
        this.$location = $location;
        this.gewestNr = $routeParams.gewestNr;
        this.gewestNaam = $routeParams.gewestNaam;
        //console.log(this.lolo + 'groepen.component.js says hi!');
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
        this.userRoles = this.user.roles;
        this.canSeeDeelnemersAantallen = this.checkCanSeeAantallen();
        console.log('===== GROEPEN ====')
        console.log('userRoles');
        console.log(this.userRoles);
        var self = this;
        this.KrinkelService.getGroepenList(this.gewestNr).then((results) => {
            //console.log(results);
            self.groepen = results;
            console.log('Ongefilterde groepen');
            console.log(self.groepen);
            if(self.gewestNr !== 'OTHERS'){
                self.filterGroepen();
            } else{
                console.log('Show groepen of OTHERS');
            }
            console.log('Gefilterde groepen');
            console.log(self.groepen);
            if(self.groepen.length > 0) {
                self.lastGroep = self.groepen[self.groepen.length - 1];
            } else {
                self.gettingData = false;
            }
        });
    }

    checkForLastGroep(groepNr){
        if(groepNr === this.lastGroep.stamnummer){
            this.gettingData = false;
            var tableGewesten = document.getElementById("tableGroepen");
            tableGroepen.style.display = "inline";
        }
        return true;
    }

    /**
     * FILTERING for access control
     */
    checkCanSeeAantallen(){
        if(this.userRole === 'ADMIN'){
            return true;
        }
        for(var i = 0; i < this.userRoles.length; i++){
            // Verbond role kan enkel zijn verbond zien dus hiermee ook aantallen van zijn verbond laten zien!!!
            if(this.checkIfNationaal(this.userRoles[i].adNumber)  || this.userRoles[i].role === 'verbond' || this.userRoles[i].role === 'gewest'){
                return true;
            }
        }
        return false;
    }

    filterGroepen(){
        if(this.userRole === 'ADMIN'){
            return;
        }
        var temp = [];
        for(var i = 0; i < this.groepen.length; i++){
            if(this.canSee(this.groepen[i])) {
                temp.push(this.groepen[i]);
            }
        }
        this.groepen = [];
        for(var j = 0; j < temp.length; j++){
            this.groepen.push(temp[j]);
        }
    }

    canSee(groep){
        var groepNr = groep.stamnummer;
        var thiz = this;
        console.log('######### GROEPEN ######')
        console.log('#### cansee('+groepNr+') ####');
        console.log('#########################')
        if( Object.prototype.toString.call( this.userRoles ) === '[object Array]' ){ // array check
            for(var i = 0; i < this.userRoles.length; i++){
                var roleAndAdNumber = this.userRoles[i];
                console.log('* ' + roleAndAdNumber.role + ' : ' + roleAndAdNumber.adNumber);
                if(this.checkForRoleAndAdNumber(roleAndAdNumber, groep)){
                    return true;
                }
            }
        }
        return false;
    }

    checkForRoleAndAdNumber(roleAndAdNumber, groep){
        var groepNr = groep.stamnummer;
        var nr = roleAndAdNumber.adNumber;
        // Nr of letters to check => in case of leuven it is 3, otherwise 2
        //      ==> want to check the first letters by which the verbond can be identified
        var nrLettersVerbond = (groepNr.substring(0,3) === 'LEG') ? 3 : 2;
        // Nr of letters to check => in case of leuven it is 5, otherwise 4
        //      ==> want to check the first letters by which the gewest can be identified
        var nrLettersGewest = (groepNr.substring(0,3) === 'LEG') ? 5 : 4;
        switch (roleAndAdNumber.role){
            case 'verbond':
                // if(nr.substring(0,nrLettersVerbond) === groepNr.substring(0,nrLettersVerbond)){
                console.log('VERBONDnr: groep.bovenliggende_stamnummer.bovenliggende_stamnummer.stamnummer');
                console.log(groep.bovenliggende_stamnummer.bovenliggende_stamnummer.stamnummer);

                if(nr === groep.bovenliggende_stamnummer.bovenliggende_stamnummer.stamnummer){
                    console.log('### TRUE verbond met nr: ' + nr);
                    return true;
                } else{
                    console.log('### FALSE verbond met nr: ' + nr);
                    return false;
                }
                // return (nr === groepNr) ? true : false;
                break;
            case 'gewest':
                //if(nr.substring(0,nrLettersGewest) === groepNr.substring(0,nrLettersGewest)){

                console.log('Gewestnr: groep.bovenliggende_stamnummer.stamnummer');
                console.log(groep.bovenliggende_stamnummer.stamnummer);
                if(nr === groep.bovenliggende_stamnummer.stamnummer){
                    console.log('### TRUE gewest met nr: ' + nr);
                    return true;
                } else {
                    console.log('### FALSE gewest met nr: ' + nr);
                    return false;}
                // return (nr.substring(0,nrLetters) === groepNr.substring(0,nrLetters)) ? true : false;
                break;
            case 'groep':
                if(nr === groepNr
                    || (groepNr === 'INT' && this.checkIfInternationaal(nr))
                    || (this.checkIfNationaal(nr))){
                //if(nr.substring(0,nrLetters) === groepNr.substring(0,nrLetters) || this.checkForOthers(nr, groepNr)){
                    console.log('### TRUE groep met nr: ' + nr + ' vs ' + groepNr);
                    return true;
                } else {
                    console.log('### FALSE groep met nr: ' + nr);
                    return false;}
                // return (nr.substring(0,nrLetters) === groepNr.substring(0,nrLetters)) ? true : false;
                break;
            default:
                return false;
        }
        return false;
    }

    //TODO REMOVE NOT USED ANYMORE
    // checkForOthers(nr, verbondNr){
    //     if(verbondNr === 'OTHERS') {
    //         var normalBeginnings = ['AG', 'AJ', 'AM',
    //             'BG', 'BJ', 'BM', 'INT', 'KG', 'KJ', 'KM', 'LEG', 'LEJ', 'LEM',
    //             'LG', 'LJ', 'LM', 'MG', 'MJ', 'MM', 'NAT', 'OG', 'OJ', 'OM', 'WG', 'WJ', 'WM'];
    //         if (!normalBeginnings.includes(nr.substr(0, 2))) {
    //             return true; // Groepnr is part of others
    //         }
    //     }
    //     return false;
    // }

    /**
     * Redirecting
     * @param groepNr
     * @param groepNaam
     */
    redirectToGroep(groepNr, groepNaam) {
        console.log('Tried to redirect to groep with groep: ' + groepNr + ' ' + groepNaam);
        //Check of de persoon in de groep zit
        var canSeeGroep = this.canDirectTo(groepNr);
        if(canSeeGroep) {
            this.$location.path('/groep/' +
                groepNr + '/' + groepNaam);
        } else{
            this.KrinkelService.popupMessage('U heeft niet de machtiging om de lijst van inschrijvingen van deze groep te bekijken.',4000,'red')
            //popupMessage(message, millis, color)
        }
    }

    canDirectTo(groepNr){
        if(groepNr === 'OTHERS'){
            return true;
        }
        var canSeeGroep = this.userRole === 'ADMIN';
        for(var i = 0; i < this.userRoles.length; i++){
            if(this.userRoles[i].adNumber === groepNr){
                return true;
            }
            if(groepNr === 'NAT' && this.checkIfNationaal(this.userRoles[i].adNumber)){
                return true;
            }
            if(groepNr === 'INT' && this.checkIfInternationaal(this.userRoles[i].adNumber)){
                return true;
            }
        }
        return canSeeGroep;
    }

    testblabllaVoorInDataSql(){
        for(var i = 0; i<gewesten.length;i++){console.log("INSERT INTO groepen (groep_naam,groep_stam_nummer,gewest_stam_nummer,gewest_naam,verbond_stam_nummer,verbond_naam) VALUES ('Groep "+gewesten[i].gewest_naam + "','"+gewesten[i].gewest_stam_nummer+"','"+gewesten[i].gewest_stam_nummer + "','"+gewesten[i].gewest_naam+"','"+gewesten[i].verbond_stam_nummer + "','"+gewesten[i].verbond_naam+"');");}
    }

    testblabllaVoorOpTEst(){
        for(var i = 0; i<gewesten.length;i++){console.log("INSERT INTO `krinkel`.`groepen` (`groep_naam`,`groep_stam_nummer`,`gewest_stam_nummer`,`gewest_naam`,`verbond_stam_nummer`,`verbond_naam`) VALUES ('Groep "+gewesten[i].gewest_naam + "','"+gewesten[i].gewest_stam_nummer+"','"+gewesten[i].gewest_stam_nummer + "','"+gewesten[i].gewest_naam+"','"+gewesten[i].verbond_stam_nummer + "','"+gewesten[i].verbond_naam+"');");}
    }
}

export var GroepenComponent = {
    template: require('./groepen.html'),
    controller: GroepenController
};

GroepenComponent.$inject = ['KrinkelService', '$route', 'AuthService', '$location', '$routeParams'];
