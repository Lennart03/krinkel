class RegisterController {

    constructor($log, $window, StorageService, MapperService, AuthService) {
        this.$log = $log;
        this.$window = $window;
        this.StorageService = StorageService;
        this.MapperService = MapperService;
        this.AuthService = AuthService;

        this.phoneNumberPattern = /^((\+|00)32\s?|0)(\d\s?\d{3}|\d{2}\s?\d{2})(\s?\d{2}){2}|((\+|00)32\s?|0)4(60|[789]\d)(\s?\d{2}){3}$/;
        this.campgrounds = ['Antwerpen', 'Kempen', 'Mechelen', 'Limburg', 'Leuven', 'Brussel', 'West-Vlaanderen', 'Heuvelland', 'Roeland', 'Reinaert', 'Nationaal', 'Internationaal'];
        angular.element('select').material_select();
    }

    registerPerson(newPerson) {
        // var person = {
        //     name: {
        //         first: newPerson.firstName,
        //         last: newPerson.lastName
        //     },
        //     street: newPerson.street,
        //     building: newPerson.building,
        //     postalCode: newPerson.postalCode,
        //     city: newPerson.city,
        //     phone: newPerson.phone,
        //     gender: newPerson.gender,
        //     rank: newPerson.rank,
        //     group: newPerson.group,
        //     buddy: newPerson.buddy,
        //     languages: newPerson.languages,
        //     dietary: newPerson.dietary,
        //     dietaryText: newPerson.dietaryText,
        //     socialPromotion: newPerson.socialPromotion,
        //     medicalText: newPerson.medicalText,
        //     otherText: newPerson.otherText
        // };
        // this.$log.debug(person);

        console.log(this.MapperService.mapVolunteer(newPerson));

        // this.$window.location.href = '/home';
    }

    $onInit() {
        angular.element('.modal-trigger').leanModal();
        angular.element('.datepicker').pickadate({
            selectMonths: true, // Creates a dropdown to control month
            selectYears: 15, // Creates a dropdown of 15 years to control year
            max: new Date(),
            onClose: this.setDateInModel(this)
        });
        // Fill data from localStorage
        // this.newPerson = this.StorageService.getUser();
        var user = this.StorageService.getUser();
        if (user) {
            this.newPerson = user;
        } else {
            this.newPerson = {};
        }
        this.errorMessages = document.getElementsByClassName("error");
        console.log(document.getElementsByClassName("error"));
    }

    setDateInModel() {
        if (this.newPerson != undefined) {
            var birthDate = $('#date_of_birth').val();
            this.newPerson.birthDate = new Date(birthDate);
        }
    }

    /**
     * Not the ideal lifecycle hook to save everything in localstorage, due to time constraints this will have to do for now.
     */

    $doCheck() {
        this.StorageService.saveUser(this.newPerson);


        // Couldn't find a better fix.
        this.setDateInModel();
    }

}


export var RegisterComponent = {
    template: require('./register.html'),
    controller: RegisterController,
    bindings: {
        type: '@'
    }
};
RegisterComponent.$inject = ['$log', '$window', 'StorageService', 'MapperService', 'AuthService'];
