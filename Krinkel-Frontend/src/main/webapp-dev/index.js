import 'materialize-css/dist/css/materialize.css';
import 'nvd3/build/nv.d3.css'
import './style.css'
import 'jquery/dist/jquery.js';
import 'materialize-css/dist/js/materialize.min.js';
import 'angular';
import 'd3/d3.js';
import 'nvd3/build/nv.d3.js';
import 'angular-nvd3/dist/angular-nvd3.js';
import configModule from './app/env.url.config.js';


import appModule from './app/app.module';

angular.bootstrap(document, [appModule]);

$('.button-collapse').sideNav();

$(document).ready(function(){
    $('.tooltipped').tooltip({delay: 50});
});

