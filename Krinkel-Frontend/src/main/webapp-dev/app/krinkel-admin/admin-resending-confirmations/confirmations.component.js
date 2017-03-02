class ConfirmationsController {
    constructor(KrinkelService, $route, AuthService, $location, $routeParams, MapperService) {
        this.KrinkelService = KrinkelService;
        this.$route = $route;
        this.AuthService = AuthService;
        this.$location = $location;
        this.MapperService = MapperService;
    }

    $onInit() {
        this.dataIsLoaded = false;
        var self = this;

        this.KrinkelService.getParticipantsListAll().then((results) => {
            self.participants = [];
            self.nrOfParticipantsPaid = 0;
            console.log('Results from getParticipantsListAll');
            console.log(results);
            results.forEach((r) => {
                r.participant = "Deelnemer";
                r.eatinghabbit = this.MapperService.mapEatingHabbit(r.eatinghabbit);
                r.campGround = this.MapperService.mapCampground(r.campGround);
                self.participants.push(r);
                if(r.status === 'PAID'){
                    self.nrOfParticipantsPaid += 1;
                }
            });

            self.dataIsLoaded = true;
        });
    }

    resendConfirmationEmails(){
        var self = this;
        var participantsPAID = [];
        this.participants.forEach((p) => {
            if(p.status === 'PAID') {
                console.log('Participant: ' + p.firstName + ' ' + p.lastName);
                participantsPAID.push(p.adNumber);
            }
        });

        console.log('participantsPAID');
        console.log(participantsPAID);

        this.KrinkelService.resendConfirmationEmails(participantsPAID).then((results) => {
            console.log('results resendConfirmationEmails');
            console.log(results);
            if(results === true) {
                self.KrinkelService.popupMessage('De mails staan in de wachtrij om verzonden te worden', 4000, 'blue');
            } else {
                self.KrinkelService.popupMessage('Zenden van mails met bevestiginslinks is mislukt', 4000, 'orange');
            }
        });


    }
}

export var ConfirmationsComponent = {
    template: require('./confirmations.html'),
    controller: ConfirmationsController
};

ConfirmationsComponent.$inject = ['KrinkelService', '$route', 'AuthService', '$location', '$routeParams', 'MapperService'];
