/*@ngInject*/
class SuccessMessageController {
    constructor(AuthService,$location) {
        this.AuthService = AuthService;
        this.$location = $location;
    }

    $onInit() {
        if (this.AuthService.getLoggedinUser() == null) {
            this.$location.path('/login');
        }
        this.title = "Hoera!"
    }

}

export var SuccessMessageComponent = {
    template: require('./success-message.html'),
    controller: SuccessMessageController
};
SuccessMessageComponent.$inject = ['AuthService','$location'];
