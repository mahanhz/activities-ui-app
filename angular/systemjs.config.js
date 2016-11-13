(function (global) {
  System.config({
    paths: {
      // paths serve as alias
      'jsLib:': 'js/lib/'
    },
    // map tells the System loader where to look for things
    map: {
      // our app is within the app folder
      app: 'app',
      // angular bundles
      '@angular/core': 'jsLib:@angular/core/bundles/core.umd.js',
      '@angular/common': 'jsLib:@angular/common/bundles/common.umd.js',
      '@angular/compiler': 'jsLib:@angular/compiler/bundles/compiler.umd.js',
      '@angular/platform-browser': 'jsLib:@angular/platform-browser/bundles/platform-browser.umd.js',
      '@angular/platform-browser-dynamic': 'jsLib:@angular/platform-browser-dynamic/bundles/platform-browser-dynamic.umd.js',
      '@angular/http': 'jsLib:@angular/http/bundles/http.umd.js',
      '@angular/router': 'jsLib:@angular/router/bundles/router.umd.js',
      '@angular/forms': 'jsLib:@angular/forms/bundles/forms.umd.js',
      // other libraries
      'rxjs':                       'jsLib:rxjs',
      'angular2-in-memory-web-api': 'jsLib:angular2-in-memory-web-api',
      'symbol-observable':          'jsLib:symbol-observable',
      'jquery': 'jsLib:jquery.min.js',
      'ng2-bootstrap/ng2-bootstrap': 'jsLib:ng2-bootstrap.umd.minjs',
      'ag-grid-ng2': 'jsLib:ag-grid-ng2',
      'ag-grid': 'jsLib:ag-grid'
    },
    // packages tells the System loader how to load when no filename and/or no extension
    packages: {
      app: {
        main: './main.js',
        defaultExtension: 'js'
      },
      'symbol-observable': {
        main: './index.js',
        defaultExtension: 'js'
      },
      rxjs: {
        defaultExtension: 'js'
      },
      'angular2-in-memory-web-api': {
        main: './index.js',
        defaultExtension: 'js'
      },
      lib: {
        format: 'register',
        defaultExtension: 'js'
      },
      'ag-grid-ng2': {
        defaultExtension: "js"
      },
      'ag-grid': {
        defaultExtension: "js"
      }
    }
  });
})(this);
