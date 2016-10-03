/*@ngInject*/
class MovieHomepageController{
  constructor(){

  }
  $onInit(){
    this.title = "Welkom welkom bij de movie app";
  }
}

export var MovieHomepageComponent = {
  template: require('./movie-homepage.html'),
  controller: MovieHomepageController
};
