/**
 * Created by FVHBB94 on 8/12/2016.
 */
class KrinkelKarController{
    constructor(KrinkelService, AuthService){
        this.KrinkelService = KrinkelService;
        this.AuthService = AuthService;
        this.colleagueList = [];
        this.init();
    }

    init() {
        var bas = this.KrinkelService.getBasket();
        this.colleagueList = bas;
        //this.colleagueList.push({first_name: "Shenno", last_name:"Willaert"});
        //this.colleagueList.push({first_name: "Shenno2", last_name:"Willaert2"});

    }

}

export var KrinkelKarComponent = {
    template: require("./krinkelkar.html"),
    controller: KrinkelKarController
};

KrinkelKarComponent.$inject = ["KrinkelService", "AuthService"];
