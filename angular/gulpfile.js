const gulp = require('gulp');
const del = require('del');
const typescript = require('gulp-typescript');
const tscConfig = require('./tsconfig.json');
const es = require('event-stream');

var distDir = 'dist';

var angularDir = '/angular';
var angularAppDir = angularDir + '/app';
var angularLibDir = angularDir + '/lib';
var angularStylesDir = angularDir + '/css';

var resourcesDir = '../src/main/resources';
var staticResourcesDir = resourcesDir + '/static';

// clean the contents of the distribution directory
gulp.task('clean', function () {
  return del([distDir + '/**/*',
              staticResourcesDir + angularDir],
              {force: true});
});

// copy static assets - i.e. non TypeScript compiled source
gulp.task('copy:assets', ['clean'], function() {
  return gulp.src(['app/**/*', '!app/**/*.ts'], { base : './' })
             .pipe(gulp.dest(distDir + angularAppDir))
});

// copy styles
gulp.task('copy:styles', ['clean'], function() {
  return gulp.src(['styles.css'], { base : './' })
             .pipe(gulp.dest(distDir + angularStylesDir))
})

// copy html
gulp.task('copy:html', ['clean'], function() {
  return gulp.src(['*.html'], { base : './' })
             .pipe(gulp.dest(distDir + angularDir))
})

// copy html
gulp.task('copy:systemjsConfig', ['clean'], function() {
  return gulp.src(['systemjs.config.js'], { base : './' })
             .pipe(gulp.dest(distDir + angularDir))
})


// copy dependencies
gulp.task('copy:libs', ['clean'], function() {

  return es.merge(
    // copy libs
    gulp.src(['./node_modules/@angular/**/bundles/*.js'])
        .pipe(gulp.dest(distDir + angularLibDir + '/@angular')),
    gulp.src(['./node_modules/angular2-in-memory-web-api/**/*.js*'])
        .pipe(gulp.dest(distDir + angularLibDir + '/angular2-in-memory-web-api')),
    gulp.src(['./node_modules/symbol-observable/**/*.js*'])
        .pipe(gulp.dest(distDir + angularLibDir + '/symbol-observable')),
    gulp.src(['./node_modules/rxjs/**/*.js*'])
        .pipe(gulp.dest(distDir + angularLibDir + '/rxjs')),
    gulp.src(['node_modules/core-js/client/shim.min.js',
              'node_modules/zone.js/dist/zone.js',
              'node_modules/reflect-metadata/Reflect.js',
              'node_modules/systemjs/dist/system.src.js',
              'node_modules/systemjs/dist/system.src.js'])
        .pipe(gulp.dest(distDir + angularLibDir)));
});

// TypeScript compile
gulp.task('compile', ['clean'], function () {
  return gulp.src('app/**/*.ts')
             .pipe(typescript(tscConfig.compilerOptions))
             .pipe(gulp.dest(distDir + angularAppDir));
});

gulp.task('build', ['compile', 'copy:libs', 'copy:assets', 'copy:systemjsConfig', 'copy:html', 'copy:styles']);
gulp.task('copyDist', ['build'], function() {
  return gulp.src(['dist/**/*'], { base : './dist' })
             .pipe(gulp.dest(staticResourcesDir))
});
gulp.task('default', ['copyDist']);