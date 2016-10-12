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
  getColleagues(adNumber){
      return this.$http.get(`${this.BASEURL}/api/colleagues?ad=${adNumber}`).then((resp) => {
          return resp.data;
      })
  }
  getCurrentUserDetails(adNumber){
      return this.$http.get(`${this.BASEURL}/api/users/${adNumber}`).then((resp) => {
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
