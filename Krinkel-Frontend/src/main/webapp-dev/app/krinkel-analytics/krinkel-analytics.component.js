class KrinkelAnalyticsController{
    constructor(KrinkelService){
        this.KrinkelService = KrinkelService;
    }

    $onInit(){
    }
}

export var KrinkelAnalyticsComponent = {
    template: require('./krinkel-analytics.html'),
    controller: KrinkelAnalyticsController
};

KrinkelAnalyticsComponent.$inject = ['KrinkelService'];
