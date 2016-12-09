/**
 * Created by MHSBB71 on 8/12/2016.
 */

export class RegisterOtherMemberService {
    constructor($http, BASEURL, $window) {
        this.$http = $http;
        this.BASEURL = BASEURL;
        this.$window = $window;

        this.boolSubscribeMember = false;
        this.boolSubscribeColleague = false;

    }

    getParticipantUsingAd(adNumber) {
        return this.$http.get(`${this.BASEURL}/api/participant/${adNumber}`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popup();
            }
        );
    }

    setParticipant(participant) {
        this.participant = participant;
    }

    getParticipant() {
        return this.participant;
    }

    setSubscribeMember(subscribeMember) {
        this.boolSubscribeMember = subscribeMember;
    }

    setSubscribeColleague(subscribeColleague) {
        this.boolSubscribeColleague = subscribeColleague;
    }

    subscribeMember() {
        return this.boolSubscribeMember;
    }

    subscribeColleague() {
        return this.boolSubscribeColleague;
    }

    popup() {
        Materialize.toast('Sessie verlopen, binnen 10 seconden herstart de applicatie', 10000, 'red rounded');
        setTimeout(() => {
            this.$window.location.reload();
        }, 10000);
    }
}
RegisterOtherMemberService.$inject = ['$http', 'BASEURL', '$window'];
