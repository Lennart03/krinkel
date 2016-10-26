/*@ngInject*/
class TopNavController {
    constructor(AuthService, $location) {
        this.AuthService = AuthService;
        this.$location = $location;
        this.userDetails;
    }

    $onInit() {
    }

    getUserDetails() {
        this.AuthService.getUserDetails().then((resp) => {
            this.userDetails = resp;
        });
        return this.userDetails;
    }
}

export var TopNavComponent = {
    template: require('./topnav.html'),
    controller: TopNavController
};

TopNavComponent.$inject = ['AuthService', '$location'];
