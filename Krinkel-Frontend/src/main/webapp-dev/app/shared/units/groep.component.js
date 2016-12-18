class GroepController {
    constructor(KrinkelService, $route, AuthService, $location, $routeParams, MapperService) {
        this.KrinkelService = KrinkelService;
        this.$route = $route;
        this.AuthService = AuthService;
        this.$location = $location;
        this.MapperService = MapperService;
        this.lolo = $routeParams.groepNr;
        this.groepNaam = $routeParams.groepNaam;
        this.showParticipants = true;
        console.log(this.lolo + 'groep.component.js says hi!');
    }

    $onInit() {
        this.participants = [];
        this.volunteers = [];
        this.KrinkelService.getParticipantsList(this.lolo).then((results) => {
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

    switchShowParticipantsVolunteers() {
        this.showParticipants = ! this.showParticipants;
    }
}

export var GroepComponent = {
    template: require('./groep.html'),
    controller: GroepController
};

GroepComponent.$inject = ['KrinkelService', '$route', 'AuthService', '$location', '$routeParams', 'MapperService'];
