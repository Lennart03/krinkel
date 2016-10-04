/*@ngInject*/
class SuccessMessageController{
  constructor(){
  }

  $onInit(){
    this.title = "Hoera!"
  }

}

export var SuccessMessageComponent = {
  template: require('./success-message.html'),
  controller: SuccessMessageController
};
