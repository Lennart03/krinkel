class UnitsController {
    constructor(KrinkelService) {
        this.KrinkelService = KrinkelService;
    }

    $onInit() {
        this.KrinkelService.getVerbonden().then((results) => {
            this.verbonden = results;
            this.getParticipantsForUnit(this.verbonden);
        });
    }

    openVerbond(verbond) {
        this.KrinkelService.getGewestenForVerbond(verbond.stamnummer).then((results) => {

            if (results.onderliggende_stamnummers != 0) {
                this.unitLevel = verbond.naam;
                this.verbonden = results.onderliggende_stamnummers;
                this.verbond = results;

                this.getParticipantsForUnit(this.verbonden);
            }
        });
    }

    getParticipantsForUnit(verbonden) {
        angular.forEach(verbonden, (value, index) => {
            this.KrinkelService.getParticipantsForUnit(value.stamnummer).then((results) => {
                value.amountParticipants = results[0];
                value.amountVolunteers = results[1];
            })
        });
    }

    goToPrevious(verbond) {
        this.unitLevel = null;

        if (verbond.bovenliggende_stamnummer != null) {
            this.openVerbond(verbond.bovenliggende_stamnummer);
        }
        else {
            this.KrinkelService.getVerbonden().then((results) => {
                this.verbonden = results;
                this.getParticipantsForUnit(this.verbonden);
            });
        }
    }
}

export var UnitsComponent = {
    template: require('./units.html'),
    controller: UnitsController
};

UnitsComponent.$inject = ['KrinkelService'];
