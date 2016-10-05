/*@ngInject*/
class RegisterController {

    constructor(AuthService, $log, $window, $location) {
        this.AuthService = AuthService;
        this.$location = $location;
        this.$log = $log;
        this.$window = $window;
        this.phoneNumberPattern = /^((\+|00)32\s?|0)(\d\s?\d{3}|\d{2}\s?\d{2})(\s?\d{2}){2}|((\+|00)32\s?|0)4(60|[789]\d)(\s?\d{2}){3}$/;
        this.campgrounds = ['Antwerpen', 'Kempen', 'Mechelen', 'Limburg', 'Leuven', 'Brussel', 'West-Vlaanderen', 'Heuvelland', 'Roeland', 'Reinaert', 'Nationaal', 'Internationaal'];
        angular.element('select').material_select();
    }

    $onInit() {
        if (this.AuthService.getLoggedinUser() == null) {
            this.$location.path('/login');
        }

        this.optionsStreet = {
            country: 'be',
            types: 'address'


        };

        this.optionsStad = {
            country: 'be',
            types: '(cities)'
        };

        this.optionsPostalCode = {
            country: 'be',
            types: '(regions)'


        };

        this.details = '';

        angular.element('.modal-trigger').leanModal();
        angular.element('.datepicker').pickadate({
            selectMonths: true, // Creates a dropdown to control month
            selectYears: 15 // Creates a dropdown of 15 years to control year
        });
        angular.element('select').material_select();

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


}


export var RegisterComponent = {
    template: require('./register.html'),
    controller: RegisterController,
    bindings: {
        type: '@'
    }
}
RegisterComponent.$inject = ['AuthService', '$log', '$window', '$location'];
