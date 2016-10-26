/*@ngInject*/
class KrinkelHomepageController {
    constructor(AuthService, $location) {
        this.AuthService = AuthService;
        this.$location = $location;
    }

    $onInit() {
    }
}

export var KrinkelHomepageComponent = {
    template: require('./krinkel-homepage.html'),
    controller: KrinkelHomepageController
};

KrinkelHomepageComponent.$inject = ['AuthService', '$location'];
