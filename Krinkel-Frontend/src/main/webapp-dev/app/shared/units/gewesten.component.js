class GewestenController {
    constructor(KrinkelService, $route, AuthService, $location, $routeParams) {
        this.KrinkelService = KrinkelService;
        this.$route = $route;
        this.AuthService = AuthService;
        this.$location = $location;
        this.verbondNr = $routeParams.verbondNr;
        this.verbondNaam = $routeParams.verbondNaam;
        //console.log(this.lolo + ' gewesten.component.js says hi! ' + this.verbondNaam);
    }

    $onInit() {
        this.KrinkelService.getGewestenList(this.verbondNr).then((results) => {
            //console.log(results);
            this.gewesten = results;
        });
    }

    redirectToGroepen(gewestNr, gewestNaam) {
        //console.log('Tried to redirect via javascript to gewesten with verbondStamNummer: ' + gewestStamNr);

        this.$location.path('/groepen/' +
            gewestNr + '/' + gewestNaam);
        //groepen/:verbondNr/:verbondNaam/:gewestNr/:gewestNaam
    }
}

export var GewestenComponent = {
    template: require('./gewesten.html'),
    controller: GewestenController
};

GewestenComponent.$inject = ['KrinkelService', '$route', 'AuthService', '$location', '$routeParams'];
