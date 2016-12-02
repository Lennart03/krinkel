class KrinkelAnalyticsController{
    constructor(KrinkelService, AuthService){
        this.KrinkelService = KrinkelService;
        this.AuthService = AuthService;
    }

    $onInit(){
    }
}

export var KrinkelAnalyticsComponent = {
    template: require('./krinkel-analytics.html'),
    controller: KrinkelAnalyticsController
};

KrinkelAnalyticsComponent.$inject = ['KrinkelService', 'AuthService'];
