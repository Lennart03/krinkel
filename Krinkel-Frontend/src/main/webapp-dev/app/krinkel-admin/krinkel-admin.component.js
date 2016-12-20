/*@ngInject*/
class KrinkelAdminController {
    constructor(KrinkelService, $location) {
        this.KrinkelService = KrinkelService;
        this.$location = $location;
    }

    $onInit() {

    }
}

export var KrinkelAdminComponent = {
    template: require('./krinkel-admin.html'),
    controller : KrinkelAdminController
};

KrinkelAdminComponent.$inject = ['KrinkelService', '$location'];
