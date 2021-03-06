/**
 * Created by MHSBB71 on 8/12/2016.
 */

class FindByAdController {
    constructor(RegisterOtherMemberService, $location,$window) {
        this.RegisterOtherMemberService = RegisterOtherMemberService;
        this.$location = $location;
        this.$window = $window;
        this.adNumber = "";

    }

    findByAdNumber(adNumber) {
        this.adNumber=adNumber;
        this.RegisterOtherMemberService.getParticipantUsingAd(adNumber).then((resp) => {
                var part = resp.data;

                if (resp.size != 0) {
                    if (part.httpStatus == '404')
                    {
                        this.popup();
                    }
                    else
                    {
                        this.participant = {
                            adNumber: part.adnr,
                            firstName: part.firstName,
                            lastName: part.lastName,
                            email: part.email,
                            birthDate: part.birthDate,
                            phone: part.phoneNumber,
                            gender: part.gender,
                            address: part.address,
                            httpStatus: part.httpStatus
                        };
                        this.RegisterOtherMemberService.setParticipant(this.participant);
                        this.$location.path("/choose-registration-participant");
                    }

                }
            },
            (resp) => {

        });


    }

    popup() {
        Materialize.toast('Geen gebruiker gevonden voor ingegeven AD nummer '+ this.adNumber, 10000, 'red rounded');
    }
}



export var FindByAdComponent = {
    template: require('./krinkel-find-member-by-ad.html'),
    controller: FindByAdController
};


FindByAdController.$inject = ['RegisterOtherMemberService', '$location','$window'];
