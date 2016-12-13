    /**
 * Created by MHSBB71 on 8/12/2016.
 */
class ChooseRegistrationController {
    constructor(RegisterOtherMemberService, $location) {
        this.RegisterOtherMemberService = RegisterOtherMemberService;
        this.$location = $location;
    }

    getParticipant() {
        return this.RegisterOtherMemberService.getParticipant();
    }

    getNameParticipant() {
        var part = this.RegisterOtherMemberService.getParticipant();
        return part.lastName + "  " + part.firstName;
    }

    subscribeMember() {
        this.RegisterOtherMemberService.setSubscribeMember(true);
        this.$location.path("/register-participant");
    }

    subscribeColleague() {
        this.RegisterOtherMemberService.setSubscribeColleague(true);
        this.$location.path("/register-volunteer");
    }

}

export var ChooseRegistrationComponent = {
    template: require('./krinkel-choose-registration-method.html'),
    controller: ChooseRegistrationController
};


ChooseRegistrationController.$inject = ['RegisterOtherMemberService', '$location'];
