/*@ngInject*/
class SideNavController {
    constructor(AuthService,$location) {
        this.AuthService = AuthService;
        this.$location = $location;
    }

    $onInit() {
    }

    logout() {
        this.AuthService.logoutUser();
        this.$location.path('/login')
    }

    go(){
        var currentUser = this.AuthService.getUserDetails();

        if(currentUser.registered && currentUser.hasPaid){
            this.$location.path('/success');
        }else if (currentUser.registered && !currentUser.hasPaid){
            this.$location.path('/fail');
        }else{
            this.$location.path('/home')
        }
    }

}

export var SideNavComponent = {
    template: require('./sidenav.html'),
    controller: SideNavController
};

SideNavComponent.$inject = ['AuthService','$location'];
