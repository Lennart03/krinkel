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

    popup() {
        Materialize.toast('Sessie verlopen, binnen 10 seconden herstart de applicatie', 10000, 'red rounded');
        setTimeout(() => {
            this.$window.location.reload();
        }, 10000);
    }
}
RegisterOtherMemberService.$inject = ['$http', 'BASEURL', '$window'];
