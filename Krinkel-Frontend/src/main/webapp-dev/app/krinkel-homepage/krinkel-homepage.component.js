/*@ngInject*/
class KrinkelHomepageController{
  constructor(AuthService, $location){
    this.AuthService = AuthService;
    this.$location = $location;
  }
  $onInit(){
    if(this.AuthService.getLoggedinUser() == null){
      this.$location.path('/login');
    }
  }
}

export var KrinkelHomepageComponent = {
  template: require('./krinkel-homepage.html'),
  controller: KrinkelHomepageController
};

KrinkelHomepageComponent.$inject = ['AuthService','$location'];
