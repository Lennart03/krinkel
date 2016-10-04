import 'angular';

import 'angular-route';

import {appConfig} from './app.config';
import {KrinkelService} from './shared/krinkel.service';

//components
import {WelcomeMessageComponent} from './shared/welcome-message/welcome-message.component'

//pages
import {KrinkelHomepageComponent} from './krinkel-homepage/krinkel-homepage.component'
import {RegisterParticipantComponent} from './krinkel-register-participant/register-participant.component'

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
  .component('registerParticipant', RegisterParticipantComponent)
  .name;
