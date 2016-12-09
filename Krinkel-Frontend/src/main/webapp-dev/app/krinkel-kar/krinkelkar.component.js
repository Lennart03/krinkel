/**
 * Created by FVHBB94 on 8/12/2016.
 */
class KrinkelKarController{
    constructor(KrinkelService, AuthService){
        this.KrinkelService = KrinkelService;
        this.AuthService = AuthService;
        this.colleagueList = [];
        this.participantPrice = 11000;
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
        this.KrinkelService.setSubscriberEmailForBasket(this.subscriberEmail).then(()=>{
            console.log("added mail");
            //redirect to payment
        });
    }


}

export var KrinkelKarComponent = {
    template: require("./krinkelkar.html"),
    controller: KrinkelKarController
};

KrinkelKarComponent.$inject = ["KrinkelService", "AuthService"];
