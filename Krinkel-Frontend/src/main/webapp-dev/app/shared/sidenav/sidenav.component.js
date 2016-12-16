/*@ngInject*/
class SideNavController {
    constructor(AuthService,$location,KrinkelService,BASEURL,$window) {
        this.AuthService = AuthService;
        this.$location = $location;
        this.KrinelService = KrinkelService;
        this.userDetails;
        this.BASEURL = BASEURL;
        this.$window = $window;
    }

    $onInit() {
    }

    logout() {
        this.AuthService.logoutUser();
        //this.$location.path('/login')
    }
    redirectToLandingsPage()
    {
        this.$window.location.href= this.BASEURL+'/site/index.html';
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

SideNavComponent.$inject = ['AuthService','$location','KrinkelService','BASEURL','$window'];
