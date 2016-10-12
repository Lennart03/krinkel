class RegisterController {

    constructor($log, $window, StorageService, MapperService, AuthService, KrinkelService, $location, SelectService) {
        this.$log = $log;
        this.$window = $window;
        this.StorageService = StorageService;
        this.MapperService = MapperService;
        this.AuthService = AuthService;
        this.KrinkelService = KrinkelService;
        this.$location = $location;
        this.SelectService = SelectService;

        this.phoneNumberPattern = /^((\+|00)32\s?|0)(\d\s?\d{3}|\d{2}\s?\d{2})(\s?\d{2}){2}|((\+|00)32\s?|0)4(60|[789]\d)(\s?\d{2}){3}$/;
        this.birthdatePattern = /^(\d{4})(\/|-)(\d{1,2})(\/|-)(\d{1,2})$/;
        this.postalcodePattern = /^(\d{4})$/;
        this.campgrounds = ['Antwerpen', 'Kempen', 'Mechelen', 'Limburg', 'Leuven', 'Brussel', 'West-Vlaanderen', 'Heuvelland', 'Roeland', 'Reinaert', 'Nationaal', 'Internationaal'];
        angular.element('select').material_select();

        this.dataIsRemoved = false;

        window.scrollTo(0,0);



        if (this.SelectService.getSelectedFlag()) {
            this.SelectService.setColleague(undefined);
            this.SelectService.setSelectedFlag(false);
        }
    }

    registerPerson(newPerson) {
        if (this.type === 'volunteer') {
            var thiz = this;
            this.KrinkelService.postVolunteer(this.MapperService.mapVolunteer(newPerson)).then(function (response) {
                thiz.dataIsRemoved = true;
                thiz.StorageService.removeUser();
                thiz.$location.path('/success');
            });
            return;
        }

        if (this.type === 'participant') {
            var thiz = this;
            this.KrinkelService.postParticipant(this.MapperService.mapParticipant(newPerson)).then(function (response) {
                thiz.dataIsRemoved = true;
                thiz.StorageService.removeUser();
                thiz.SelectService.setSelectedFlag(false);
                thiz.$location.path('/success');
            });
            return;
        }
    }



    $onInit() {
        if (this.AuthService.getLoggedinUser() == null) {
            this.$location.path('/');
        }

        if (this.SelectService.getColleague() !== undefined) {
            this.newPerson = {
                adNumber: this.SelectService.getColleague().adnr
            };
            this.SelectService.setSelectedFlag(true);

            console.log("From select");
        } else {
            console.log("Not from selectx");
            var user = this.StorageService.getUser();
            if (user) {
                this.newPerson = user;
            } else {
                this.newPerson = {};
                // this.newPerson.birthDate = "1995-11-24";
            }
        }

        angular.element('.modal-trigger').leanModal();
        // Fill data from localStorage
        // this.newPerson = this.StorageService.getUser();

        this.errorMessages = document.getElementsByClassName("error");
        // console.log(document.getElementsByClassName("error"));
    }

    functionCallAfterDOMRender() {
        try {
            Materialize.updateTextFields();
        } catch (exception) {

        }
    }





    /**
     * Not the ideal lifecycle hook to save everything in localstorage, due to time constraints this will have to do for now.
     */

    $doCheck() {
        if(!this.dataIsRemoved){
            this.StorageService.saveUser(this.newPerson);
        }
    }

}


export var RegisterComponent = {
    template: require('./register.html'),
    controller: RegisterController,
    bindings: {
        type: '@'
    }
};
RegisterComponent.$inject = ['$log', '$window', 'StorageService', 'MapperService', 'AuthService', 'KrinkelService', '$location', 'SelectService'];
