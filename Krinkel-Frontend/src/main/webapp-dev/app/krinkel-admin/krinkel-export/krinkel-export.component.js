/*@ngInject*/
class KrinkelExportController {
    constructor(KrinkelService, $location) {
        this.KrinkelService = KrinkelService;
        this.$location = $location;
    }

    $onInit() {

    }
}

export var KrinkelExportComponent = {
    template: require('./krinkel-export.html'),
    controller: KrinkelExportController
};

KrinkelExportComponent.$inject = ['KrinkelService','$location'];
