/*@ngInject*/
class SuccessMessageController {
    constructor($location, AuthService) {
        this.AuthService = AuthService;
        this.$location = $location;
        window.scrollTo(0, 0);
    }

    $onInit() {
    }
}

export var SuccessMessageComponent = {
    template: require('./success-message.html'),
    controller: SuccessMessageController
};

SuccessMessageComponent.$inject = ['AuthService', '$location'];
