/*@ngInject*/
export function appConfig($routeProvider, $locationProvider) {

    $locationProvider.html5Mode(true);

    $routeProvider.when('/home', {
        template: '<krinkel-homepage></krinkel-homepage>'
    });

    $routeProvider.when('/success', {
        template: '<success-message></success-message>'
    });

    $routeProvider.when('/register-participant', {
        template: '<register type="participant"></register>'
    });

    $routeProvider.when('/register-volunteer', {
        template: '<register type="volunteer"></register>'
    });

    $routeProvider.when('/analytics', {
        template: '<krinkel-analytics></krinkel-analytics>'
    });

    $routeProvider.when('/select-participant', {
        template: '<krinkel-select></krinkel-select>'
    });

    $routeProvider.when('/graph', {
        template: '<krinkel-graph></krinkel-graph>'
    });

    $routeProvider.when('/find-participant-by-ad', {
        template: '<krinkel-find-member-by-ad></krinkel-find-member-by-ad>'
    });

<<<<<<< HEAD
    $routeProvider.when('/error', {
        template: '<error></error>'
    });

    /*$routeProvider.when('/choose-registration-participant', {
=======
    $routeProvider.when('/choose-registration-participant', {
>>>>>>> 879f472e1e8d25f96453dc970aeb7a41c2af9e98
        template: '<krinkel-choose-registration></krinkel-choose-registration>'
    });



    //Fail page had to go :(
    // $routeProvider.when('/fail', {
    //     template: '<fail-message></fail-message>'
    // });

    $routeProvider.otherwise({
        redirectTo: '/home'
    });
}
