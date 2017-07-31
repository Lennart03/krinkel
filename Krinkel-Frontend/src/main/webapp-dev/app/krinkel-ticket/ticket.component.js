class KrinkelTicketController {

    constructor($location, $log, ticketService, authService) {
        this.$log = $log;
        this.$location = $location;
        this.ticketService = ticketService;
        this.AuthService = authService;
        this.isLoading = true;
    }

    $onInit() {
        if (this.AuthService.getLoggedinUser() === null) {
            this.$location.path('/');
            return;
        }

        this.optionsBonnen = [5, 10, 15, 20, 25, 50, 75, 100, 125, 150, 175, 200];
    }

    prepurchase(ticketPurchase) {

    }

    initModal6(valid) {
        this.validateNow = true;
        if (valid) {
            $('#modal6').openModal();
        }
    }
}

export var KrinkelTicketComponent = {
    template: require('./tickets.html'),
    controller: KrinkelTicketController
};

KrinkelTicketComponent.$inject = ['$location', '$log', 'TicketService', 'AuthService'];
