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
                return resp;
            },
            (resp) => {
                return resp;
            }
        );
    }

    /*
    Returns true if not yet registered, returns false if is registered
     */
    checkIfParticipantIsNotYetAdded(adNumber)
    {
        console.log('CHECKING IF PARTICIPANT IS ADDED')
        return this.$http.get(`${this.BASEURL}/api/notYetAdded/${adNumber}`).then((resp) => {
                console.log('response from service: ' +resp);
                console.log('response.data from service: ' + resp.data);
                return resp.data;
            },
            (resp) => {
                popup();
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
        Materialize.toast('Er is iets fout gelopen bij het nakijken of de gebruiker al geregistreerd is, onze excuses.', 10000, 'red rounded');
    }
}
RegisterOtherMemberService.$inject = ['$http', 'BASEURL', '$window'];
