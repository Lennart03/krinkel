class VerbondUnitController {
    constructor(KrinkelService) {
        this.KrinkelService = KrinkelService;
    }

    $onInit() {
        this.KrinkelService.getVerbonden().then((results) => {
            this.verbonden = results;
        });
    }

    openVerbond(verbond) {
        this.verbond = verbond;
        this.KrinkelService.getGewestenForVerbond(this.verbond.stamnummer).then((results) => {
            if (results.onderliggende_stamnummers != 0) {
                this.unitLevel = this.verbond.naam;
                this.verbonden = results.onderliggende_stamnummers;
            }
        });
    }
}

export var VerbondUnitComponent = {
    template: require('./verbond-unit.html'),
    controller: VerbondUnitController
};

VerbondUnitComponent.$inject = ['KrinkelService'];
