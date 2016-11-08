const gulp = require('gulp');
const del = require('del');
const typescript = require('gulp-typescript');
const tscConfig = require('./tsconfig.json');
const es = require('event-stream');

var distDir = 'dist';

var angularDir = '/angular';
var angularAppDir = angularDir + '/app';
var angularJsLibDir = angularDir + '/js/lib';
var angularStylesDir = angularDir + '/css/lib';

// clean the contents of the distribution directory
gulp.task('clean', function () {
  return del([distDir]);
});

// copy static assets - i.e. non TypeScript compiled source
gulp.task('copy:assets', ['clean'], function() {
//  return gulp.src(['app/**/*', '!app/**/*.ts'], { base : './' })
//             .pipe(gulp.dest(distDir + angularAppDir))
});

// copy styles
gulp.task('copy:styles', ['clean'], function() {
  return gulp.src(['styles.css'], { base : './' })
             .pipe(gulp.dest(distDir + angularStylesDir))
})

// copy html
gulp.task('copy:html', ['clean'], function() {

  return es.merge(
    gulp.src(['index.html'], { base : './' })
        .pipe(gulp.dest(distDir + angularDir)),
    gulp.src('app/**/*.html')
        .pipe(gulp.dest(distDir + angularAppDir))
  )
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
        .pipe(gulp.dest(distDir + angularJsLibDir + '/@angular')),
    gulp.src(['./node_modules/angular2-in-memory-web-api/**/*.js*'])
        .pipe(gulp.dest(distDir + angularJsLibDir + '/angular2-in-memory-web-api')),
    gulp.src(['./node_modules/symbol-observable/**/*.js*'])
        .pipe(gulp.dest(distDir + angularJsLibDir + '/symbol-observable')),
    gulp.src(['./node_modules/rxjs/**/*.js*'])
        .pipe(gulp.dest(distDir + angularJsLibDir + '/rxjs')),
    gulp.src(['node_modules/core-js/client/shim.min.js',
              'node_modules/zone.js/dist/zone.js',
              'node_modules/reflect-metadata/Reflect.js',
              'node_modules/systemjs/dist/system.src.js',
              'node_modules/systemjs/dist/system.src.js'])
        .pipe(gulp.dest(distDir + angularJsLibDir)),

    // copy bootstrap dependencies
    gulp.src(['./node_modules/jquery/dist/jquery.js',
              './node_modules/tether/dist/js/tether.js',
              './node_modules/bootstrap/dist/js/bootstrap.js',
              './node_modules/ng2-bootstrap/bundles/ng2-bootstrap.umd.js'])
        .pipe(gulp.dest(distDir + angularJsLibDir)),
    gulp.src(['./node_modules/tether/dist/css/tether.css',
              './node_modules/bootstrap/dist/css/bootstrap.css'])
        .pipe(gulp.dest(distDir + angularStylesDir))
  );
});

// TypeScript compile
gulp.task('tscompile', ['clean'], function () {
  return gulp.src('app/**/*.ts')
             .pipe(typescript(tscConfig.compilerOptions))
             .pipe(gulp.dest(distDir + angularAppDir));
});

// typescript watch compile
gulp.task('tscompilew', function() {
    gulp.watch(['./app/**/*.ts'],
                ['tscompile']);
});

// build task
gulp.task('build', ['tscompile', 'copy:libs', 'copy:assets', 'copy:systemjsConfig', 'copy:html', 'copy:styles']);

// watch task
gulp.task('watch', ['build', 'tscompilew']);

gulp.task('default', ['build']);