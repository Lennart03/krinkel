/**
 * Created by MHSBB71 on 8/12/2016.
 */

export class RegisterOtherMemberService {
    constructor($http, BASEURL, $window) {
        console.log("begin");
        this.$http = $http;
        this.BASEURL = BASEURL;
        this.$window = $window;

        console.log("end");
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

    setParticipant(participant) {
        this.participant = participant;
    }

    getParticipant() {
        return this.participant;
    }

    popup() {
        Materialize.toast('Sessie verlopen, binnen 10 seconden herstart de applicatie', 10000, 'red rounded');
        setTimeout(() => {
            this.$window.location.reload();
        }, 10000);
    }
}
RegisterOtherMemberService.$inject = ['$http', 'BASEURL', '$window'];
