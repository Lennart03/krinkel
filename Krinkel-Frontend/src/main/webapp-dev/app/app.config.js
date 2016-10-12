/*@ngInject*/
export function appConfig($routeProvider, $locationProvider) {

    $locationProvider.html5Mode(true);

    $routeProvider.when('/home', {
        template: '<krinkel-homepage></krinkel-homepage>'
    })
        .when('/login', {
            template: '<krinkel-login></krinkel-login>'
        });

    $routeProvider.when('/success', {
        template: '<success-message></success-message>'
    });

    $routeProvider.when('/register-participant', {
        template: '<register type="participant"></register>'
    });

    $routeProvider.when('/register-volunteer', {
        template: '<register type="volunteer"></register>',
    });

    $routeProvider.when('/confirmation', {
        template: '<krinkel-confirmation></krinkel-confirmation>'
    });

    $routeProvider.when('/select-participant', {
        template: '<krinkel-select></krinkel-select>'
    });


    $routeProvider.otherwise({
        redirectTo: '/home'
    });
}
