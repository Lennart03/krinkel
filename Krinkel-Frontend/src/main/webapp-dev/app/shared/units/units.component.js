class UnitsController {
    constructor(KrinkelService, AuthService, MapperService) {
        this.KrinkelService = KrinkelService;
        this.AuthService = AuthService;
        this.MapperService = MapperService;
    }

    $onInit() {
        this.KrinkelService.getVerbonden().then((results) => {
            this.verbonden = results;
            this.getParticipantsForUnit(this.verbonden);
        });
    }

    openExtraInfo(verbond) {
        this.openVerbond(verbond);

        if (!verbond.stamnummer.endsWith("00") && this.AuthService.getUserRole() === 'ADMIN') {
            this.openUsers(verbond);
        }
    }



    openUsers(verbond) {
        this.unitLevel = verbond.naam;
        this.userDetails = true;
        this.participantDetails = true;
        this.volunteerDetails = false;
        this.participants = [];
        this.volunteers = [];
        this.KrinkelService.getParticipantsOfUnit(verbond.stamnummer).then((results) => {
            results.forEach((r) => {
                r.participant = "Deelnemer";
                r.eatinghabbit = this.MapperService.mapEatingHabbit(value.eatinghabbit);
                r.campGround = this.MapperService.mapCampground(value.campGround);
                this.participants.push(r);
            });
        });
        this.KrinkelService.getVolunteersOfUnit(verbond.stamnummer).then((results) => {
            results.forEach((r) => {
                r.participant = "Vrijwilliger";
                r.function.preset = this.MapperService.mapVolunteerFunction(value.function.preset);
                r.eatinghabbit = this.MapperService.mapEatingHabbit(value.eatinghabbit);
                r.campGround = this.MapperService.mapCampground(value.campGround);
                if (r.function.preset == "CUSTOM"){
                    r.function.preset = r.function.other;
                }
                this.volunteers.push(r);
            })
        });

            // angular.forEach(results[0], (value, index) => {
            //     value.participant = "Deelnemer";
            //     value.eatinghabbit = this.MapperService.mapEatingHabbit(value.eatinghabbit);
            //     value.campGround = this.MapperService.mapCampground(value.campGround);
            //     this.participants.push(value);
            // });
        //     angular.forEach(results[1], (value, index) => {
        //         value.participant = "Vrijwilliger";
        //         value.function.preset = this.MapperService.mapVolunteerFunction(value.function.preset);
        //         value.eatinghabbit = this.MapperService.mapEatingHabbit(value.eatinghabbit);
        //         value.campGround = this.MapperService.mapCampground(value.campGround);
        //         if (value.function.preset == "CUSTOM"){
        //             value.function.preset = value.function.other;
        //         }
        //         this.volunteers.push(value);
        //     });
        // });
    }

    openVerbond(verbond) {
        this.KrinkelService.getGewestenForVerbond(verbond.stamnummer).then((results) => {
            this.unitLevel = verbond.naam;
            this.verbonden = results.onderliggende_stamnummers;
            this.verbond = results;

            if (results.onderliggende_stamnummers != 0) {
                this.getParticipantsForUnit(this.verbonden);
            }
        });
    }

    getParticipantsForUnit(verbonden) {
        // angular.forEach(verbonden, (value, index) => {
        //     this.KrinkelService.getParticipantsForUnit(value.stamnummer).then((results) => {
        //         value.amountParticipants = results[0];
        //         value.amountVolunteers = results[1];
        //     })
        // });
        verbonden.forEach((r) => {
            this.KrinkelService.getParticipantsForUnit(r.stamnummer).then((results) => {
            })
        })
    }

    goToPrevious(verbond) {
        this.unitLevel = null;
        this.userDetails = false;
        this.participantDetails = false;
        this.volunteerDetails = false;

        if (verbond.bovenliggende_stamnummer != null) {
            this.openVerbond(verbond.bovenliggende_stamnummer);
        } else if (!verbond.stamnummer.endsWith("000")) {
            this.openVerbond(verbond.stamnummer);
        } else {
            this.KrinkelService.getVerbonden().then((results) => {
                this.verbonden = results;
                this.getParticipantsForUnit(this.verbonden);
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
}

export var UnitsComponent = {
    template: require('./units.html'),
    controller: UnitsController
};

UnitsComponent.$inject = ['KrinkelService', 'AuthService', 'MapperService'];
