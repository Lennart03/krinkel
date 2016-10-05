/*@ngInject*/
class KrinkelLoginController{
  constructor(AuthService){
    this.AuthService = AuthService;

  }
  $onInit(){
  }

  login(user,password)
  {
    console.debug(user +" " + password);
    this.AuthService.setLoggedinUser(user,password).then((results) => {


      console.debug(results);
    });


  }
}

export var KrinkelLoginComponent = {
  template: require('./krinkel-login.html'),
  controller: KrinkelLoginController
};

