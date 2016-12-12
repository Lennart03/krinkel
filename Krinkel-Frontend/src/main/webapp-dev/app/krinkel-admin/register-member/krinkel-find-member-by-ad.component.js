/**
 * Created by MHSBB71 on 8/12/2016.
 */

class FindByAdController {
    constructor($log, $window, $location, ChiroContactService) {
        this.$log = $log;
        this.$window = $window;
        this.$location = $location;
        this.ChiroContactService = ChiroContactService;

        this.participant = {};
    }

    findByAdNumber(adNumber) {
        this.participant = ChiroContactService.getRegistrationParticipant(adNumber);
    }
}
