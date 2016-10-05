/*@ngInject*/
class WelcomeMessageController{
  constructor($location, AuthService){
    this.$location = $location;
    this.AuthService = AuthService;
  }

  $onInit(){
    if(this.AuthService.getRegistrationStatus()){
      this.$location.path('/success');
    }
  }


}

export var WelcomeMessageComponent = {
  template: require('./welcome-message.html'),
  controller: WelcomeMessageController
};

WelcomeMessageComponent.$inject = ['$location', 'AuthService'];
