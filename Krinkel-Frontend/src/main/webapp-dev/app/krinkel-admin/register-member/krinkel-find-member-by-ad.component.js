/**
 * Created by MHSBB71 on 8/12/2016.
 */

class FindByAdController {
    constructor(ChiroContactService) {
        this.ChiroContactService = ChiroContactService;

        this.participant = {};
    }

    findByAdNumber(adNumber) {
        var part = this.ChiroContactService.getRegistrationParticipant(adNumber);

        this.participant = {
            adNumber: part.adNumber,
            firstName: part.firstName,
            lastName: part.lastName,
            email: part.email,
            birthDate: part.birthdate,
            phone: part.phoneNumber.replace('-', ''),
            gender: part.gender,
            address: part.address
        };
    }
}

export var FindByAdComponent = {
    template: require('./krinkel-find-member-by-ad.html'),
    controller: FindByAdController
};


FindByAdController.$inject = ['ChiroContactService'];
