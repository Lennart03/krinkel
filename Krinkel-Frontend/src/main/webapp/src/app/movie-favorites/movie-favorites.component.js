/*@ngInject*/
class MovieFavoritesController {
  constructor(MoviesService) {
    this.MoviesService = MoviesService;
  }

  $onInit() {
   this.getBrol();
  }

  remove(movie) {
    this.MoviesService.deleteMovie(movie.id).then((d) => {
      Materialize.toast('Removed movie', 4000)
      this.getBrol();
    });
  }
  getBrol(){
    this.MoviesService.getFavMovies().then((d) => {
      this.movies = d;
    });
  }
}

export var MovieFavoritesComponent = {
  template: require('./movie-favorites.html'),
  controller: MovieFavoritesController
};
