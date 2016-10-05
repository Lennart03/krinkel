class RegisterController {

    constructor($log, $window, $scope, StorageService) {
        this.$log = $log;
        this.$window = $window;
        this.StorageService = StorageService;

        this.phoneNumberPattern = /^((\+|00)32\s?|0)(\d\s?\d{3}|\d{2}\s?\d{2})(\s?\d{2}){2}|((\+|00)32\s?|0)4(60|[789]\d)(\s?\d{2}){3}$/;
        this.campgrounds = ['Antwerpen', 'Kempen', 'Mechelen', 'Limburg', 'Leuven', 'Brussel', 'West-Vlaanderen', 'Heuvelland', 'Roeland', 'Reinaert', 'Nationaal', 'Internationaal'];

        $scope.$watch(this.newPerson, function () {
            console.log("test");
        });

    }

    registerPerson(newPerson) {
        var person = {
            name: {
                first: newPerson.firstName,
                last: newPerson.lastName
            },
            street: newPerson.street,
            building: newPerson.building,
            postalCode: newPerson.postalCode,
            city: newPerson.city,
            phone: newPerson.phone,
            gender: newPerson.gender,
            rank: newPerson.rank,
            group: newPerson.group,
            buddy: newPerson.buddy,
            languages: newPerson.languages,
            dietary: newPerson.dietary,
            dietaryText: newPerson.dietaryText,
            socialPromotion: newPerson.socialPromotion,
            medicalText: newPerson.medicalText,
            otherText: newPerson.otherText
        };
        this.$log.debug(person);
        this.$window.location.href = '/home';
    }
    $onInit() {
        angular.element('.modal-trigger').leanModal();
        angular.element('.datepicker').pickadate({
            selectMonths: true, // Creates a dropdown to control month
            selectYears: 15, // Creates a dropdown of 15 years to control year
            max: new Date()
        });
        angular.element('select').material_select();





        // Fill data from localStorage
        this.newPerson = this.StorageService.getUser();

    }

    /**
     * Not the ideal lifecycle hook to save everything in localstorage, due to time constraints this will have to do for now.
     */
    $doCheck() {
        this.StorageService.saveUser(this.newPerson);

        if ()
    }

}



export var RegisterComponent = {
    template: require('./register.html'),
    controller: RegisterController,
    bindings:{
      type:'@'
    }
};
RegisterComponent.$inject = ['$log', '$window', '$scope', 'StorageService'];
