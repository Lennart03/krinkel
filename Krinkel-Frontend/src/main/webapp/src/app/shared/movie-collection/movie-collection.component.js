/*@ngInject*/
class MovieCollectionController{
  constructor($location){
    this.$location = $location;
  }
  addSelected(movie){
    this.added({movie: movie});
  }
  remSelected(movie){
    this.removed({movie: movie});
  }

  detail(movie){
    this.$location.path('/details/' + movie.id);
  }
}

export var MovieCollectionComponent = {
  template: require('./movie-collection.html'),
  controller: MovieCollectionController,
  bindings: {
    movies: '<',
    favorite: '<',
    added: '&',
    removed: '&'
  }
};
