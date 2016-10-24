class KrinkelSelectController{
    constructor($location, $log, KrinkelService,AuthService,SelectService){
        this.$log = $log;
        this.$location = $location;
        this.KrinkelService = KrinkelService;
        this.AuthService = AuthService;
        this.SelectService = SelectService;
        this.colleagues = [];
    }

    $onInit(){
        this.$log.debug("log");
        this.$log.debug(this.AuthService.getUserDetails());
        this.SelectService.setSelectedFlag(false);
        this.KrinkelService.getColleagues(this.AuthService.getUserDetails().stamnummer).then((resp) => {
            this.colleagues = resp;
        });
    }

    selectPerson(colleague){
        this.SelectService.setColleague(colleague);
        this.$location.path("/register-participant");
    }
}

export var KrinkelSelectComponent = {
    template: require('./select.html'),
    controller: KrinkelSelectController
};

KrinkelSelectComponent.$inject = ['$location', '$log', 'KrinkelService','AuthService','SelectService'];
