class KrinkelSelectController {
    constructor($location, $log, KrinkelService, AuthService, SelectService){
        this.$log = $log;
        this.$location = $location;
        this.KrinkelService = KrinkelService;
        this.AuthService = AuthService;
        this.SelectService = SelectService;
        this.colleagues = [];
        this.userDetails2;
    }

    $onInit(){
        this.SelectService.setSelectedFlag(false);
        this.AuthService.getUserDetails().then((resp) => {
            this.KrinkelService.getColleagues(resp.stamnummer).then((resp) => {
                resp.forEach(p => this.colleagues.push(JSON.parse(p)));
            });
        });


    }

    selectPerson(colleague){
        this.SelectService.setColleague(colleague);
        this.$location.path("/register-participant");
    }
    getUserDetails() {

    }
}

export var KrinkelSelectComponent = {
    template: require('./select.html'),
    controller: KrinkelSelectController
};

KrinkelSelectComponent.$inject = ['$location', '$log', 'KrinkelService', 'AuthService', 'SelectService'];
