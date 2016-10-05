import 'angular';

import 'angular-route';
import 'ng-autocomplete'
import {appConfig} from './app.config';
import {KrinkelService} from './shared/krinkel.service';
import {AuthService} from './shared/auth.service';

//components

import {WelcomeMessageComponent} from './shared/welcome-message/welcome-message.component'
import {RegisterComponent} from './krinkel-register/register.component'
import {SuccessMessageComponent} from './shared/success-message/success-message.component'
import {SideNavComponent} from './shared/sidenav/sidenav.component'
import {TopNavComponent} from './shared/topnav/topnav.component';

//pages
import {KrinkelHomepageComponent} from './krinkel-homepage/krinkel-homepage.component';
import {KrinkelLoginComponent} from './krinkel-login/krinkel-login.component';

export default angular
  .module('contactsApp', ['ngRoute','ngAutocomplete'])
  .config(appConfig)
  .constant('appVersion', 'BETA')
  .constant('BASEURL', 'http://localhost:8080')
  .run((appVersion)=> {
    console.log(`version: ${appVersion}`);
  })
  .service('KrinkelService', KrinkelService)
  .service('AuthService', AuthService)

  //global components
  .component('welcomeMessage', WelcomeMessageComponent)
  .component('sideNav', SideNavComponent)
  .component('successMessage',SuccessMessageComponent)
  .component('topNav', TopNavComponent)
  //pages
  .component('krinkelHomepage', KrinkelHomepageComponent)
  .component('register', RegisterComponent)
  .component('krinkelLogin', KrinkelLoginComponent)
  .name;
