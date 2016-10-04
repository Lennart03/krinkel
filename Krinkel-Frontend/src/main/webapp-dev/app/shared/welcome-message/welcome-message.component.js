/*@ngInject*/
class WelcomeMessageController{
  constructor($location){
    this.$location = $location;
  }

  $onInit(){
    this.title = "Welcome to the Krinkel app!";
    this.$location.path('/success');
  }


}

export var WelcomeMessageComponent = {
  template: require('./welcome-message.html'),
  controller: WelcomeMessageController
};

WelcomeMessageComponent.$inject = ['$location'];
