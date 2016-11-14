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
        this.birthdatePattern = /^(\d{4})(\/|-)(\d{1,2})(\/|-)(\d{1,2})$/;
        this.postalcodePattern = /^(\d{4})$/;
        this.emailPattern = /^[-a-z0-9~!$%^&*_=+}{\'?]+(\.[-a-z0-9~!$%^&*_=+}{\'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$/i;
        this.campgrounds = ['Antwerpen', 'Kempen', 'Mechelen', 'Limburg', 'Leuven', 'Brussel', 'West-Vlaanderen', 'Heuvelland', 'Roeland', 'Reinaert', 'Nationaal', 'Internationaal'];
        angular.element('select').material_select();

        this.dataIsRemoved = false;
        this.voorwaarden = false;
        window.scrollTo(0, 0);


        if (this.SelectService.getSelectedFlag()) {
            this.SelectService.setColleague(undefined);
            this.SelectService.setSelectedFlag(false);
        }
        this.details3 = {};
        this.details2 = {};
        this.details = {};
    }

    clearPostCodeAndCityNameFields() {

        if (this.details3 != null || this.details2 != null || this.details != null) {

            this.details3 = {};
            this.details2 = {};

        }
    }

    extractFromAdress(components) {
        if (this.details3.vicinity != null || this.details2.name != null) {
            return;
        }

        if (components != null) {
            for (var i = 0; i < components.length; i++)
                for (var j = 0; j < components[i].types.length; j++) {
                    if (components[i].types[j] == 'postal_code') {
                        //  console.debug(components[i].long_name);
                        this.details2 = {
                            name: components[i].long_name
                        };
                    }
                    // console.debug(components[i].types[j]);
                    if (components[i].types[j] == 'locality') {
                        //console.debug(components[i].short_name);

                        this.details3 = {
                            vicinity: components[i].short_name
                        };
                    }

                }
        }
        return "";
    }

    registerPerson(newPerson) {
        this.newPerson.city = this.details3.vicinity;
        this.newPerson.postalCode = this.details2.name;
        this.newPerson.street = this.details.address_components[0].long_name;
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
                thiz.SelectService.setSelectedFlag(false);
                thiz.$window.location.href = resp.headers().location;
            });
            return;
        }
    }

    prefillColleague() {
        var colleague = this.SelectService.getColleague();
        var loggedInUser = this.AuthService.getLoggedinUser();

        this.newPerson = {
            adNumber: colleague.adnummer,
            job: "Aanbod nationale kampgrond",
            firstName: colleague.first_name,
            lastName: colleague.last_name,
            email: colleague.email,
            birthDate: colleague.birth_date,
            phone: colleague.phone.replace('-', ''),
            emailSubscriber: loggedInUser.email,
        };

        this.KrinkelService.getPloegen(colleague.adnr).then((resp) => {
            this.options = resp;
            this.newPerson.group = this.options[0];
        });

        this.details2.name = colleague.postal_code;


        this.details.address_components = [];
        this.details.address_components.push({
            long_name: colleague.street_address
        });


        this.details3.vicinity = colleague.city;


        this.SelectService.setSelectedFlag(true);
    }

    prefillSelf() {
        var loggedInUser = this.AuthService.getLoggedinUser();
        this.KrinkelService.getContactFromChiro(loggedInUser.adnummer).then((resp) => {
            var chiroContact = resp[0];
            if (resp.size != 0) {
                this.newPerson = {
                    adNumber: loggedInUser.adnummer,
                    job: "Aanbod nationale kampgrond",
                    firstName: chiroContact.first_name,
                    lastName: chiroContact.last_name,
                    email: chiroContact.email,
                    birthDate: chiroContact.birth_date,
                    phone: chiroContact.phone.replace('-', ''),
                };

                this.KrinkelService.getPloegen(loggedInUser.adnummer).then((resp) => {
                    this.options = resp;
                    this.newPerson.group = this.options[0];
                });
                this.details2.name = chiroContact.postal_code;


                this.details.address_components = [];
                this.details.address_components.push({
                    long_name: chiroContact.street_address
                });


                this.details3.vicinity = chiroContact.city;

            }
        });
    }


    $onInit() {
        if (this.AuthService.getLoggedinUser() == null) {
            this.$location.path('/');
        }


        /**
         * Prefilling the form when subscribing others
         */
        if (this.SelectService.getColleague() !== undefined) {
            this.prefillColleague();

        } else {

            var user = this.StorageService.getUser();
            if (user && user.email === this.AuthService.getLoggedinUser().email) {
                this.newPerson = user;
            } else {
                /**
                 * Prefilling the form when subscribing yourself
                 */
                this.prefillSelf();

            }
        }

        this.optionsStreet = {
            country: 'be',
            types: 'address'


        };

        this.optionsStad = {
            country: 'be',
            types: '(regions)'
        };

        this.optionsPostalCode = {
            country: 'be',
            types: '(regions)'
        };


        angular.element('.modal-trigger').leanModal();
        // Fill data from localStorage
        // this.newPerson = this.StorageService.getUser();

        this.errorMessages = document.getElementsByClassName("error");
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
        if (!this.dataIsRemoved) {
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
