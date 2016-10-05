import 'angular';

import 'angular-route';

import {appConfig} from './app.config';
import {KrinkelService} from './shared/krinkel.service';

//components
import {WelcomeMessageComponent} from './shared/welcome-message/welcome-message.component'
import {RegisterComponent} from './krinkel-register/register.component'

//pages
import {KrinkelHomepageComponent} from './krinkel-homepage/krinkel-homepage.component'

export default angular
  .module('contactsApp', ['ngRoute'])
  .config(appConfig)
  .constant('appVersion', 'BETA')
  .constant('BASEURL', 'http://localhost:8080')
  .run((appVersion)=> {
    console.log(`version: ${appVersion}`);
  })
  .service('KrinkelService', KrinkelService)
  //global components
  .component('welcomeMessage', WelcomeMessageComponent)
  //pages
  .component('krinkelHomepage', KrinkelHomepageComponent)
  .component('register', RegisterComponent)
  .name;
