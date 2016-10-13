export class KrinkelService {
  constructor($http, BASEURL) {
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
          console.debug("Printing volunteer POST response");
          console.debug(resp);
          console.debug(resp.headers);
          console.debug(resp.headers().location);
          return resp;
      });
  }
  postParticipant(user) {
      return this.$http.post(`${this.BASEURL}/api/participants`, user).then((resp) => {
          console.debug("Printing participant POST response");
          console.debug(resp.headers);
          console.debug(resp.headers().location);
          return resp;
      });
  }
  getConfirmation(adNumber, token){
      return this.$http.get(`${this.BASEURL}/api/confirmation?ad=${adNumber}&token=${token}`).then((resp) => {
          return resp.data;
      });
  }
  // getColleagues(adNumber){
  //     return this.$http.get(`${this.BASEURL}/api/colleagues?ad=${adNumber}`).then((resp) => {
  //         return resp.data;
  //     })
  // }

    getColleagues(stamnummer){
        return this.$http.get(`${this.BASEURL}/api/units/${stamnummer}/users`).then((resp) => {
            return resp.data;
        })
    }

  getCurrentUserDetails(adNumber){
      //`${this.BASEURL}/api/users/${adNumber}`
      console.log("adNumber:");
      console.log(adNumber);
      return this.$http.get(this.BASEURL + "/api/users/" + adNumber).then((resp) => {
          return resp.data;
      }, (resp) => {
          console.log("logging resp");
          console.log(resp);
      })
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

    getUsersOfUnit(stamNummer){
        return this.$http.get(`${this.BASEURL}/api/participants/info/${stamNummer}`).then((resp) => {
            return resp.data;
        })
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
