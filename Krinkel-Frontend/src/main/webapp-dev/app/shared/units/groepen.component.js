class GroepenController {
    constructor(KrinkelService, $route, AuthService, $location, $routeParams) {
        this.KrinkelService = KrinkelService;
        this.$route = $route;
        this.AuthService = AuthService;
        this.$location = $location;
        this.gewestNr = $routeParams.gewestNr;
        this.gewestNaam = $routeParams.gewestNaam;
        //console.log(this.lolo + 'groepen.component.js says hi!');
    }

    $onInit() {
        this.KrinkelService.getGroepenList(this.gewestNr).then((results) => {
            //console.log(results);
            this.groepen = results;
        });
    }

    redirectToGroep(groepNr, groepNaam) {
        //console.log('Tried to redirect via javascript to groep with groepStamNummer: ' + groepStamNr);

        this.$location.path('/groep/'+
            groepNr + '/' + groepNaam);
    }
}

export var GroepenComponent = {
    template: require('./groepen.html'),
    controller: GroepenController
};

GroepenComponent.$inject = ['KrinkelService', '$route', 'AuthService', '$location', '$routeParams'];
