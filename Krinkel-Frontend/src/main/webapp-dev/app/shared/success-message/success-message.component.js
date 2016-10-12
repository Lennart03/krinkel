/*@ngInject*/
class SuccessMessageController {
    constructor(AuthService, $location) {
        this.AuthService = AuthService;
        this.$location = $location;
        window.scrollTo(0, 0);

        if (this.AuthService.getLoggedinUser() == null) {
            this.$location.path('/login');
        } else {
            try {
                var currentUser = this.AuthService.getUserDetails();
                if (currentUser === undefined) {
                    this.$location.path('/home');
                }
                if (currentUser.registered && currentUser.hasPaid) {
                } else if (currentUser.registered && !currentUser.hasPaid) {
                    this.$location.path('/fail');
                } else {
                    this.$location.path('/home')
                }
            } catch (exception) {

            }
        }
    }

    $onInit() {
        this.title = "Hoera!"
    }

}

export var SuccessMessageComponent = {
    template: require('./success-message.html'),
    controller: SuccessMessageController
};
SuccessMessageComponent.$inject = ['AuthService', '$location'];
