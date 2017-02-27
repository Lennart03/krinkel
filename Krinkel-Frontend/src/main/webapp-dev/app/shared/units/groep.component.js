class GroepController {
    constructor(KrinkelService, $route, AuthService, $location, $routeParams, MapperService) {
        this.KrinkelService = KrinkelService;
        this.$route = $route;
        this.AuthService = AuthService;
        this.$location = $location;
        this.MapperService = MapperService;
        this.groepNr = $routeParams.groepNr;
        this.groepNaam = $routeParams.groepNaam;
        this.showParticipants = true;
        //console.log(this.lolo + 'groep.component.js says hi!');
        this.initSelectPayStatus();
    }

    checkIfNationaal(stamnummer){
        return this.nationaalStamnummers.indexOf(stamnummer) > -1;
    }
    checkIfInternationaal(stamnummer){
        return this.internationaalStamnummer === stamnummer;
    }

    $onInit() {
        this.nationaalStamnummers = ['4AF', '4AG', '4AL', '4CF', '4CR', '4KL', '4WB', '4WJ', '4WK', '5AA', '5CA', '5CC', '5CD', '5CG', '5CJ', '5CL', '5CP', '5CV', '5DI', '5IG', '5IP', '5IT', '5KA', '5PA', '5PG', '5PM', '5PP', '5PV', '5RA', '5RD', '5RI', '5RP', '5RV', '5RW', '5SB', '5UG', '5UK', '5UL', '6KV', '7WD', '7WH', '7WK', '7WO', '7WW', '8BB', '8BC', '8BH', '8BR', '8BZ', '8DB', '8HD', '8HH', '8HK', '8HO', '8HW', '9KO'];
        this.internationaalStamnummer = '5DI';
        this.participants = [];
        this.volunteers = [];
        this.KrinkelService.getParticipantsList(this.groepNr).then((results) => {
            results.forEach((r) => {
                r.participant = "Deelnemer";
                r.eatinghabbit = this.MapperService.mapEatingHabbit(r.eatinghabbit);
                r.campGround = this.MapperService.mapCampground(r.campGround);
                this.participants.push(r);
            });
        });
        this.KrinkelService.getVolunteersList(this.groepNr).then((results) => {
            results.forEach((r) => {
                r.participant = "Vrijwilliger";
                r.function.preset = this.MapperService.mapVolunteerFunction(r.function.preset);
                r.eatinghabbit = this.MapperService.mapEatingHabbit(r.eatinghabbit);
                r.campGround = this.MapperService.mapCampground(r.campGround);
                if (r.function.preset == "CUSTOM"){
                    r.function.preset = r.function.other;
                }
                this.volunteers.push(r);
            })
        });
    }

    // changeBackgroundColorStatus(){
    //     $(document).ready(function(){
    //         //Iterate through each of the rows
    //         $('tr').each(function(){
    //             //Check the value of the last <td> element in the row (trimmed to ignore white-space)
    //             if($(this).find('td:last').text().trim() === "Geannuleerd"){
    //                 //Set the row to green
    //                 $(this).css('background','green');
    //             }
    //         });
    //     });
    // }

    /**
     *
     * FOR CHANGING PAYMENT STATUS AND CANCELLING PAYMENT
     *
     */

    //DONE: remove getElementById & setAttribute when 'reloading to same view' is implemented and uncomment the route.reload()
    putParticipantToCancelled(participantId) {
        if (participantId != null) {
            this.KrinkelService.putParticipantToCancelled(participantId).then((result) => {
                this.$route.reload();
            });

            // let id = document.getElementById(participantId);
            // id.setAttribute('style', 'display: none;');
        }
    }

    saveData(participantId, paymentStatus) {
        this.KrinkelService.updatePayment(participantId, paymentStatus);
    }

    initCancelModal(modalId) {
        let modalName = '#modal' + modalId;
        $(modalName).openModal();
    }

    switchShowParticipantsVolunteers() {
        this.showParticipants = ! this.showParticipants;
    }

    initSelectPayStatus() {
        this.statusses =  [
            { 'value': 'TO_BE_PAID', 'label': 'Onbetaald' },
            { 'value': 'PAID', 'label': 'Betaald' },
            { 'value': 'CONFIRMED', 'label': 'Bevestigd'}
        ]
    }
    /*
    To parse dates from a list of data objects
     */
    getDatesListFromList(list){
        if(list.length > 0) {
            function pad(s) {
                return (s < 10) ? '0' + s : s;
            };
            function formatDate(d){
                return [pad(d.getDate()), pad(d.getMonth()+1), d.getFullYear()].join('-');
            };
            var dates = "";
            for (var i = 0; i < list.length -1; i++) {
                var d = new Date(list[i].date);
                dates += formatDate(d) + ", ";
            }
            var d = new Date(list[list.length-1].date);
            dates += formatDate(d);
            return dates;
        }
        return "";
    }
}

export var GroepComponent = {
    template: require('./groep.html'),
    controller: GroepController
};

GroepComponent.$inject = ['KrinkelService', '$route', 'AuthService', '$location', '$routeParams', 'MapperService'];
