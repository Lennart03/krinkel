export class KrinkelService {
    constructor($http, BASEURL) {
        this.$http = $http;
        this.BASEURL = BASEURL;
    }

    logIn(user, password) {
        return this.$http.get(`${this.BASEURL}/api/users?user=${user}&password=${password}`).then((resp) => {
            return resp.data;
        });
    }

    postVolunteer(user) {
        return this.$http.post(`${this.BASEURL}/api/volunteers`).then((resp) => {
            return resp.data;
        });
    }

    postParticipant(user) {
        return this.$http.post(`${this.BASEURL}/api/participants`).then((resp) => {
            return resp.data;
        });
    }

    getConfirmation(adNumber, token) {
        return this.$http.get(`${this.BASEURL}/api/confirmation?ad=${adNumber}&token=${token}`).then((resp) => {
            return resp.data;
        });
    }

    getVerbonden() {
        return this.$http.get(`${this.BASEURL}/api/units?verbond`).then((resp) => {
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

    // getMovies(title) {
    //   return this.$http.get(`${this.BASEURL}/online?title=${title}`).then((resp) => {
    //     return resp.data;
    //   });
    // }
    // postMovie(imdbid){
    //   return this.$http.post(`${this.BASEURL}`, {imdbId: imdbid}).then((resp) => {
    //     return resp.data;
    //   });
    // }
}

KrinkelService.$inject = ['$http', 'BASEURL'];
