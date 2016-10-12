import 'angular';

import 'angular-route';

import {appConfig} from './app.config';
import {KrinkelService} from './shared/krinkel.service';
import {StorageService} from './shared/storage.service.js';
import {MapperService} from './shared/mapping.service';
import {SelectService} from './shared/select.service';



import {AuthService} from './shared/auth.service';

//components
import {WelcomeMessageComponent} from './shared/welcome-message/welcome-message.component'
import {RegisterComponent} from './krinkel-register/register.component'
import {SuccessMessageComponent} from './shared/success-message/success-message.component'
import {SideNavComponent} from './shared/sidenav/sidenav.component'
import {TopNavComponent} from './shared/topnav/topnav.component';
import {KrinkelSelectComponent} from './krinkel-select-person/select.component';

//pages
import {KrinkelHomepageComponent} from './krinkel-homepage/krinkel-homepage.component';
import {KrinkelConfirmationComponent} from './krinkel-confirmation/krinkel-confirmation.component';
import {KrinkelGraphComponent} from './krinkel-graph/krinkel-graph.component';

export default angular
  .module('contactsApp', ['ngRoute', 'nvd3'])
  .config(appConfig)
  .constant('appVersion', 'BETA')
  .constant('BASEURL', 'http://localhost:8080')
  .run((appVersion)=> {
    console.log(`version: ${appVersion}`);
  })
  .service('KrinkelService', KrinkelService)
  .service('AuthService', AuthService)
    .service('SelectService', SelectService)
  .service('StorageService', StorageService)
  .service('MapperService', MapperService)
  //global components
  .component('welcomeMessage', WelcomeMessageComponent)
  .component('sideNav', SideNavComponent)
  .component('successMessage',SuccessMessageComponent)
  .component('topNav', TopNavComponent)
  .component('krinkelSelect', KrinkelSelectComponent)
  //pages
  .component('krinkelHomepage', KrinkelHomepageComponent)
  .component('register', RegisterComponent)
    .component('krinkelConfirmation', KrinkelConfirmationComponent)
    .component('krinkelGraph', KrinkelGraphComponent)
  .name;
