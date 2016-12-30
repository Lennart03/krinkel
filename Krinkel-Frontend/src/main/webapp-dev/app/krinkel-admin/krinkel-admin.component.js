/*@ngInject*/
class KrinkelAdminController {
    constructor(KrinkelService, AuthService, $location) {
        this.KrinkelService = KrinkelService;
        this.AuthService = AuthService;
        this.$location = $location;
    }

    $onInit() {
        this.KrinkelService.isSuperAdmin(this.AuthService.getLoggedinUser().adnummer).then((resp) => {
            this.isSuperAdmin = resp;
        });
    }

}

export var KrinkelAdminComponent = {
    template: require('./krinkel-admin.html'),
    controller : KrinkelAdminController
};

KrinkelAdminComponent.$inject = ['KrinkelService', 'AuthService', '$location'];
