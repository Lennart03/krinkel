class GewestenController {
    constructor(KrinkelService, $route, AuthService, $location, $routeParams) {
        this.KrinkelService = KrinkelService;
        this.$route = $route;
        this.AuthService = AuthService;
        this.$location = $location;
        this.lolo = $routeParams.lol;
        this.verbondNaam = $routeParams.verbondNaam;
        //console.log(this.lolo + ' gewesten.component.js says hi! ' + this.verbondNaam);
    }

    $onInit() {
        this.KrinkelService.getGewestenList(this.lolo).then((results) => {
            //console.log(results);
            this.gewesten = results;
        });
    }

    redirectToGroepen(gewestStamNr, gewestNaam) {
        //console.log('Tried to redirect via javascript to gewesten with verbondStamNummer: ' + gewestStamNr);

        this.$location.path('/groepen/' + gewestStamNr + '/' + gewestNaam);
    }
}

export var GewestenComponent = {
    template: require('./gewesten.html'),
    controller: GewestenController
};

GewestenComponent.$inject = ['KrinkelService', '$route', 'AuthService', '$location', '$routeParams'];
