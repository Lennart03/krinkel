const ngConstant = require('gulp-ng-constant');
const gulp = require('gulp');
const gulpConf = require('../conf/gulp.conf.js');

const configurl = require('../conf/env.url.config.json');
const env = configurl[gulpConf.environment];
console.log(env);

gulp.task('angularConstant',()=>{
    return ngConstant({
        name: 'ngConstants',
        constants: env,
        stream: true,
        wrap:es6
    })
    .pipe(gulp.dest('../../src/main/webapp/app'));
});