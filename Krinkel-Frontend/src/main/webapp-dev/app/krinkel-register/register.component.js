class RegisterController {

    constructor($log, $window, StorageService, MapperService, AuthService, KrinkelService, $location, SelectService, RegisterOtherMemberService) {
        this.$log = $log;
        this.$window = $window;
        this.StorageService = StorageService;
        this.MapperService = MapperService;
        this.AuthService = AuthService;
        this.KrinkelService = KrinkelService;
        this.$location = $location;
        this.SelectService = SelectService;
        this.RegisterOtherMemberService = RegisterOtherMemberService;

        this.phoneNumberPattern = /^((\+|00)32\s?|0)(\d\s?\d{3}|\d{2}\s?\d{2})(\s?\d{2}){2}|((\+|00)32\s?|0)4(60|[789]\d)(\s?\d{2}){3}$/;
        this.birthdatePattern = /^(?:(?:31(-)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(-)(?:0?[1,3-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(-)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(-)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$/;
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

        this.validateNow = false;
    }

    registerPerson(newPerson) {
        this.newPerson.city = this.details3.vicinity;
        this.newPerson.postalCode = this.details2.name;
        this.newPerson.street = this.details.address_components[0].long_name;

        if(this.user === "admin") {
            if (this.type === 'volunteer') {
                var thiz = this;
                this.KrinkelService.postVolunteerByAdmin(this.MapperService.mapVolunteerByAdmin(newPerson)).then(function (resp) {
                    thiz.dataIsRemoved = true;
                    thiz.StorageService.removeUser();
                    thiz.$location.path("/admin");

                });
                this.KrinkelService.popupForAdmin();
                return;
            }

            if (this.type === 'participant') {
                var thiz = this;
                this.KrinkelService.postParticipantByAdmin(this.MapperService.mapParticipantByAdmin(newPerson)).then(function (resp) {
                    thiz.dataIsRemoved = true;
                    thiz.StorageService.removeUser();
                    thiz.SelectService.setSelectedFlag(false);
                    thiz.$location.path("/admin");

                });
                this.KrinkelService.popupForAdmin();
                return;
            }



        } else {
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
    }

    prefillColleague() {
        let colleague = this.SelectService.getColleague();
        let loggedInUser = this.AuthService.getLoggedinUser();
        this.prefillWithAdNumber(colleague.adnr, loggedInUser.email);
        this.SelectService.setSelectedFlag(true);
    }

    prefillWithAdNumber(adNumber, emailSubscriber){ //second var is optional, as it's only required when subscribing a colleague.

        this.KrinkelService.getContactFromChiro(adNumber).then((resp) => {
            if (resp) {
                let chiroContact = resp[0];
                this.newPerson = {
                    adNumber: adNumber,
                    job: "Aanbod nationale kampgrond",
                    firstName: chiroContact.first_name || "", //this pretty much means use the var if it's not null/undefined, else use an empty string
                    lastName: chiroContact.last_name || "",
                    email: chiroContact.email || "",
                    birthDate: chiroContact.birth_date ? chiroContact.birth_date.split("-").reverse().join("-") : "",
                    emailSubscriber: emailSubscriber || "",
                    phone: chiroContact.phone ? chiroContact.phone.replace('-', '') : "",
                    gender: chiroContact.gender_id || "",
                    rank: chiroContact.afdeling.toUpperCase() || ""
                };
                this.KrinkelService.getPloegen(adNumber).then((resp) => {
                    this.options = [];
                    resp.forEach((r) => {
                        this.options.push(JSON.parse(r));
                    });
                    this.newPerson.group = this.options[0].stamnr;
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
    //todo: fix the inconsistencies betwee the name for the AD number
    prefillSelf() {
        let loggedInUser = this.AuthService.getLoggedinUser();
        console.log(loggedInUser);
        this.prefillWithAdNumber(loggedInUser.adnummer);
    }

    /*
    * wanneer de admin een deelnemer wil toevoegen
     */
    prefillMember() {
        let participant = this.RegisterOtherMemberService.getParticipant();
        console.log(participant);
        this.prefillWithAdNumber(participant.adNumber);
    }
    $onInit() {
        if (this.AuthService.getLoggedinUser() == null) {
            this.$location.path('/');
        }


        /**
         * Prefilling the form when subscribing others
         */
        if(this.RegisterOtherMemberService.subscribeMember()) {
            this.prefillMember();
            this.user = "admin";
            this.RegisterOtherMemberService.setSubscribeMember(false);
        } else if(this.RegisterOtherMemberService.subscribeColleague()) {
            this.prefillMember();
            this.user = "admin";
            this.RegisterOtherMemberService.setSubscribeColleague(false);
        } else if (this.SelectService.getColleague() !== undefined) {
            this.prefillColleague();
        } else {
            this.prefillSelf();
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

    addToBasket(person){
        let perzon = person;
        perzon.city = this.details3.vicinity;
        perzon.postalCode = this.details2.name;
        perzon.street = this.details.address_components[0].long_name;

        let mappedPerson = this.MapperService.mapParticipant(perzon);
        //add person to cart using service
        this.KrinkelService.addPersonToBasket(mappedPerson).then(() => {
            this.$location.path("/cart");
        }, () => {
            Materialize.toast("Persoon reeds in krinkelkar", 5000);
            this.$location.path("/cart");
        });
    }

    /**
     * Not the ideal lifecycle hook to save everything in localstorage, due to time constraints this will have to do for now.
     */

    $doCheck() {
        if (!this.dataIsRemoved) {
            this.StorageService.saveUser(this.newPerson);
        }
    }

    initVoorwaardenModal() {
        $('#modal2').openModal();
    }

    initModal4(form) {
        this.validateNow = true;
        if(form) {
            $('#modal4').openModal();
        }
    }

    initModal5(form) {
        this.validateNow = true;
        if(form) {
            $('#modal5').openModal();
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


RegisterComponent.$inject = ['$log', '$window', 'StorageService', 'MapperService', 'AuthService', 'KrinkelService', '$location', 'SelectService', 'RegisterOtherMemberService'];


export var BasketComponent = {
    template:require('./addtobasket.html'),
    controller:RegisterController,
    bindings : {
        type:'@'
    }
};

BasketComponent.$inject = ['$log', '$window', 'StorageService', 'MapperService', 'AuthService', 'KrinkelService', '$location', 'SelectService', 'RegisterOtherMemberService'];

