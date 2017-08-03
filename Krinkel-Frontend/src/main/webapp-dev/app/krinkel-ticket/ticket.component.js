class KrinkelTicketController {

    constructor($location, $window, $log, ticketService, authService) {
        this.$log = $log;
        this.$window = $window;
        this.$location = $location;
        this.ticketService = ticketService;
        this.AuthService = authService;
        this.isLoading = true;
        this.disableEverything = true;
        this.orderingTrainTickets = true;
        this.phoneNumberPattern = /(\+324|04|00324)([0-9]{8})|(0\d{1,2}([0-9]{6}))$/;
        this.birthdatePattern = /^(?:(?:31(-)(?:0?[13578]|1[02]))\1|(?:(?:29|30)(-)(?:0?[1,3-9]|1[0-2])\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:29(-)0?2\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\d|2[0-8])(-)(?:(?:0?[1-9])|(?:1[0-2]))\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$/;
        this.postalcodePattern = /^(\d{4})$/;
        this.emailPattern = /^[-a-z0-9~!$%^&*_=+}{\'?]+(\.[-a-z0-9~!$%^&*_=+}{\'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$/i;
        this.inputPattern = /^(.{0,256})$/;

        // Parameters for ticket ordering
        this.numberOfTrainTickets = 0;
        this.totalAmountToPayForTrainTickets = 0;
        this.trainTicketPrices = [];
        this.couponPrices = [];
    }

    $onInit() {

        if (this.AuthService.getLoggedinUser() === null) {
            this.$location.path('/');
            return;
        }

        this.optionsBonnen = [5, 10, 15, 20, 25, 50, 75, 100, 125, 150, 175, 200];

        let thiz = this;
        this.isLoading = true;
        this.ticketService.getParticipantInfo().then(resp => {
            thiz.isLoading = false;
            if (resp.status === 404) {
                //No user details found
                thiz.person = {
                    test: thiz.AuthService.getUserDetails()
                };
                thiz.disableEverything = true;
            } else if (resp.status === 200) {
                thiz.disableEverything = false;
                thiz.person = resp.person;
            }
        });

        this.ticketService.getTrainTicketPrices().then((resp) => {
            this.trainTicketPrices = resp;
        });

        this.ticketService.getCouponPrices().then((resp) => {
            this.couponPrices = resp;
        });

    }

    purchaseTrainTickets() {
        let trainDTO = {
            type: "TREIN",
            ticketAmount: 1,
            timesOrdered: this.numberOfTrainTickets,
            firstName: this.person.firstName,
            lastName: this.person.lastName,
            email: this.person.email,
            phoneNumber: this.person.phoneNumber,
            address: this.person.address
        };
        var thiz = this;
        this.ticketService.submitPurchase(trainDTO).then((resp) => {
            console.log(resp);
            thiz.$window.location.href = resp.headers().location;
            return;
        })
    }

    updatePriceToPayForTrainTickets() {
        if (this.numberOfTrainTickets == 0) {
            this.totalAmountToPayForTrainTickets = 0;
        } else {

            this.totalAmountToPayForTrainTickets = (this.numberOfTrainTickets * this.trainTicketPrices[0].price) + this.trainTicketPrices[0].transportationCost;
        }
    }

    prepurchase(ticketPurchase) {
        return true;
    }

    initTrainModal6(valid) {
        console.log("initModal");
        console.log(valid);
        if (valid) {
            this.trainModal6Address = this.person.address.street + " " + this.person.address.houseNumber + " - " + this.person.address.postalCode + " " + this.person.address.city;
            $('#trainModal6').openModal();
        }
    }

    trainTickets() {
        this.orderingTrainTickets = true;
    }

    coupons() {
        this.orderingTrainTickets = false;
    }

    functionCallAfterDOMRender() {
        try {
            Materialize.updateTextFields();
        } catch (exception) {
        }
    }


}

export var KrinkelTicketComponent = {
    template: require('./tickets.html'),
    controller: KrinkelTicketController
};

KrinkelTicketComponent.$inject = ['$location', '$window', '$log', 'TicketService', 'AuthService'];
