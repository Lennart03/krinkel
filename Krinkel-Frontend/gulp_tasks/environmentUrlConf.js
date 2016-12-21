const ngConfig = require('gulp-ng-config');
const gulp = require('gulp');
const gulpConf = require('../conf/gulp.conf.js');
const configurl = require('../conf/env.url.config.json');
const env = gulpConf.environment||'local';

/*log the url to be defined*/
console.log(configurl[env]);

/**
 * this task creates a angular module 'ngConstants' (filename: env.url.config.js) 
 * the which defines a constant
 * 'BASEURL':<hostname url environment>
 */
gulp.task('angularConstant',()=>{
    return gulp.src('./conf/env.url.config.json')
        .pipe(ngConfig('ngConstants',{
            environment:env,
            wrap:'es6'
        }))
        .pipe(gulp.dest('./src/main/webapp-dev/app'));
});

