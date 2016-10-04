/*@ngInject*/
class KrinkelLoginController {
  constructor(AuthService, KrinkelService, $location) {
    this.AuthService = AuthService;
    this.KrinkelService = KrinkelService;
    this.$location = $location;

  }

  $onInit() {
  }

  login(user, password) {
    console.debug(user + " " + password);
    var user = this.KrinkelService.logIn(user, password).then((results) => {
      // console.debug(results);
      return results;
    });
    this.AuthService.setLoggedinUser(user);
    this.$location.path('/home')
  }
}

export var KrinkelLoginComponent = {
  template: require('./krinkel-login.html'),
  controller: KrinkelLoginController
};


// KrinkelLoginController.$inject = ['AuthService', 'KrinkelService','$location'];
