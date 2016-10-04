/*@ngInject*/
class SideNavController{
  constructor(AuthService){
    this.AuthService = AuthService;
  }

  $onInit(){
  }

}

export var SideNavComponent = {
  template: require('./sidenav.html'),
  controller: SideNavController
};

SideNavComponent.$inject = ['AuthService'];
