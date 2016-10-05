/*@ngInject*/
class WelcomeMessageController{
  constructor($location, AuthService){
    this.$location = $location;
    this.AuthService = AuthService;
  }

  $onInit(){
    this.title = "Welcome to the Krinkel app!";
    if(this.AuthService.getRegistrationStatus()){
      this.$location.path('/success');
    }
    $('.datepicker').pickadate({
      selectMonths: true, // Creates a dropdown to control month
      selectYears: 15 // Creates a dropdown of 15 years to control year
    });
  }


}

export var WelcomeMessageComponent = {
  template: require('./welcome-message.html'),
  controller: WelcomeMessageController
};

WelcomeMessageComponent.$inject = ['$location', 'AuthService'];
