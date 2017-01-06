    /**
 * Created by MHSBB71 on 8/12/2016.
 */
class ChooseRegistrationController {
    constructor(KrinkelService, RegisterOtherMemberService, $location) {
        this.KrinkelService = KrinkelService;
        this.RegisterOtherMemberService = RegisterOtherMemberService;
        this.$location = $location;
        var adNumber1 = this.RegisterOtherMemberService.getParticipant().adNumber;
        var response2 = this.RegisterOtherMemberService.checkIfParticipantIsNotYetAdded(adNumber1);
        this.isRegistered = false;
        response2.then((participantNotYetAdded) => {
            console.log('PARTICIPANTSISADDED: ' +participantNotYetAdded)
            if(!participantNotYetAdded) {
                this.isRegistered = true;
            }
        });
    }

    getParticipant() {
        return this.RegisterOtherMemberService.getParticipant();
    }

    getNameParticipant() {
        var part = this.RegisterOtherMemberService.getParticipant();
        return part.lastName + "  " + part.firstName;
    }

    subscribeMember() {
        var adNumber = this.RegisterOtherMemberService.getParticipant().adNumber;
        var response = this.RegisterOtherMemberService.checkIfParticipantIsNotYetAdded(adNumber);
        response.then((participantNotYetAdded) => {
            if(participantNotYetAdded) {
                this.RegisterOtherMemberService.setSubscribeMember(true);
                this.$location.path("/register-participant");
            }
            else{
                // Deze code wordt niet meer uitgevoerd. Indien de persoon al is geregistreerd dan komt er op de html pagina een boodschap die dit vermeld en worden te knoppen om in te schrijven niet getoond.
                Materialize.toast('Gebruiker met AD nummer '+ adNumber + ' is reeds ingeschreven.', 10000, 'red rounded');
            }
        });
    }

    subscribeColleague() {
        var adNumber = this.RegisterOtherMemberService.getParticipant().adNumber;
        console.log('SUBSCRIBING VOLUNTEER with AD nummer: ' + adNumber)
        var response = this.RegisterOtherMemberService.checkIfParticipantIsNotYetAdded(adNumber);
        response.then((participantNotYetAdded) => {
            console.log('PARTICIPANTSISADDED: ' +participantNotYetAdded)
            if(participantNotYetAdded) {
                this.RegisterOtherMemberService.setSubscribeColleague(true);
                this.$location.path("/register-volunteer");
            }
            else{
                Materialize.toast('Gebruiker met AD nummer '+ adNumber + ' is reeds ingeschreven.', 10000, 'red rounded');
            }
        });
    }

}

export var ChooseRegistrationComponent = {
    template: require('./krinkel-choose-registration-method.html'),
    controller: ChooseRegistrationController
};


ChooseRegistrationController.$inject = ['KrinkelService', 'RegisterOtherMemberService', '$location'];
