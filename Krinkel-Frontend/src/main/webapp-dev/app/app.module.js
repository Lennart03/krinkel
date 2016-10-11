import 'angular';

import 'angular-route';

import {appConfig} from './app.config';
import {KrinkelService} from './shared/krinkel.service';
import {StorageService} from './shared/storage.service.js';
import {MapperService} from './shared/mapping.service';



import {AuthService} from './shared/auth.service';

//components
import {WelcomeMessageComponent} from './shared/welcome-message/welcome-message.component'
import {RegisterComponent} from './krinkel-register/register.component'
import {SuccessMessageComponent} from './shared/success-message/success-message.component'
import {SideNavComponent} from './shared/sidenav/sidenav.component'
import {TopNavComponent} from './shared/topnav/topnav.component'
import {VerbondUnitComponent} from './shared/units/verbond-unit/verbond-unit.component'
import {GewestUnitComponent} from './shared/units/gewest-unit/gewest-unit.component'
import {GroepUnitComponent} from './shared/units/groep-unit/groep-unit.component'
import {UnitMemberComponent} from './shared/units/unit-member/unit-member.component'

//pages
import {KrinkelHomepageComponent} from './krinkel-homepage/krinkel-homepage.component';
import {KrinkelLoginComponent} from './krinkel-login/krinkel-login.component';
import {KrinkelConfirmationComponent} from './krinkel-confirmation/krinkel-confirmation.component';
import {KrinkelAnalyticsComponent} from './krinkel-analytics/krinkel-analytics.component';

export default angular
  .module('contactsApp', ['ngRoute'])
  .config(appConfig)
  .constant('appVersion', 'BETA')
  .constant('BASEURL', 'http://localhost:8080')
  .run((appVersion)=> {
    console.log(`version: ${appVersion}`);
  })
  .service('KrinkelService', KrinkelService)
  .service('AuthService', AuthService)

  .service('StorageService', StorageService)
  .service('MapperService', MapperService)
  //global components
  .component('welcomeMessage', WelcomeMessageComponent)
  .component('sideNav', SideNavComponent)
  .component('successMessage',SuccessMessageComponent)
  .component('topNav', TopNavComponent)
    .component('verbondUnit', VerbondUnitComponent)
    .component('gewestUnit', GewestUnitComponent)
    .component('groepUnit', GroepUnitComponent)
    .component('unitMember', UnitMemberComponent)
  //pages
  .component('krinkelHomepage', KrinkelHomepageComponent)
  .component('register', RegisterComponent)
  .component('krinkelLogin', KrinkelLoginComponent)
   .component('krinkelConfirmation', KrinkelConfirmationComponent)
    .component('krinkelAnalytics', KrinkelAnalyticsComponent)
  .name;
