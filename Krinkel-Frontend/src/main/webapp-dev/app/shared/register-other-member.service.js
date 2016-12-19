/**
 * Created by MHSBB71 on 8/12/2016.
 */

export class RegisterOtherMemberService {
    constructor($http, BASEURL, $window) {
        this.$http = $http;
        this.BASEURL = BASEURL;
        this.$window = $window;


        console.log("end");

        this.boolSubscribeMember = false;
        this.boolSubscribeColleague = false;


    }

    getParticipantUsingAd(adNumber) {
        return this.$http.get(`${this.BASEURL}/api/participant/${adNumber}`).then((resp) => {
                console.log('response from service: ' +resp);
                console.log('response.data from service: ' + JSON.stringify(resp.data));
                return resp;
            },
            (resp) => {
                console.log('response error from service ' +resp);
                console.log('response error from service ' +resp.data);
                return resp;
            }
        );
    }

    checkIfParticipantIsAdded(adNumber)
    {
        return this.$http.get(`${this.BASEURL}/api/notYetAdded?adNumber=${adNumber}`).then((resp) => {
                console.log('response from service: ' +resp);
                console.log('response.data from service: ' + JSON.stringify(resp.data));
                return resp;
            },
            (resp) => {
                console.log('response error from service ' +resp);
                console.log('response error from service ' +resp.data);
                return resp;
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
