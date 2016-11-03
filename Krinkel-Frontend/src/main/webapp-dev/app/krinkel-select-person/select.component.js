class KrinkelSelectController {
    constructor($location, $log, KrinkelService, AuthService, SelectService){
        this.$log = $log;
        this.$location = $location;
        this.KrinkelService = KrinkelService;
        this.AuthService = AuthService;
        this.SelectService = SelectService;
        this.colleagues = [];
        this.userDetails2;

        console.log("user details hier:");

        console.log(this.getUserDetails());

    }

    $onInit(){
        this.SelectService.setSelectedFlag(false);
        this.AuthService.getUserDetails().then((resp) => {
            this.KrinkelService.getColleagues(resp.stamnummer).then((resp) => {
                console.log("LOGGING THE COLLEAGUES FROM CHIRO YO");


                resp.forEach(p => this.colleagues.push(JSON.parse(p)));
                console.log(this.colleagues);
            });
        });


    }

    selectPerson(colleague){
        this.SelectService.setColleague(colleague);
        this.$location.path("/register-participant");
    }
    getUserDetails() {

        // console.log("user details:");
        // console.log(this.userDetails2);
    }
}

export var KrinkelSelectComponent = {
    template: require('./select.html'),
    controller: KrinkelSelectController
};

KrinkelSelectComponent.$inject = ['$location', '$log', 'KrinkelService', 'AuthService', 'SelectService'];
