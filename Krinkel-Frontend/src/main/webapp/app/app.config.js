/*@ngInject*/
export function appConfig($routeProvider, $locationProvider) {

  $locationProvider.html5Mode(true);

  $routeProvider.when('/home', {
    template: '<krinkel-homepage></krinkel-homepage>'
  });

  $routeProvider.otherwise({
    redirectTo: '/home'
  });
}
