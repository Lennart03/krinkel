/**
 * Created by MHSBB71 on 8/12/2016.
 */

class FindByAdController {
    constructor(RegisterOtherMemberService, $location) {
        this.RegisterOtherMemberService = RegisterOtherMemberService;
        this.$location = $location;

        this.adNumber = "";
    }

    findByAdNumber(adNumber) {
        this.RegisterOtherMemberService.getParticipantUsingAd(adNumber).then((resp) => {
            if (resp.size != 0) {
                this.participant = {
                    adNumber: resp.adNumber,
                    firstName: resp.firstName,
                    lastName: resp.lastName,
                    email: resp.email,
                    birthDate: resp.birthdate,
                    phone: resp.phoneNumber,
                    gender: resp.gender,
                    address: resp.address
                };
                this.RegisterOtherMemberService.setParticipant(this.participant);
            }
        });

        this.$location.path("/choose-registration-participant");
    }
}

export var FindByAdComponent = {
    template: require('./krinkel-find-member-by-ad.html'),
    controller: FindByAdController
};


FindByAdController.$inject = ['RegisterOtherMemberService', '$location'];
