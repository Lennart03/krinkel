class KrinkelTicketController {
    constructor($location, $log, ticketService){
        this.$log = $log;
        this.$location = $location;
        this.ticketService = ticketService;
        this.isLoading = true;
    }

}

export var KrinkelTicketComponent = {
    template: require('./tickets.html'),
    controller: KrinkelSelectController
};

KrinkelTicketComponent.$inject = ['$location', '$log', 'TicketService'];
