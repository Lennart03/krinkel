/*@ngInject*/
class FailMessageController {
    constructor(AuthService, $location) {
        this.AuthService = AuthService;
        this.$location = $location;
        window.scrollTo(0, 0);


        if (this.AuthService.getLoggedinUser() == null) {
            this.$location.path('/login');
        } else {
            try {
                var currentUser;
                this.AuthService.getUserDetails().then((resp) => {
                    currentUser = resp;
                });

                if (currentUser === undefined) {
                    this.$location.path('/home');
                }
                if (currentUser.registered && currentUser.hasPaid) {
                    this.$location.path('/success');
                } else if (currentUser.registered && !currentUser.hasPaid) {
                } else {
                    this.$location.path('/home')
                }
            } catch (exception) {

            }

        }
        this.title = 'Oeps!';

    }

    $onInit() {
    }

}

export var FailMessageComponent = {
    template: require('./fail-message.html'),
    controller: FailMessageController
};
FailMessageComponent.$inject = ['AuthService', '$location'];
