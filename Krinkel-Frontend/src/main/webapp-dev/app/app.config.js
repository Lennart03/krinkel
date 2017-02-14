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

    $routeProvider.when('/add-to-basket', {
        template: '<basket type="participant"></basket>'
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

    $routeProvider.when('/cart', {
       template:'<krinkel-kar></krinkel-kar>'
    });

    $routeProvider.when('/graph', {
        template: '<krinkel-graph></krinkel-graph>'
    });

    $routeProvider.when('/find-participant-by-ad', {
        template: '<krinkel-find-member-by-ad></krinkel-find-member-by-ad>'
    });

    $routeProvider.when('/choose-registration-participant', {
           template: '<krinkel-choose-registration></krinkel-choose-registration>'
    });

    $routeProvider.when('/adminBeheer', {
        template: '<admin-toevoegen></admin-toevoegen>'
    });

    $routeProvider.when('/admin', {
        template: '<krinkel-admin></krinkel-admin>'
    });

    $routeProvider.when('/export', {
        template: '<krinkel-export></krinkel-export>'
    });

    $routeProvider.when('/generate-groups', {
        template: '<generate-groups></generate-groups>'
    });

    $routeProvider.when('/verbonden', {
        template: '<verbonden></verbonden>'
    });

    $routeProvider.when('/gewesten/:verbondNr/:verbondNaam', {
        template: '<gewesten></gewesten>'
    });

    $routeProvider.when('/groepen/:gewestNr/:gewestNaam', {
        template: '<groepen></groepen>'
    });

    $routeProvider.when('/groep/:groepNr/:groepNaam', {
        template: '<groep></groep>'
    });

    $routeProvider.otherwise({
        redirectTo: '/home'
    });
}
