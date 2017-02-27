class GewestenController {
    constructor(KrinkelService, $route, AuthService, $location, $routeParams, MapperService) {
        this.KrinkelService = KrinkelService;
        this.$route = $route;
        this.AuthService = AuthService;
        this.MapperService = MapperService;
        this.$location = $location;
        this.verbondNr = $routeParams.verbondNr;
        this.verbondNaam = $routeParams.verbondNaam;
        this.showVolunteers = false;
        this.initSelectPayStatus();
        //console.log(this.lolo + ' gewesten.component.js says hi! ' + this.verbondNaam);
    }

    $onInit() {
        this.user = this.AuthService.getLoggedinUser();
        this.userRole = this.user.role;
        this.userRoles = this.user.roles;
        this.canSeeDeelnemersAantallen = this.checkCanSeeAantallen();
        this.canSeeMedewerkers = this.checkCanSeeMedewerkers();
        console.log('===== GEWESTEN ====')
        console.log('userRoles');
        console.log(this.userRoles);

        this.KrinkelService.getGewestenList(this.verbondNr).then((results) => {
            //console.log(results);
            this.gewesten = results;
            if(this.verbondNr !== 'OTHERS') {
                this.filterGewesten();
            } else{
                console.log('Show gewesten of OTHERS')
            }
        });

        this.volunteers = [];

        this.KrinkelService.getVolunteersListByCampground(this.verbondNaam).then((results) => {
            console.log('Result from this.KrinkelService.getVolunteersListByCampground(this.verbondNaam)');
            console.log(results);
            results.forEach((r) => {
                r.participant = "Vrijwilliger";
                r.function.preset = this.MapperService.mapVolunteerFunction(r.function.preset);
                r.eatinghabbit = this.MapperService.mapEatingHabbit(r.eatinghabbit);
                r.campGround = this.MapperService.mapCampground(r.campGround);
                if (r.function.preset == "CUSTOM"){
                    r.function.preset = r.function.other;
                }
                this.volunteers.push(r);
            })
        },
            () => {
                console.log('Something wrong met getVolunteersListByCampground(this.verbondNaam)')
        });
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
            if(this.userRoles[i].adNumber.substring(0,3) === 'NAT'
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
            if(this.userRoles[i].adNumber.substring(0,3) === 'NAT' || this.userRoles[i].role === 'verbond' || this.userRoles[i].role === 'gewest'){
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
            // Verbond role kan enkel zijn verbond zien dus hiermee ook aantallen van zijn verbond laten zien!!!
            if(this.userRoles[i].role === 'verbond' && this.userRoles[i].adNumber === this.verbondNr){
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
            if(this.canSee(this.gewesten[i].stamnummer)) {
                temp.push(this.gewesten[i]);
            }
        }
        this.gewesten = [];
        for(var j = 0; j < temp.length; j++){
            this.gewesten.push(temp[j]);
        }
    }

    canSee(gewestNr){
        var thiz = this;
        console.log('######### GEWESTEN ######')
        console.log('#### cansee('+gewestNr+') ####');
        console.log('#########################')
        if( Object.prototype.toString.call( this.userRoles ) === '[object Array]' ){ // array check
            for(var i = 0; i < this.userRoles.length; i++){
                var roleAndAdNumber = this.userRoles[i];
                console.log('* ' + roleAndAdNumber.role + ' : ' + roleAndAdNumber.adNumber);
                if(this.checkForRoleAndAdNumber(roleAndAdNumber, gewestNr)){
                    return true;
                }
            }
        }
        return false;
    }

    checkForRoleAndAdNumber(roleAndAdNumber, gewestNr){
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
                if(nr.substring(0,nrLettersGewest) === gewestNr.substring(0,nrLettersGewest) || this.checkForOthers(nr, gewestNr)){
                    console.log('### TRUE groep met nr: ' + nr);
                    return true;
                } else {
                    console.log('### FALSE groep met nr: ' + nr);
                    return false;}
                // return (nr.substring(0,nrLetters) === gewestNr.substring(0,nrLetters)) ? true : false;
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

    checkForOthers(nr, gewestNr){
        if(gewestNr === 'OTHERS') {
            var normalBeginnings = ['AG', 'AJ', 'AM',
                'BG', 'BJ', 'BM', 'INT', 'KG', 'KJ', 'KM', 'LEG', 'LEJ', 'LEM',
                'LG', 'LJ', 'LM', 'MG', 'MJ', 'MM', 'NAT', 'OG', 'OJ', 'OM', 'WG', 'WJ', 'WM'];
            if (!normalBeginnings.includes(nr.substr(0, 2))) {
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

GewestenComponent.$inject = ['KrinkelService', '$route', 'AuthService', '$location', '$routeParams', 'MapperService'];
