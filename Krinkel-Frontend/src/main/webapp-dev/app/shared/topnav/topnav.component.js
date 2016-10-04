/*@ngInject*/
class TopNavController{
  constructor(AuthService){
    this.AuthService = AuthService;
  }

  $onInit(){
  }

}

export var TopNavComponent = {
  template: require('./topnav.html'),
  controller: TopNavController
};

TopNavComponent.$inject = ['AuthService'];
