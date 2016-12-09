/**
 * Created by FVHBB94 on 8/12/2016.
 */
class KrinkelKarController{
    constructor(KrinkelService, AuthService){
        this.KrinkelService = KrinkelService;
        this.AuthService = AuthService;
        this.colleagueList = [];
        this.participantPrice = 11000;
        this.init();
    }

    init() {
        this.KrinkelService.getBasket().then((data) => {
            this.colleagueList = data;
        });
    }

    deleteColleague(adNumber) {
        this.KrinkelService.removePersonFromBasket(adNumber).then(() => {
            this.init();
            console.log('deleted ' + adNumber)
        });

    }


}

export var KrinkelKarComponent = {
    template: require("./krinkelkar.html"),
    controller: KrinkelKarController
};

KrinkelKarComponent.$inject = ["KrinkelService", "AuthService"];
