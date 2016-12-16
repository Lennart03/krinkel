/**
 * Created by JCPBB69 on 15/12/2016.
 */
class VerbondenController {
    constructor(KrinkelService, AuthService, $route, $location, $http) {
        this.KrinkelService = KrinkelService;
        this.AuthService = AuthService;
        this.$route = $route;
        this.$location = $location;
        this.$http = $http;
    }

    $onInit() {
        this.KrinkelService.getVerbondenList().then((results) => {
            this.verbonden = results;
        });
    }

    redirectToGewesten(lol){
        console.log('Tried to redirect via javascript to gewesten with verbondStamNummer: ' + lol);

        this.$location.path('/gewesten/'+ lol);

        // console.log(this.$location.url);
        // console.log(this.$location.path('/gewesten/'+verbondStamNummer));

        // this.$http.get(`/gewesten/stamNummerTest`).then((resp) => {
        //
        // });

        // this.$http.get(`/gewesten/${verbondStamNummer}`).then((resp) => {
        //
        // });
        // this.$location.path('/gewesten/${verbondStamNummer}');

    }
}

export var VerbondenComponent = {
    template: require('./verbonden.html'),
    controller: VerbondenController
};

VerbondenComponent.$inject = ['KrinkelService', 'AuthService', '$route', '$location', '$http'];

