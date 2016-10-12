class KrinkelSelectController{
    constructor($location, KrinkelService,AuthService,SelectService){
        this.$location = $location;
        this.KrinkelService = KrinkelService;
        this.AuthService = AuthService;
        this.SelectService = SelectService;
        this.colleagues = []
    }

    $onInit(){
        this.KrinkelService.getColleagues('1').then((resp) => {
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

KrinkelSelectComponent.$inject = ['$location', 'KrinkelService','AuthService','SelectService'];
