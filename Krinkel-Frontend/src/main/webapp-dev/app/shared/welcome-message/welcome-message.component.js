/*@ngInject*/
class WelcomeMessageController{
  constructor(){
  }

  $onInit(){
    this.title = "Welcome to the Krinkel app!"
  }

}

export var WelcomeMessageComponent = {
  template: require('./welcome-message.html'),
  controller: WelcomeMessageController
};
