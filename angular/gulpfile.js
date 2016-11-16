'use strict';

const gulp = require('gulp');
const del = require('del');
const sass = require('gulp-sass');
const typescript = require('gulp-typescript');
const tscConfig = require('./tsconfig.json');
const es = require('event-stream');

var distDir = 'dist';

var angularDir = '/angular';
var angularAppDir = angularDir + '/app';
var angularJsLibDir = angularDir + '/js/lib';
var angularStylesDir = angularDir + '/css';
var angularStylesLibDir = angularStylesDir + '/lib';

var angularUnitTestResultsDir = 'unit_tests/results';

// clean the contents of the distribution directory
gulp.task('clean', function () {
  return del([distDir]);
});

// copy html
gulp.task('copy:html', ['clean'], function() {

  return es.merge(
    gulp.src(['index.html'], { base : './' })
        .pipe(gulp.dest(distDir + angularDir)),
    gulp.src('app/**/*.html')
        .pipe(gulp.dest(distDir + angularAppDir))
  )
})

// sass compile
gulp.task('copy:sass', ['clean'], function() {

    // compile sass and copy
    return gulp.src('./assets/sass/**/*.scss')
        .pipe(sass.sync().on('error', sass.logError))
        .pipe(gulp.dest(distDir + angularStylesDir));
});

// copy systemjs config
gulp.task('copy:systemjsConfig', ['clean'], function() {
  return gulp.src(['systemjs.config.js'], { base : './' })
             .pipe(gulp.dest(distDir + angularDir))
})

// copy dependencies
gulp.task('copy:libs', ['clean'], function() {

  return es.merge(
    // copy libs
    gulp.src(['./node_modules/@angular/**/bundles/*.js'])
        .pipe(gulp.dest(distDir + angularJsLibDir + '/@angular')),
    gulp.src(['./node_modules/angular2-in-memory-web-api/**/*.js*'])
        .pipe(gulp.dest(distDir + angularJsLibDir + '/angular2-in-memory-web-api')),
    gulp.src(['./node_modules/symbol-observable/**/*.js*'])
        .pipe(gulp.dest(distDir + angularJsLibDir + '/symbol-observable')),
    gulp.src(['./node_modules/rxjs/**/*.js*'])
        .pipe(gulp.dest(distDir + angularJsLibDir + '/rxjs')),
    gulp.src(['node_modules/core-js/client/shim.min.js',
              'node_modules/zone.js/dist/zone.min.js',
              'node_modules/reflect-metadata/Reflect.js',
              'node_modules/systemjs/dist/system.src.js'])
        .pipe(gulp.dest(distDir + angularJsLibDir)),

    // copy bootstrap dependencies
    gulp.src(['./node_modules/jquery/dist/jquery.min.js',
              './node_modules/tether/dist/js/tether.min.js',
              './node_modules/bootstrap/dist/js/bootstrap.min.js',
              './node_modules/ng2-bootstrap/bundles/ng2-bootstrap.umd.min.js'])
        .pipe(gulp.dest(distDir + angularJsLibDir)),
    gulp.src(['./node_modules/tether/dist/css/tether.min.css',
              './node_modules/bootstrap/dist/css/bootstrap.min.css',
              './node_modules/jquery/dist/css/jquery.min.css'])
        .pipe(gulp.dest(distDir + angularStylesLibDir)),

    // copy font-awesome
    gulp.src(['./node_modules/font-awesome/css/font-awesome.min.css'])
        .pipe(gulp.dest(distDir + angularStylesLibDir + '/font-awesome/css')),
    gulp.src(['./node_modules/font-awesome/fonts/*'])
        .pipe(gulp.dest(distDir + angularStylesLibDir + '/font-awesome/fonts')),

    // copy ag-grid
    gulp.src(['./node_modules/ag-grid-ng2/**/*.js*'])
        .pipe(gulp.dest(distDir + angularJsLibDir + '/ag-grid-ng2')),
    gulp.src(['./node_modules/ag-grid/**/*.js*'])
        .pipe(gulp.dest(distDir + angularJsLibDir + '/ag-grid')),
    gulp.src(['./node_modules/ag-grid/**/*.css*'])
        .pipe(gulp.dest(distDir + angularStylesLibDir + '/ag-grid'))
  );
});

// TypeScript compile app
gulp.task('tsc:app', ['clean'], function () {
  return gulp.src('app/**/*.ts')
             .pipe(typescript(tscConfig.compilerOptions))
             .pipe(gulp.dest(distDir + angularAppDir));
});

gulp.task('cleanUnitTestResults', function () {
  return del([angularUnitTestResultsDir]);
});

// TypeScript compile app
gulp.task('unitTest', ['cleanUnitTestResults'], function () {

  return es.merge(
    // copy html
    gulp.src('unit_tests/**/*.html')
        .pipe(gulp.dest(angularUnitTestResultsDir)),

    // copy jasmine-core dependencies
    gulp.src(['./node_modules/jasmine-core/lib/jasmine-core/jasmine.js',
              './node_modules/jasmine-core/lib/jasmine-core/jasmine-html.js',
              './node_modules/jasmine-core/lib/jasmine-core/boot.js'])
        .pipe(gulp.dest(angularUnitTestResultsDir + '/js/lib')),
    gulp.src(['./node_modules/jasmine-core/lib/jasmine-core/jasmine.css'])
        .pipe(gulp.dest(angularUnitTestResultsDir + '/css/lib')),

    gulp.src('unit_tests/**/*.ts')
        .pipe(typescript(tscConfig.compilerOptions))
        .pipe(gulp.dest(angularUnitTestResultsDir))
  );
});

// typescript watch compile
gulp.task('tscw:app', function() {
    gulp.watch(['./app/**/*.ts'],
                ['tsc:app']);
});

// build task
gulp.task('build', ['tsc:app', 'copy:libs', 'copy:sass', 'copy:systemjsConfig', 'copy:html', 'copy:sass', 'unitTest']);

// watch task
gulp.task('watch', ['build', 'tscw:app']);

gulp.task('default', ['build']);