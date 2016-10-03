/*@ngInject*/
export function appConfig($routeProvider, $locationProvider) {

  $locationProvider.html5Mode(true);

  $routeProvider.when('/home', {
    template: '<movies-homepage></movies-homepage>'
  });

  $routeProvider.when('/search', {
    template: '<movies-search></movies-search>'
  });

  $routeProvider.when('/favorites', {
    template: '<movies-favorites></movies-favorites>'
  });

  $routeProvider.when('/details/:id', {
    template: '<movies-details></movies-details>'
  });

  $routeProvider.otherwise({
    redirectTo: '/home'
  });
}
