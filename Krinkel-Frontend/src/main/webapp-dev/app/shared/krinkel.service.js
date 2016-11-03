export class KrinkelService {
    constructor($http, $log, BASEURL) {
        this.$log = $log;
        this.$http = $http;
        this.BASEURL = BASEURL;
    }

    getCasUrl(){
        return this.$http.get(`${this.BASEURL}/api/casurl`).then((resp) => {
             return resp.data;
        });
    }

    logIn(user, password) {
        return this.$http.get(`${this.BASEURL}/api/users?user=${user}&password=${password}`).then((resp) => {
            return resp.data;
        });
    }

    postVolunteer(user) {
        return this.$http.post(`${this.BASEURL}/api/volunteers`, user).then((resp) => {
            this.$log.debug("Printing volunteer POST response");
            this.$log.debug(resp);
            this.$log.debug(resp.headers);
            this.$log.debug(resp.headers().location);
            return resp;
        });
    }

    postParticipant(user) {
        return this.$http.post(`${this.BASEURL}/api/participants`, user).then((resp) => {
            this.$log.debug("Printing participant POST response");
            this.$log.debug(resp.headers);
            this.$log.debug(resp.headers().location);
            return resp;
        });
    }

    getConfirmation(adNumber, token) {
        return this.$http.get(`${this.BASEURL}/api/confirmation?ad=${adNumber}&token=${token}`).then((resp) => {
            return resp.data;
        });
    }

    getColleagues(){
        return this.$http.get(`${this.BASEURL}/api/colleagues`).then((resp) => {
            return resp.data;
        })
    }

    getCurrentUserDetails(adNumber) {
        return this.$http.get(this.BASEURL + "/api/users/" + adNumber)
                         .then((resp) => {
                                    return resp.data;
                                }, (resp) => {
                                    this.$log.debug("logging resp");
                                    this.$log.debug(resp);
                                });
    }

    getVerbonden() {
        return this.$http.get(`${this.BASEURL}/api/units?verbond`)
                         .then((resp) => {
                            return resp.data;
                         });
    }

    getGewestenForVerbond(verbondNummer) {
        return this.$http.get(`${this.BASEURL}/api/units/${verbondNummer}`).then((resp) => {
            return resp.data;
        });
    }

    getParticipantsForUnit(stamNummer) {
        return this.$http.get(`${this.BASEURL}/api/participants/${stamNummer}`).then((resp) => {
            return resp.data;
        });
    }

    getUsersOfUnit(stamNummer){
        return this.$http.get(`${this.BASEURL}/api/participants/info/${stamNummer}`).then((resp) => {
            return resp.data;
        })
    }

    getGraphSunInfo() {
        return this.$http.get(`${this.BASEURL}/api/graph/sun`).then((resp) => {
            return resp.data;
        });
    }

    getGraphStatusInfo() {
        return this.$http.get(`${this.BASEURL}/api/graph/status`).then((resp) => {
           return resp.data;
        });
    }

    getGraphLoginInfo() {
        return this.$http.get(`${this.BASEURL}/api/graph/login`).then((resp) => {
            return resp.data;
        });
    }

    getContactFromChiro(adNumber) {
        return this.$http.get(`${this.BASEURL}/api/contact/` + adNumber).then((resp) => {
            return resp.data.values;
        });
    }
}

KrinkelService.$inject = ['$http', '$log', 'BASEURL'];
