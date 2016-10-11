class RegisterController {

    constructor($log, $window, StorageService, MapperService, AuthService, KrinkelService, $location) {
        this.$log = $log;
        this.$window = $window;
        this.StorageService = StorageService;
        this.MapperService = MapperService;
        this.AuthService = AuthService;
        this.KrinkelService = KrinkelService;
        this.$location = $location;

        this.phoneNumberPattern = /^((\+|00)32\s?|0)(\d\s?\d{3}|\d{2}\s?\d{2})(\s?\d{2}){2}|((\+|00)32\s?|0)4(60|[789]\d)(\s?\d{2}){3}$/;
        this.birthdatePattern = /^(\d{4})(\/|-)(\d{1,2})(\/|-)(\d{1,2})$/;
        this.postalcodePattern = /^(\d{4})$/;
        this.campgrounds = ['Antwerpen', 'Kempen', 'Mechelen', 'Limburg', 'Leuven', 'Brussel', 'West-Vlaanderen', 'Heuvelland', 'Roeland', 'Reinaert', 'Nationaal', 'Internationaal'];
        angular.element('select').material_select();

        this.dataIsRemoved = false;


    }

    registerPerson(newPerson) {
        if (this.type === 'volunteer') {
            var thiz = this;
            this.KrinkelService.postVolunteer(this.MapperService.mapVolunteer(newPerson)).then(function (resp) {
                thiz.dataIsRemoved = true;
                thiz.StorageService.removeUser();
                thiz.$window.location.href = resp.headers().location;
            });
            return;
        }

        if (this.type === 'participant') {
            var thiz = this;
            this.KrinkelService.postParticipant(this.MapperService.mapParticipant(newPerson)).then(function (resp) {
                thiz.dataIsRemoved = true;
                thiz.StorageService.removeUser();
                thiz.$window.location.href = resp.headers().location;
            });
            return;
        }
    }

    $onInit() {
        if (this.AuthService.getLoggedinUser() == null) {
            this.$location.path('/login');
        }
        angular.element('.modal-trigger').leanModal();
        // Fill data from localStorage
        // this.newPerson = this.StorageService.getUser();
        var user = this.StorageService.getUser();
        if (user) {
            this.newPerson = user;
        } else {
            this.newPerson = {};
            this.newPerson.job = 'Aanbod nationale kampgrond';
            // this.newPerson.birthDate = "1995-11-24";
        }
        this.errorMessages = document.getElementsByClassName("error");


        // Initialize Materialize inputs
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
RegisterComponent.$inject = ['$log', '$window', 'StorageService', 'MapperService', 'AuthService', 'KrinkelService', '$location'];
