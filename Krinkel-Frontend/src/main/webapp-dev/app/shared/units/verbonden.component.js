/**
 * Created by JCPBB69 on 15/12/2016.
 */
class VerbondenController {
    constructor(KrinkelService, AuthService, $route) {
        this.KrinkelService = KrinkelService;
        this.AuthService = AuthService;
        this.$route = $route;
    }

    $onInit() {
        this.KrinkelService.getVerbondenList().then((results) => {
            this.verbondenList = results;
        });
    }

}

export var UnitsComponent = {
    template: require('./verbonden.html'),
    controller: VerbondenController
};

UnitsComponent.$inject = ['KrinkelService', 'AuthService', 'VerbondenService', '$route'];

