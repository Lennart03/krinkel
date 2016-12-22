/*@ngInject*/
class KrinkelAdminController {
    constructor(KrinkelService, AuthService, $location) {
        this.KrinkelService = KrinkelService;
        this.AuthService = AuthService;
        this.$location = $location;
    }

    $onInit() {
        let currentAdnumber = this.AuthService.getLoggedinUser().adnummer;
        this.isSuperAdmin = (currentAdnumber == 152504 || currentAdnumber == 109318);
    }
}

export var KrinkelAdminComponent = {
    template: require('./krinkel-admin.html'),
    controller : KrinkelAdminController
};

KrinkelAdminComponent.$inject = ['KrinkelService', 'AuthService', '$location'];
