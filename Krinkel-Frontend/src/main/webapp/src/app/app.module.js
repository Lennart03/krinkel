import 'angular';

import 'angular-route';

import {appConfig} from './app.config';
import {MoviesService} from './shared/movies.service';

import {MovieCollectionComponent} from './shared/movie-collection/movie-collection.component'
import {MovieHomepageComponent} from './movie-homepage/movie-homepage.component'
import {MovieSearchComponent} from './movie-search/movie-search.component'
import {MovieFavoritesComponent} from './movie-favorites/movie-favorites.component'
import {MovieDetailsComponent} from './movie-details/movie-details.component'
import {MovieLookupComponent} from './shared/movie-lookup/movie-lookup.component'

export default angular
  .module('contactsApp', ['ngRoute'])
  .config(appConfig)
  .constant('appVersion', 'BETA')
  .constant('BASEURL', 'http://angularcoursebackend.azurewebsites.net/api/Movies')
  .run((appVersion)=> {
    console.log(`version: ${appVersion}`);
  })
  .service('MoviesService', MoviesService)
  .component('moviesLookup', MovieLookupComponent)
  .component('moviesCollection', MovieCollectionComponent)
  .component('moviesHomepage', MovieHomepageComponent)
  .component('moviesSearch', MovieSearchComponent)
  .component('moviesFavorites', MovieFavoritesComponent)
  .component('moviesDetails', MovieDetailsComponent)
  .name;
