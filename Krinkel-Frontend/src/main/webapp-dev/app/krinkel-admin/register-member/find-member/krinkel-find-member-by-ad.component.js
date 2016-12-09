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
            var part = resp;
            console.log(part);
            if (resp.size != 0) {
                this.participant = {
                    adNumber: part.adNumber,
                    firstName: part.firstName,
                    lastName: part.lastName,
                    email: part.email,
                    birthDate: part.birthdate,
                    phone: part.phoneNumber,
                    gender: part.gender,
                    address: part.address
                };
                console.log(this.participant.lastName);
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
