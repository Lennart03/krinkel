class KrinkelSelectController {
    constructor($location, $log, KrinkelService, AuthService, SelectService){
        this.$log = $log;
        this.$location = $location;
        this.KrinkelService = KrinkelService;
        this.AuthService = AuthService;
        this.SelectService = SelectService;
        this.colleagues = [];
        this.userDetails2;
        this.isLoading = true;
    }

    $onInit(){
        this.SelectService.setSelectedFlag(false);
        this.AuthService.getUserDetails().then((resp) => {
            this.KrinkelService.getColleagues(resp.stamnummer).then((resp) => {
                //resp.forEach(p => this.colleagues.push(JSON.parse(p)));
                resp.forEach(p => this.colleagues.push(p));
                this.isLoading = false;
            });

        });


    }

    selectPerson(colleague){
        this.SelectService.setColleague(colleague);
        this.$location.path("/add-to-basket");
    }
    getUserDetails() {

    }
}

export var KrinkelSelectComponent = {
    template: require('./select.html'),
    controller: KrinkelSelectController
};

KrinkelSelectComponent.$inject = ['$location', '$log', 'KrinkelService', 'AuthService', 'SelectService'];
