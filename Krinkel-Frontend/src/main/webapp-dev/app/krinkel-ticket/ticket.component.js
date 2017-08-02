class KrinkelTicketController {

    constructor($location, $log, ticketService, authService) {
        this.$log = $log;
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
    }

    $onInit() {

        if (this.AuthService.getLoggedinUser() === null) {
            this.$location.path('/');
            return;
        }

        this.optionsBonnen = [5, 10, 15, 20, 25, 50, 75, 100, 125, 150, 175, 200];

        let thiz = this;
        this.isLoading = true;
        this.ticketService.getUserAddress().then(resp => {
            console.log(resp);
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

            console.log(thiz.person);
        });

        this.ticketService.getTrainTicketPrices().then((resp) => {
            // TODO: check content of the response.
            // TODO: save the ticket prices in the component
        });

        this.ticketService.getCouponPrices().then((resp) => {
            // TODO: check content of the response.
            // TODO: save the ticket prices in the component
        });

    }

    purchaseTrainTickets() {
        let ticketdto = {
            type: "TREIN"
            // TODO: add following properties to the DTO
            /*
            ticketAmount
            timesOrdered
            firstName
            lastName
            email
            phoneNumber
            address {
                street
                postalCode
                houseNumber
                city
            }
             */
        };
        this.ticketService.submitPurchase(ticketdto).then((resp) => {
            // TODO: Do something with this response?
        })
    }

    prepurchase(ticketPurchase) {
        return true;
    }

    initModal6(valid) {
        this.validateNow = true;
        if (valid) {
            $('#modal6').openModal();
        }
    }

    trainTickets() {
        this.orderingTrainTickets = true;
    }

    coupons() {
        this.orderingTrainTickets = false;
    }


}

export var KrinkelTicketComponent = {
    template: require('./tickets.html'),
    controller: KrinkelTicketController
};

KrinkelTicketComponent.$inject = ['$location', '$log', 'TicketService', 'AuthService'];
