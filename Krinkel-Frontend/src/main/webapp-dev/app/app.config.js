/*@ngInject*/
export function appConfig($routeProvider, $locationProvider) {

  $locationProvider.html5Mode(true);

  $routeProvider.when('/home', {
    template: '<krinkel-homepage></krinkel-homepage>'
  })
    .when('/login', {
      template: '<krinkel-login></krinkel-login>'
    });

  $routeProvider.otherwise({
    redirectTo: '/home'
  });
}
