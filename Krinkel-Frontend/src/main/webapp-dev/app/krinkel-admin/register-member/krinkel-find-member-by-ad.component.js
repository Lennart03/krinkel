/**
 * Created by TSOAV51 on 8/12/2016.
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
        export
    var KrinkelTestComponent = {
        template: require('./krinkel-find-member-by-ad.html'),
        controller: FindByAdController
    };

KrinkelTestComponent.$inject = ['ChiroContactService'];
