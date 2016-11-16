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
        console.log("IN details");
        this.AuthService.getUserDetails().then((resp) => {
            this.userDetails = resp;
            console.log("In return");
            console.log(resp);
        });
        return this.userDetails;
    }
}

export var TopNavComponent = {
    template: require('./topnav.html'),
    controller: TopNavController
};

TopNavComponent.$inject = ['AuthService', '$location'];
