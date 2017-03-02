class GewestenController {
    constructor(KrinkelService, $route, AuthService, $location, $routeParams, MapperService, $q) {
        this.KrinkelService = KrinkelService;
        this.$route = $route;
        this.AuthService = AuthService;
        this.MapperService = MapperService;
        this.$location = $location;
        this.$q = $q;
        this.verbondNr = $routeParams.verbondNr;
        this.verbondNaam = $routeParams.verbondNaam;
        this.showVolunteers = false;
        this.initSelectPayStatus();
        //console.log(this.lolo + ' gewesten.component.js says hi! ' + this.verbondNaam);
    }

    $onInit() {
        this.gettingData = true;
        this.nationaalStamnummers = ['4AF', '4AG', '4AL', '4CF', '4CR', '4KL', '4WB', '4WJ', '4WK', '5AA', '5CA', '5CC', '5CD', '5CG', '5CJ', '5CL', '5CP', '5CV', '5DI', '5IG', '5IP', '5IT', '5KA', '5PA', '5PG', '5PM', '5PP', '5PV', '5RA', '5RD', '5RI', '5RP', '5RV', '5RW', '5SB', '5UG', '5UK', '5UL', '6KV', '7WD', '7WH', '7WK', '7WO', '7WW', '8BB', '8BC', '8BH', '8BR', '8BZ', '8DB', '8HD', '8HH', '8HK', '8HO', '8HW', '9KO'];
        this.internationaalStamnummer = '5DI';
        this.user = this.AuthService.getLoggedinUser();
        this.userRole = this.user.role;
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

            for (var j = 0; j < value.length; j++) {
                if (typeof value[j].groepstamnummer !== null) {
                    self.userRoles[value[j].index].chiroGroepGewestVerbond = value[j];
                }
            }

            self.canSeeDeelnemersAantallen = self.checkCanSeeAantallen();
            self.canSeeMedewerkers = self.checkCanSeeMedewerkers();
            console.log('===== GEWESTEN ====');
            console.log('userRoles');
            console.log(self.userRoles);

            self.KrinkelService.getGewestenList(self.verbondNr).then((results) => {
                //console.log(results);
                self.gewesten = results;
                if (self.verbondNr !== 'OTHERS') {
                    self.filterGewesten();
                } else {
                    console.log('Show gewesten of OTHERS')
                }
                // console.log('self.gewesten.length ' + self.gewesten.length);
                if(self.gewesten.length > 0) {
                    // console.log(' > 0 ');
                    self.lastGewest = self.gewesten[self.gewesten.length - 1];
                } else {
                    self.gettingData = false;
                }
            });

            self.volunteers = [];

            self.KrinkelService.getVolunteersListByCampground(self.verbondNaam).then((results) => {
                console.log('Result from self.KrinkelService.getVolunteersListByCampground(self.verbondNaam)');
                console.log(results);
                results.forEach((r) => {
                    r.participant = "Vrijwilliger";
                    r.function.preset = self.MapperService.mapVolunteerFunction(r.function.preset);
                    r.eatinghabbit = self.MapperService.mapEatingHabbit(r.eatinghabbit);
                    r.campGround = self.MapperService.mapCampground(r.campGround);
                    if (r.function.preset == "CUSTOM") {
                        r.function.preset = r.function.other;
                    }
                    self.volunteers.push(r);
                });
                self.volunteersLength = 0;
                if(self.userRole === 'ADMIN'){
                    for(var i = 0; i < self.volunteers.length; i++){
                        if(self.volunteers[i].status !== 'CANCELLED') {
                            console.log('volunteersLength for admin');
                            self.volunteersLength += 1;
                        }
                    }
                } else if(self.userRole !== 'ADMIN'){
                    for(var i = 0; i < self.volunteers.length; i++){
                        if(self.volunteers[i].status === 'CONFIRMED') {
                            console.log('volunteersLength for not admin => so international/national');
                            self.volunteersLength += 1;
                        }
                    }
                }
            },
            () => {
                console.log('Something wrong met getVolunteersListByCampground(self.verbondNaam)')
            });
        });
    }

    checkForLastGewest(gewestNr){
        if(gewestNr === this.lastGewest.stamnummer){
            this.gettingData = false;
            var tableGewesten = document.getElementById("tableGewesten");
            tableGewesten.style.display = "inline";
        }
        return true;
    }

    checkIfNationaal(stamnummer){
        return this.nationaalStamnummers.indexOf(stamnummer) > -1;
    }
    checkForNationaalToSeeNrOfMedewerkers(){
        for(var i = 0; i < this.userRoles.length; i++){
            if(this.checkIfNationaal(this.userRoles[i].adNumber)){
                return true;
            }
        }
        return false;
    }

    checkIfInternationaal(stamnummer){
        return this.internationaalStamnummer === stamnummer;
    }

    /**
     * FILTERING for access control
     */
    checkCanSeeAantallen2(gewestNr){
        if(this.userRole === 'ADMIN'){
            return true;
        }
        for(var i = 0; i < this.userRoles.length; i++){
            // Verbond role kan enkel zijn verbond zien dus hiermee ook aantallen van zijn verbond laten zien!!!
            if(this.checkIfNationaal(this.userRoles[i].adNumber)
                    || (this.userRoles[i].role === 'verbond' && this.verbondNr === this.userRoles[i].adNumber)
                    || (this.userRoles[i].role === 'gewest' && this.userRoles[i].adNumber === gewestNr)){
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
            if(this.checkIfNationaal(this.userRoles[i].adNumber) ||
                this.userRoles[i].role === 'verbond' ||
                this.userRoles[i].role === 'gewest'){
                return true;
            }
        }
        return false;
    }

    checkCanSeeMedewerkers(){
        if(this.userRole === 'ADMIN'){
            return true;
        }
        for(var i = 0; i < this.userRoles.length; i++){
            // Verbond mag primaire gegevens van medewerkers zien
            if(this.userRoles[i].role === 'verbond' && this.userRoles[i].adNumber === this.verbondNr){
                return true;
            }
            // Internationaal mag medewerkers zien binnen internationaal met verbondNr INT
            if(this.verbondNr === 'INT' && this.checkIfInternationaal(this.userRoles[i].adNumber)){
                return true;
            }
        }
        return false;
    }

    filterGewesten(){
        if(this.userRole === 'ADMIN'){
            return;
        }
        var temp = [];
        for(var i = 0; i < this.gewesten.length; i++){
            if(this.canSee(this.gewesten[i])) {
                temp.push(this.gewesten[i]);
            }
        }
        this.gewesten = [];
        for(var j = 0; j < temp.length; j++){
            this.gewesten.push(temp[j]);
        }
    }

    canSee(gewest){
        var gewestNr = gewest.stamnummer;
        var thiz = this;
        console.log('######### GEWESTEN ######')
        console.log('#### cansee('+gewestNr+') ####');
        console.log('#########################')
        if( Object.prototype.toString.call( this.userRoles ) === '[object Array]' ){ // array check
            for(var i = 0; i < this.userRoles.length; i++){
                var roleAndAdNumber = this.userRoles[i];
                console.log('* ' + roleAndAdNumber.role + ' : ' + roleAndAdNumber.adNumber);
                if(this.checkForRoleAndAdNumber(roleAndAdNumber, gewest)){
                    return true;
                }
            }
        }
        return false;
    }

    checkForRoleAndAdNumber(roleAndAdNumber, gewest){
        var gewestNr = gewest.stamnummer; //TODO maybe checken met gewest.upper
        var nr = roleAndAdNumber.adNumber;
        // Nr of letters to check => in case of leuven it is 3, otherwise 2
        //      ==> want to check the first letters by which the verbond can be identified
        var nrLettersVerbond = (gewestNr.substring(0,3) === 'LEG') ? 3 : 2;
        // Nr of letters to check => in case of leuven it is 5, otherwise 4
        //      ==> want to check the first letters by which the gewest can be identified
        var nrLettersGewest = (gewestNr.substring(0,3) === 'LEG') ? 5 : 4;
        switch (roleAndAdNumber.role){
            case 'verbond':
                if(nr.substring(0,nrLettersVerbond) === gewestNr.substring(0,nrLettersVerbond)){
                    console.log('### TRUE verbond met nr: ' + nr);
                    return true;
                } else{
                    console.log('### FALSE verbond met nr: ' + nr);
                    return false;
                }
                // return (nr === gewestNr) ? true : false;
                break;
            case 'gewest':
                if(nr === gewestNr){
                    console.log('### TRUE gewest met nr: ' + nr);
                    return true;
                } else {
                    console.log('### FALSE gewest met nr: ' + nr);
                    return false;}
                // return (nr.substring(0,nrLetters) === gewestNr.substring(0,nrLetters)) ? true : false;
                break;
            case 'groep':
                //TODO => mss nog aparte check voor '4WK' en '5DI' hier of in de default checken of voor dat ge gaat filteren
                var chiroGroepGewestVerbond = roleAndAdNumber.chiroGroepGewestVerbond;
                // if undefined => hoort bij others
                if((gewestNr === 'OTHERS' && typeof chiroGroepGewestVerbond === 'undefined')
                    || this.checkForOthers(nr, gewestNr)
                    || chiroGroepGewestVerbond.geweststamnummer === gewestNr
                    || (gewestNr === 'INT' && this.checkIfInternationaal(nr))
                    || this.checkIfNationaal(nr)){
                // if(nr.substring(0,nrLettersGewest) === gewestNr.substring(0,nrLettersGewest) || this.checkForOthers(nr, gewestNr)){
                    console.log('### TRUE groep met nr: ' + nr);
                    return true;
                } else {
                    console.log('### FALSE groep met nr: ' + nr);
                    return false;}
                // return (nr.substring(0,nrLetters) === gewestNr.substring(0,nrLetters)) ? true : false;
                break;
            default:
                return false;
        }
        return false;
    }

    checkForOthers(nr, gewestNr){
        if(gewestNr === 'OTHERS') {
            var normalBeginnings = ['AG', 'AJ', 'AM',
                'BG', 'BJ', 'BM', 'INT', 'KG', 'KJ', 'KM', 'LEG', 'LEJ', 'LEM',
                'LG', 'LJ', 'LM', 'MG', 'MJ', 'MM', 'NAT', 'OG', 'OJ', 'OM', 'WG', 'WJ', 'WM'];
            if (!normalBeginnings.includes(nr.substr(0, 2)) && !this.checkIfNationaal(nr) && !this.checkIfInternationaal(nr)) {
                return true; // Groepnr is part of others
            }
        }
        return false;
    }

    /**
     * REDIRECTING
     * @param gewestNr
     * @param gewestNaam
     */

    redirectToGroepen(gewestNr, gewestNaam) {
        //console.log('Tried to redirect via javascript to gewesten with verbondStamNummer: ' + gewestStamNr);

        this.$location.path('/groepen/' +
            gewestNr + '/' + gewestNaam);
        //groepen/:verbondNr/:verbondNaam/:gewestNr/:gewestNaam
    }

    /**
     *
     * FOR CHANGING PAYMENT STATUS AND CANCELLING PAYMENT
     *
     */

    //DONE: remove getElementById & setAttribute when 'reloading to same view' is implemented and uncomment the route.reload()
    putParticipantToCancelled(participantId) {
        if (participantId != null) {
            this.KrinkelService.putParticipantToCancelled(participantId).then((result) => {
                this.$route.reload();
            });

            // let id = document.getElementById(participantId);
            // id.setAttribute('style', 'display: none;');
        }
    }

    saveData(participantId, paymentStatus) {
        this.KrinkelService.updatePayment(participantId, paymentStatus);
    }

    initCancelModal(modalId) {
        let modalName = '#modal' + modalId;
        $(modalName).openModal();
    }

    switchShowParticipantsVolunteers() {
        this.showVolunteers = ! this.showVolunteers;
    }

    initSelectPayStatus() {
        this.statusses =  [
            { 'value': 'TO_BE_PAID', 'label': 'Onbetaald' },
            { 'value': 'PAID', 'label': 'Betaald' },
            { 'value': 'CONFIRMED', 'label': 'Bevestigd'}
        ]
    }
    /*
     To parse dates from a list of data objects
     */
    getDatesListFromList(list){
        if(list.length > 0) {
            function pad(s) {
                return (s < 10) ? '0' + s : s;
            };
            function formatDate(d){
                return [pad(d.getDate()), pad(d.getMonth()+1), d.getFullYear()].join('-');
            };
            var dates = "";
            for (var i = 0; i < list.length -1; i++) {
                var d = new Date(list[i].date);
                dates += formatDate(d) + ", ";
            }
            var d = new Date(list[list.length-1].date);
            dates += formatDate(d);
            return dates;
        }
        return "";
    }
}

export var GewestenComponent = {
    template: require('./gewesten.html'),
    controller: GewestenController
};

GewestenComponent.$inject = ['KrinkelService', '$route', 'AuthService', '$location', '$routeParams', 'MapperService', '$q'];
