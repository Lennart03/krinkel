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