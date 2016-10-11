import 'materialize-css/dist/css/materialize.css';
import './style.css'
import 'jquery/dist/jquery.js';
import 'materialize-css/dist/js/materialize.min.js';
import 'angular';


import appModule from './app/app.module';

angular.bootstrap(document, [appModule]);

$('.button-collapse').sideNav();

