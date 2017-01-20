class UnitsController {
    constructor(KrinkelService, AuthService, MapperService, $route) {
        this.KrinkelService = KrinkelService;
        this.AuthService = AuthService;
        this.MapperService = MapperService;
        this.$route = $route;
        this.initSelectPayStatus();
    }

    $onInit() {
        this.KrinkelService.getVerbonden().then((results) => {
            this.verbonden = results;
        });

    }

    openExtraInfo(verbond) {
        this.openVerbond(verbond);

        if (!verbond.stamnummer.endsWith("00") || this.unitLevel) {
            this.openUsers(verbond);
        }
    }

    openUsers(verbond) {

        this.unitLevel = verbond.name;
        this.userDetails = true;
        this.participantDetails = true;
        this.volunteerDetails = false;
        this.participants = [];
        this.volunteers = [];
        this.KrinkelService.getParticipantsOfUnit(verbond.stamnummer).then((results) => {
            results.forEach((r) => {
                r.participant = "Deelnemer";
                r.eatinghabbit = this.MapperService.mapEatingHabbit(r.eatinghabbit);
                r.campGround = this.MapperService.mapCampground(r.campGround);
                this.participants.push(r);
            });
        });
        this.KrinkelService.getVolunteersOfUnit(verbond.stamnummer).then((results) => {
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

    openVerbond(verbond) {
        this.KrinkelService.getGewestenList(verbond.stamnummer).then((results) => {
            this.unitLevel = verbond.name;
            this.verbonden = results;
            this.verbond = verbond;
        });
    }

    goToPrevious(verbond) {
        this.unitLevel = null;
        this.userDetails = false;
        this.participantDetails = false;
        this.volunteerDetails = false;

        if(verbond.bovenliggende_stamnummer /*!= null*/){
            this.openVerbond(verbond.bovenliggende_stamnummer)
        } else {
            this.KrinkelService.getVerbonden().then((results) => {
                this.verbonden = results;
            });
        }
    }

    switchDetails(participant) {
        if (participant == 'participants') {
            this.participantDetails = true;
            this.volunteerDetails = false;
        } else if (participant == 'volunteers') {
            this.volunteerDetails = true;
            this.participantDetails = false;
        }
    }

    putParticipantToCancelled(participantId) {
        if (participantId != null) {
            this.KrinkelService.putParticipantToCancelled(participantId).then((result) => {
                //this.$route.reload();
            });

            let id = document.getElementById(participantId);
            id.setAttribute('style', 'display: none;');
        }
    }

    saveData(participantId, paymentStatus) {
        this.KrinkelService.updatePayment(participantId, paymentStatus);
    }

    initCancelModal(modalId) {
        let modalName = '#modal' + modalId;
        $(modalName).openModal();
    }

    initSelectPayStatus() {
        this.statusses =  [
            { 'value': 'TO_BE_PAID', 'label': 'Niet betaald' },
            { 'value': 'PAID', 'label': 'Betaald' },
            { 'value': 'CONFIRMED', 'label': 'Bevestigd'}
            ]
    }
}

export var UnitsComponent = {
    template: require('./units.html'),
    controller: UnitsController
};

UnitsComponent.$inject = ['KrinkelService', 'AuthService', 'MapperService','$route'];
