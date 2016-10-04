/*@ngInject*/
class TopNavController{
  constructor(AuthService, $location){
    this.AuthService = AuthService;
    this.$location = $location;
  }

  $onInit(){
  }

  logout(){
    this.AuthService.logoutUser();
    this.$location.path('/login')
  }
}

export var TopNavComponent = {
  template: require('./topnav.html'),
  controller: TopNavController
};

TopNavComponent.$inject = ['AuthService','$location'];
