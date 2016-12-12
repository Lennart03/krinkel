/**
 * Created by FVHBB94 on 8/12/2016.
 */
class KrinkelKarController{
    constructor(KrinkelService, AuthService, $window, $location){
        this.KrinkelService = KrinkelService;
        this.AuthService = AuthService;
        this.colleagueList = [];
        this.participantPrice = 11000;
        this.$window = $window;
        this.$location = $location;
        this.subscriberEmail = '';
        this.init();
    }

    init() {
        this.KrinkelService.getBasket().then((data) => {
            this.colleagueList = data;
        });
        $(".modal-trigger").leanModal();
        this.subscriberEmail = this.AuthService.getLoggedinUser().email;
    }

    deleteColleague(adNumber) {
        this.KrinkelService.removePersonFromBasket(adNumber).then(() => {
            this.init();
            console.log('deleted ' + adNumber)
        });

    }

    doPayment(){
        var self = this;
        this.KrinkelService.setSubscriberEmailForBasket(this.subscriberEmail).then((resp)=>{
            console.log("added mail");
            this.KrinkelService.doPayment().then((resp) =>{
                console.log(resp);
                self.$window.location.href = resp.headers().location;
            });
        });
    }

    functionCallAfterDOMRender() {
        try {
            Materialize.updateTextFields();
        } catch (exception) {

        }
    }


}

export var KrinkelKarComponent = {
    template: require("./krinkelkar.html"),
    controller: KrinkelKarController
};

KrinkelKarComponent.$inject = ["KrinkelService", "AuthService", "$window", "$location"];
