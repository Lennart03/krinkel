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
        this.KrinkelService.getGewestenForVerbond(this.verbond.stam).then((results) => {
            this.gewesten = results;
            console.debug(this.gewesten);
        });
    }
}

export var VerbondUnitComponent = {
    template: require('./verbond-unit.html'),
    controller: VerbondUnitController
};

VerbondUnitComponent.$inject = ['KrinkelService'];
