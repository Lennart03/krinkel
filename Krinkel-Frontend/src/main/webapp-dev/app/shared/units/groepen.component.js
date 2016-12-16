class GroepenController {
    constructor(KrinkelService, $route, AuthService, $location, $routeParams) {
        this.KrinkelService = KrinkelService;
        this.$route = $route;
        this.AuthService = AuthService;
        this.$location = $location;
        this.lolo = $routeParams.gewestNr;
        console.log(this.lolo + 'groepen.component.js says hi!');
    }

    $onInit() {
        this.KrinkelService.getGroepenList(this.lolo).then((results) => {
            console.log(results);
            this.groepen = results;
        });
    }
}

export var GroepenComponent = {
    template: require('./groepen.html'),
    controller: GroepenController
};

GroepenComponent.$inject = ['KrinkelService', '$route', 'AuthService', '$location', '$routeParams'];
