import 'angular';

import 'angular-route';
import 'ng-autocomplete';
import {appConfig} from './app.config';
import {KrinkelService} from './shared/krinkel.service';
import {StorageService} from './shared/storage.service.js';
import {MapperService} from './shared/mapping.service';
import {SelectService} from './shared/select.service';
import {AuthService} from './shared/auth.service';
import {RegisterOtherMemberService} from './shared/register-other-member.service';

//components
import {WelcomeMessageComponent} from './shared/welcome-message/welcome-message.component'
import {RegisterComponent} from './krinkel-register/register.component'
import {SuccessMessageComponent} from './shared/success-message/success-message.component'
import {SideNavComponent} from './shared/sidenav/sidenav.component'
import {TopNavComponent} from './shared/topnav/topnav.component'
import {UnitsComponent} from './shared/units/units.component'
import {KrinkelSelectComponent} from './krinkel-select-person/select.component';
import {FailMessageComponent} from './shared/fail-message/fail-message.component';
import {VoorwaardenComponent} from './shared/voorwaarden/voorwaarden.component';
import {LoadingSpinnerComponent} from './shared/loading-spinner/loading-spinner.component';

//pages
import {KrinkelHomepageComponent} from './krinkel-homepage/krinkel-homepage.component';
import {KrinkelThomasComponent} from './krinkel-admin/register-member/find-member/krinkel-find-member-by-ad.component';
import {KrinkelGraphComponent} from './krinkel-graph/krinkel-graph.component';
import {KrinkelAnalyticsComponent} from './krinkel-analytics/krinkel-analytics.component';
import {FindByAdComponent} from './krinkel-admin/register-member/find-member/krinkel-find-member-by-ad.component';
import {ChooseRegistrationComponent} from './krinkel-admin/register-member/choose-registration-method/krinkel-choose-registration-method.component';


//TODO : make BASEURL dynamic!!!
export default angular
    .module('contactsApp', ['ngRoute', 'nvd3', 'ngAutocomplete'])
    .config(appConfig)
    .constant('appVersion', 'BETA')
    .constant('BASEURL', 'http://localhost:8080')
    .run((appVersion)=> {
        console.log(`version: ${appVersion}`);
    })
    .service('KrinkelService', KrinkelService)
    .service('RegisterOtherMemberService', RegisterOtherMemberService)
    .service('AuthService', AuthService)
    .service('SelectService', SelectService)
    .service('StorageService', StorageService)
    .service('MapperService', MapperService)
    //global components
    .component('welcomeMessage', WelcomeMessageComponent)
    .component('sideNav', SideNavComponent)
    .component('successMessage', SuccessMessageComponent)
    .component('topNav', TopNavComponent)
    .component('krinkelSelect', KrinkelSelectComponent)
    .component('units', UnitsComponent)
    .component('failMessage', FailMessageComponent)
    .component('spinner', LoadingSpinnerComponent)
    //pages
    .component('krinkelHomepage', KrinkelHomepageComponent)
    .component('register', RegisterComponent)
    .component('krinkelAnalytics', KrinkelAnalyticsComponent)
    .component('krinkelGraph', KrinkelGraphComponent)
    .component('voorwaarden', VoorwaardenComponent)
    .component('krinkelFindMemberByAd', FindByAdComponent)
    .component('krinkelChooseRegistration', ChooseRegistrationComponent)
    .name;
