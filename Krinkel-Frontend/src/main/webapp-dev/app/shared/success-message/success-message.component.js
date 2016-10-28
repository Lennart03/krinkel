/*@ngInject*/
class SuccessMessageController {
    constructor($location, AuthService) {
        this.AuthService = AuthService;
        this.$location = $location;
        window.scrollTo(0, 0);

        // if (this.AuthService.getLoggedinUser() == null) {
        //     this.$location.path('/login');
        // } else {
        //     this.AuthService.getCurrentUserDetails(this.AuthService.getLoggedinUser().adnummer).then((resp) => {
        //         if (resp.registered == false || resp.hasPaid == false) {
        //             $location.path('/home');
        //         }
        //     });
        // }
    }

    $onInit() {
    }
}

export var SuccessMessageComponent = {
    template: require('./success-message.html'),
    controller: SuccessMessageController
};

SuccessMessageComponent.$inject = ['AuthService', '$location'];
