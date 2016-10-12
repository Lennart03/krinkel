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

}

export var SideNavComponent = {
    template: require('./sidenav.html'),
    controller: SideNavController
};

SideNavComponent.$inject = ['AuthService','$location'];
