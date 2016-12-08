/*@ngInject*/
class KrinkelExportController {
    constructor(KrinkelService, $location, ExportController) {
        this.KrinkelService = KrinkelService;
        this.$location = $location;
        this.ExportController = ExportController;
    }

    $onInit() {

    }

    exportCompleteEntryList() {
    /*    this.ExportController.getFile()*/
    }
}

export var KrinkelExportComponent = {
    template: require('./krinkel-export.html'),
    controller: KrinkelExportController
};

KrinkelExportComponent.$inject = ['KrinkelService','$location'];
