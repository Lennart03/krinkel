export class MoviesService {
  constructor($http, BASEURL) {
    this.$http = $http;
    this.BASEURL = BASEURL;
  }
  getMovies(title) {
    return this.$http.get(`${this.BASEURL}/online?title=${title}`).then((resp) => {
      return resp.data;
    });
  }
  postMovie(imdbid){
    return this.$http.post(`${this.BASEURL}`, {imdbId: imdbid}).then((resp) => {
      return resp.data;
    });
  }
  getFavMovies(){
    return this.$http.get(`${this.BASEURL}`).then((resp) => {
      return resp.data;
    });
  }
  deleteMovie(id){
    return this.$http.delete(`${this.BASEURL}/${id}`).then((resp) => {
      return resp.data;
    });
  }
  getDetailMovie(id){
    return this.$http.get(`${this.BASEURL}/${id}`).then((resp) =>{
      return resp.data;
    });
  }
  setSeen(movie){
    return this.$http.put(`${this.BASEURL}/${movie.id}`, {seen: movie.seen}).then((resp) => {
      return resp.data;
    });
  }
}

MoviesService.$inject = ['$http', 'BASEURL'];
