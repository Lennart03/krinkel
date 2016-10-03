class MovieLookupController{

  constructor(){
  }

  search(filter){
    this.filtered({filter: filter});
  }

}

export var MovieLookupComponent = {
  template: require('./movie-lookup.html'),
  controller: MovieLookupController,
  bindings: {
    filtered: '&'
  }
};
