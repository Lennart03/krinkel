/**
 * Created by FVHBB94 on 8/12/2016.
 */
class KrinkelKarController{
    constructor(KrinkelService, AuthService){
        this.KrinkelService = KrinkelService;
        this.AuthService = AuthService;
        this.colleagueList = [];
        this.colleagueList.push({first_name: "Shenno", last_name:"Willaert", adnr:"1234567"});
    }
    getCurrentList(){
        //get basket from service
        //return this.colleagueList;
        return this.KrinkelService.getBasket();
    }
}

export var KrinkelKarComponent = {
    template: require("./krinkelkar.html"),
    controller: KrinkelKarController
};

KrinkelKarComponent.$inject = ["KrinkelService", "AuthService"];
