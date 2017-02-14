class GroepController {
    constructor(KrinkelService, $route, AuthService, $location, $routeParams, MapperService) {
        this.KrinkelService = KrinkelService;
        this.$route = $route;
        this.AuthService = AuthService;
        this.$location = $location;
        this.MapperService = MapperService;
        this.groepNr = $routeParams.groepNr;
        this.groepNaam = $routeParams.groepNaam;
        this.showParticipants = true;
        //console.log(this.lolo + 'groep.component.js says hi!');
        this.initSelectPayStatus();
    }

    $onInit() {
        this.participants = [];
        this.volunteers = [];
        this.KrinkelService.getParticipantsList(this.groepNr).then((results) => {
            results.forEach((r) => {
                r.participant = "Deelnemer";
                r.eatinghabbit = this.MapperService.mapEatingHabbit(r.eatinghabbit);
                r.campGround = this.MapperService.mapCampground(r.campGround);
                this.participants.push(r);
            });
        });
        this.KrinkelService.getVolunteersList(this.lolo).then((results) => {
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
        });
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
        this.showParticipants = ! this.showParticipants;
    }

    initSelectPayStatus() {
        this.statusses =  [
            { 'value': 'TO_BE_PAID', 'label': 'Onbetaald' },
            { 'value': 'PAID', 'label': 'Betaald' },
            { 'value': 'CONFIRMED', 'label': 'Bevestigd'}
        ]
    }

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

export var GroepComponent = {
    template: require('./groep.html'),
    controller: GroepController
};

GroepComponent.$inject = ['KrinkelService', '$route', 'AuthService', '$location', '$routeParams', 'MapperService'];
