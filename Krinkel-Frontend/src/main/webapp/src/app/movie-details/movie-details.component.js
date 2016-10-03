/*@ngInject*/
class MovieDetailsController {
  constructor(MoviesService, $routeParams) {
    this.MoviesService = MoviesService;
    this.id = $routeParams.id;
  }

  $onInit() {
    this.MoviesService.getDetailMovie(this.id).then((d) => {
      this.movie = d;
    });
  }
  change(){
    this.MoviesService.setSeen(this.movie).then((d) => {
      Materialize.toast('Changed movie', 4000)
    });
  }


}

export var MovieDetailsComponent = {
  template: require('./movie-details.html'),
  controller: MovieDetailsController
};
