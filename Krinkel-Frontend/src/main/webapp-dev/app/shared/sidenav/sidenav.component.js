/*@ngInject*/
class SideNavController {
    constructor(AuthService,$location,KrinkelService) {
        this.AuthService = AuthService;
        this.$location = $location;
        this.KrinelService = KrinkelService;
        this.userDetails;
    }

    $onInit() {
    }

    logout() {
        this.AuthService.logoutUser();
        this.$location.path('/login')
    }

    go(){
        var currentUser = this.userDetails;

        if(currentUser.registered && currentUser.hasPaid){
            this.$location.path('/success');
        }else{
            this.$location.path('/home')
        }
    }


    getUserDetails() {
        this.AuthService.getUserDetails().then((resp) => {
            this.userDetails = resp;
        });
        return this.userDetails;
    }
}

export var SideNavComponent = {
    template: require('./sidenav.html'),
    controller: SideNavController
};

SideNavComponent.$inject = ['AuthService','$location','KrinkelService'];
