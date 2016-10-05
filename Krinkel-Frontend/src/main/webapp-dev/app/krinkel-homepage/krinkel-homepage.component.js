/*@ngInject*/
class KrinkelHomepageController{
  constructor(AuthService, $location){
    this.AuthService = AuthService;
    this.$location = $location;
  }
  $onInit(){
<<<<<<< HEAD
    
=======
    if(this.AuthService.getLoggedinUser() == null){
      this.$location.path('/login');
    }
>>>>>>> feature/login
  }
}

export var KrinkelHomepageComponent = {
  template: require('./krinkel-homepage.html'),
  controller: KrinkelHomepageController
};

KrinkelHomepageComponent.$inject = ['AuthService','$location'];
