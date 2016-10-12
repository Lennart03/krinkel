class UnitsController {
    constructor(KrinkelService) {
        this.KrinkelService = KrinkelService;
    }

    $onInit() {
        this.KrinkelService.getVerbonden().then((results) => {
            this.verbonden = results;
        });
    }

    openVerbond(verbond) {
        console.log(verbond);
        this.KrinkelService.getGewestenForVerbond(verbond.stamnummer).then((results) => {

            if (results.onderliggende_stamnummers != 0) {
                this.unitLevel = verbond.naam;
                this.verbonden = results.onderliggende_stamnummers;
                this.verbond = results;

                angular.forEach(this.verbonden, (value, index) => {
                    if (!value.stamnummer.endsWith("00")) {
                        this.KrinkelService.getParticipantsForUnit(value.stamnummer).then((results) => {
                            value.amount = results;
                        })
                    }
                });
            }
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
            });
        }
    }
}

export var UnitsComponent = {
    template: require('./units.html'),
    controller: UnitsController
};

UnitsComponent.$inject = ['KrinkelService'];
