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
        this.KrinkelService.getGewestenList(this.verbondNr).then((results) => {
            //console.log(results);
            this.gewesten = results;
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
