localStorage.setItem('test', JSON.stringify(getParameterByName('ticket')));

function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    if (!url.("index")){
        return;
    }
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}


import 'materialize-css/dist/css/materialize.css';
import './style.css'
import 'jquery/dist/jquery.js';
import 'materialize-css/dist/js/materialize.min.js';
import 'angular';


import appModule from './app/app.module';

angular.bootstrap(document, [appModule]);

$('.button-collapse').sideNav();

