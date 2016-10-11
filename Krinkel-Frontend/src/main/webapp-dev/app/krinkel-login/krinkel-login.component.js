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
    this.KrinkelService.logIn(user, password).then((results) => {
      console.debug(results);

      this.AuthService.setLoggedinUser(results);
      this.$location.path('/home')
      return results;
    }, (results) => {
      console.debug(results.data);

      if (results.status == "401") {
        Materialize.toast(results.data.message, 2000);
      }

    });

  }
}

export var KrinkelLoginComponent = {
  template: require('./krinkel-login.html'),
  controller: KrinkelLoginController
};


// KrinkelLoginController.$inject = ['AuthService', 'KrinkelService','$location'];
