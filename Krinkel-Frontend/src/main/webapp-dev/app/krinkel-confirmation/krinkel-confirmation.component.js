class KrinkelConfirmationController{
    constructor($location, KrinkelService){
        this.$location = $location;
        this.KrinkelService = KrinkelService;
    }

    $onInit(){
        this.adNumber = this.$location.search().adNumber;
        this.token = this.$location.search().token;

        this.KrinkelService.getConfirmation(this.adNumber, this.token).then((results) => {
            this.isSuccess = results.success;
        });
    }


}

export var KrinkelConfirmationComponent = {
    template: require('./krinkel-confirmation.html'),
    controller: KrinkelConfirmationController
};

KrinkelConfirmationComponent.$inject = ['$location', 'KrinkelService'];
