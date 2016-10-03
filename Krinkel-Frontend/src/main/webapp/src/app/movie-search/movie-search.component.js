/*@ngInject*/
class MovieSearchController {
  constructor(MoviesService) {
    this.MoviesService = MoviesService;
  }

  search(filter){
    console.log(filter);
    this.MoviesService.getMovies(filter).then((data) => {
      console.log(data);
      this.movies = data;
    });
  }

  add(movie){
    //add movie to list
    this.MoviesService.postMovie(movie.imdbId).then((data) =>{
      Materialize.toast('Added new movie', 4000)
    });
  }
}

export var MovieSearchComponent = {
  template: require('./movie-search.html'),
  controller: MovieSearchController
};
