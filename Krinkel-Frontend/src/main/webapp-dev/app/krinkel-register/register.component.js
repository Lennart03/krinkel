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
        //TODO remove next line
        this.$location.path('/success');

        if (this.type === 'volunteer') {
            this.KrinkelService.postVolunteer(this.MapperService.mapVolunteer(newPerson));
            return;
        }

        if (this.type === 'participant') {
            this.KrinkelService.postParticipant(this.MapperService.mapParticipant(newPerson));
            return;
        }

        // console.log(this.MapperService.mapVolunteer(newPerson));

        // this.$window.location.href = '/home';
    }

    $onInit() {
        if (this.AuthService.getLoggedinUser() == null) {
            this.$location.path('/login');
        }
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
            // this.newPerson.birthDate = "1995-11-24";
        }
        this.errorMessages = document.getElementsByClassName("error");


        // Initialize Materialize inputs
        // console.log(document.getElementsByClassName("error"));
    }


    isDisabled(form) {
        /**
         * This method is currently a workaround for the broken date. TODO: fix
         */
        // console.log(Object.keys(form.$error).length);
        if (Object.keys(form.$error).length == 1) {
            if (form.$error.hasOwnProperty("date")) {
                return false;
            }
        }
        if (form.$error) {
            return true;
        }
        // personForm.$invalid
    }

    setDateInModel() {
        //TODO FIX DATE
        // if (this.newPerson != undefined) {
        //     var birthDate = $('#date_of_birth').val();
        //     var birthDateInDate = new Date(birthDate);
        //     // this.newPerson.birthDate = new Date(birthDate).toISOString().split('T')[0];
        //     this.newPerson.birthDate = birthDate;
        //     // console.log(birthDateInDate.toDateString());
        //     console.log(birthDate);
        //     if (birthDate != "") {
        //         console.log(new Date(birthDate).toISOString().split('T')[0]);
        //     }
        //     // console.log(birthDateInDate.getFullYear() + "-" + (birthDateInDate.getMonth()+1) + "-" + birthDateInDate.getDate());
        //     // this.newPerson.birthDate = new Date(birthDate);
        // }
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
RegisterComponent.$inject = ['$log', '$window', 'StorageService', 'MapperService', 'AuthService', 'KrinkelService', '$location'];
