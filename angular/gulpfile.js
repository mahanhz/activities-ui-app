const gulp = require('gulp');
const del = require('del');
const typescript = require('gulp-typescript');
const tscConfig = require('./tsconfig.json');

var distDir = 'dist';

var angularDir = '/angular';
var angularAppDir = angularDir + '/app';
var angularLibDir = angularDir + '/lib';
var angularStylesDir = angularDir + '/css';

var resourcesDir = '../src/main/resources';
var staticResourcesDir = resourcesDir + '/static';
var templatesDir = resourcesDir + '/templates/' + angularDir;

// clean the contents of the distribution directory
gulp.task('clean', function () {
  return del([distDir + '/**/*',
              staticResourcesDir + angularDir + '/**/*',
              templatesDir + '/**/*'],
              {force: true});
});

// copy static assets - i.e. non TypeScript compiled source
gulp.task('copy:assets', ['clean'], function() {
  return gulp.src(['app/**/*', '!app/**/*.ts'], { base : './' })
    .pipe(gulp.dest(distDir + angularAppDir))
    .pipe(gulp.dest(staticResourcesDir + angularAppDir))
});

// copy styles
gulp.task('copy:styles', ['clean'], function() {
  return gulp.src(['styles.cs'], { base : './' })
         .pipe(gulp.dest(staticResourcesDir + angularStylesDir))
})

// copy html
gulp.task('copy:html', ['clean'], function() {
  return gulp.src(['index.html'], { base : './' })
    .pipe(gulp.dest(templatesDir))
})

// copy dependencies
gulp.task('copy:libs', ['clean'], function() {
  return gulp.src([
      'node_modules/core-js/client/shim.min.js',
      'node_modules/zone.js/dist/zone.js',
      'node_modules/reflect-metadata/Reflect.js',
      'node_modules/rxjs/bundles/Rx.js',
      'node_modules/@angular/core/bundles/core.umd.js',
      'node_modules/@angular/common/bundles/common.umd.js',
      'node_modules/@angular/compiler/bundles/compiler.umd.js',
      'node_modules/@angular/platform-browser/bundles/platform-browser.umd.js',
      'node_modules/@angular/platform-browser-dynamic/bundles/platform-browser-dynamic.umd.js',
      'node_modules/systemjs/dist/system.src.js',
    ])
    .pipe(gulp.dest(distDir + angularLibDir))
    .pipe(gulp.dest(staticResourcesDir + angularLibDir))
});

// TypeScript compile
gulp.task('compile', ['clean'], function () {
  return gulp
    .src('app/**/*.ts')
    .pipe(typescript(tscConfig.compilerOptions))
    .pipe(gulp.dest(distDir + angularAppDir))
    .pipe(gulp.dest(staticResourcesDir + angularAppDir));
});

gulp.task('build', ['compile', 'copy:libs', 'copy:assets', 'copy:html', 'copy:styles']);
gulp.task('default', ['build']);