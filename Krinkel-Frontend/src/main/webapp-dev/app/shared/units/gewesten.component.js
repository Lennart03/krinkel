class GewestenController {
    constructor(KrinkelService, $route, AuthService, $location, $routeParams) {
        this.KrinkelService = KrinkelService;
        this.$route = $route;
        this.AuthService = AuthService;
        this.$location = $location;
        this.lolo = $routeParams.lol;
        console.log(this.lolo + 'gewesten.component.js says hi!');
    }

    $onInit() {
        this.KrinkelService.getGewestenList(this.lolo).then((results) => {
            console.log(results);
            this.gewesten = results;
        });
    }

    redirectToGroepen(gewestStamNr) {
        console.log('Tried to redirect via javascript to gewesten with verbondStamNummer: ' + gewestStamNr);

        this.$location.path('/groepen/' + gewestStamNr);
    }

    getPloegen(stamnummer) {
        this.$location.path('/gewesten/'+ stamnummer);
    }
}

export var GewestenComponent = {
    template: require('./gewesten.html'),
    controller: GewestenController
};

GewestenComponent.$inject = ['KrinkelService', '$route', 'AuthService', '$location', '$routeParams'];
