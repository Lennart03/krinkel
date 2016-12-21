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



    redirectToInschrijvingen() {

        this.$location.path('/verbonden');
        //Commented below is init for redirecting from WP10

        // userDetails is a User
        // console.log('Redirecting through Overzicht Inschrijvingen knop ' + this.userDetails.role + ' -- ' +
        //     this.userDetails.adNumber + ' -- ' + this.userDetails.registered);
        // console.log('The rolesandupperclasses '+ this.userDetails.rolesAndUpperClassesByStam);
        // console.log('Debug');
        // console.log('The rolesandupperclasses size: ' + this.userDetails.rolesAndUpperClassesByStam.size());
        // if(this.userDetails.rolesAndUpperClassesByStam.size() > 0){
        //     console.log('To array: ' + this.userDetails.rolesAndUpperClassesByStam.entrySet().toArray());
        //     console.log('To array first element: ' + this.userDetails.rolesAndUpperClassesByStam.entrySet().toArray()[0]);
        // }
        // if(this.userDetails.role === 'ADMIN'){
        //     console.log('Security role ADMIN!');
        //     this.$location.path('/verbonden');
        // }
        // else if(this.userDetails.role === 'NATIONAAL'){
        //     console.log('Security role NATIONAAL!');
        //     this.$location.path('/verbonden');
        // }
        // else if(this.userDetails.role === 'VERBOND') {
        //     console.log('Security role VERBOND!');
        //     this.$location.path('/verbonden');
        // }
        // else if(this.userDetails.role === 'GEWEST'){
        //     console.log('Security role GEWEST!');
        //     this.$location.path('/verbonden');
        // }
        // else if(this.userDetails.role === 'GROEP'){
        //     console.log('Security role GROEP!');
        //     this.$location.path('/verbonden');
        // }
    }
}

export var SideNavComponent = {
    template: require('./sidenav.html'),
    controller: SideNavController
};

SideNavComponent.$inject = ['AuthService','$location','KrinkelService','BASEURL','$window'];
