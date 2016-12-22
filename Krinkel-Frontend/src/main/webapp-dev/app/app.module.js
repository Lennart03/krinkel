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
import {BasketComponent} from './krinkel-register/register.component'
import {SuccessMessageComponent} from './shared/success-message/success-message.component'
import {SideNavComponent} from './shared/sidenav/sidenav.component'
import {TopNavComponent} from './shared/topnav/topnav.component'
import {UnitsComponent} from './shared/units/units.component'
import {KrinkelSelectComponent} from './krinkel-select-person/select.component';
import {FailMessageComponent} from './shared/fail-message/fail-message.component';
import {VoorwaardenComponent} from './shared/voorwaarden/voorwaarden.component';
import {LoadingSpinnerComponent} from './shared/loading-spinner/loading-spinner.component';
import {KrinkelKarComponent} from './krinkel-kar/krinkelkar.component';
import {VerbondenComponent} from './shared/units/verbonden.component';
import {GewestenComponent} from './shared/units/gewesten.component';
import {GroepenComponent} from './shared/units/groepen.component';
import {GroepComponent} from './shared/units/groep.component';


//pages
import {KrinkelHomepageComponent} from './krinkel-homepage/krinkel-homepage.component';
import {KrinkelGraphComponent} from './krinkel-graph/krinkel-graph.component';
import {KrinkelAnalyticsComponent} from './krinkel-analytics/krinkel-analytics.component';
import {FindByAdComponent} from './krinkel-admin/register-member/find-member/krinkel-find-member-by-ad.component';
import {ChooseRegistrationComponent} from './krinkel-admin/register-member/choose-registration-method/krinkel-choose-registration-method.component';
import {KrinkelAdminComponent} from './krinkel-admin/krinkel-admin.component';
import {KrinkelExportComponent} from './krinkel-admin/krinkel-export/krinkel-export.component';
import {AdminToevoegenComponent} from './krinkel-admin/admin-toevoegen/admin-toevoegen.component';



export default angular
    .module('contactsApp', ['ngRoute', 'nvd3', 'ngAutocomplete','ngConstants'])
    .config(appConfig)
    .constant('appVersion', 'BETA')
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
    .component('basket', BasketComponent)
    .component('krinkelAnalytics', KrinkelAnalyticsComponent)
    .component('krinkelGraph', KrinkelGraphComponent)
    .component('voorwaarden', VoorwaardenComponent)
    .component('krinkelExport', KrinkelExportComponent)
    .component('krinkelFindMemberByAd', FindByAdComponent)
    .component('krinkelChooseRegistration', ChooseRegistrationComponent)
    .component('krinkelAdmin', KrinkelAdminComponent)
    .component('adminToevoegen', AdminToevoegenComponent)
    .component('krinkelKar', KrinkelKarComponent)
    .component('verbonden', VerbondenComponent)
    .component('gewesten', GewestenComponent)
    .component('groepen', GroepenComponent)
    .component('groep', GroepComponent)
    .filter('escape', function() { // Filter for escaping special characters in URLs
        return window.encodeURIComponent;
    })
    .name;
