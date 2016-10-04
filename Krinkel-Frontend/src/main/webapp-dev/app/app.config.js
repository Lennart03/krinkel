/*@ngInject*/
export function appConfig($routeProvider, $locationProvider) {

  $locationProvider.html5Mode(true);

  $routeProvider.when('/home', {
    template: '<krinkel-homepage></krinkel-homepage>'
  });

  $routeProvider.when('/register-participant', {
    template: '<register-participant></register-participant>'
  });


  $routeProvider.otherwise({
    redirectTo: '/home'
  });
}
