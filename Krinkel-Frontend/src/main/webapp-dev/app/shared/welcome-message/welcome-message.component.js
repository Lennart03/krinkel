/*@ngInject*/
class WelcomeMessageController {
    constructor($location, AuthService) {
        this.$location = $location;
        this.AuthService = AuthService;


        this.AuthService.getCurrentUserDetails(this.AuthService.getLoggedinUser().adnummer).then((resp) => {
            if (resp.registered && resp.hasPaid) {
                this.$location.path('/success');
            }
        }, () => {
        })
    }

    $onInit() {
        // if(this.AuthService.getCurrentUserDetails().registered && this.AuthService.getCurrentUserDetails().hasPaid){
        //     this.$location.path('/success');
        // }else if(this.AuthService.getCurrentUserDetails().registered && !this.AuthService.getCurrentUserDetails().hasPaid){
        //     this.$location.path('/fail');
        // }
    }
}

export var WelcomeMessageComponent = {
    template: require('./welcome-message.html'),
    controller: WelcomeMessageController
};

WelcomeMessageComponent.$inject = ['$location', 'AuthService'];
