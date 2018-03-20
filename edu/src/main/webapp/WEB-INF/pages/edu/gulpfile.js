var dist = 'src/dist';
var timestamp = new Date().getTime();

// var cssLibFileName = 'vendor.min.' + timestamp + '.css'
var customCssFileName = 'eWorkUI.min.' + timestamp + '.css';
// var jsLibFileName = 'vendor.min.' + timestamp + '.js'
var customJsFileName = 'eWorkUI.min.' + timestamp + '.js';
var customJsFileNames = 'eWorkUI.min.' + timestamp + '.js';


var gulp = require('gulp'),
    uglify = require('gulp-uglify'),
    // imagemin = require('gulp-imagemin'),
    // pngquant = require('imagemin-pngquant'),
    // cache = require('gulp-cache'),
    minifyJS = require('gulp-minify'),
    // watch = require('gulp-watch'),
    minifyCss = require('gulp-minify-css'),
    concat = require('gulp-concat'),
    rev = require('gulp-rev'),
    revCollector = require('gulp-rev-collector'),
    less = require('gulp-less'),
    gulpRemoveHtml = require('gulp-remove-html'),
    removeEmptyLines = require('gulp-remove-empty-lines'),
    replace = require('gulp-replace'),
    gulpSequence = require('gulp-sequence'),
    minifyHTML = require('gulp-minify-html'),
    del = require('del');

gulp.task('clean', function() {
    // You can use multiple globbing patterns as you would with `gulp.src`
    return del([
        'src/dist/js/*.js',
        '!src/dist/js/eWorkUI.min.js',
        'src/dist/css/*.css',
        '!src/dist/css/eWorkUI.min.css',
        'src/dist/templates/**',
        'src/dist/printhtml/**',
        'src/dist/img/**',
    ]);
});

var paths = {
    scripts: ['src/js/**/*.js'],
    styles: 'src/less/**/*.{css, less}',
    images: ['src/img/**/*.*', 'src/less/img/**/*.*'],
    templates: 'src/templates/**/*.html',
    printhtml: 'src/printhtml/*.html',
    index: 'src/index.html',
    bower_fonts: 'src/components/**/*.{ttf,woff,eof,svg,woff2}',
    data: 'src/data/**/*.{json}'
};

/* Gulp CSS */
var cssPathPrefix = 'src/less/';
var cssPaths = {
    lib: [
        // 'src/components/bootstrap/dist/css/bootstrap.min.css',
        'src/components/angular-bootstrap-calendar/dist/css/angular-bootstrap-calendar.min.css',
        'src/components/angular/angular-csp.css',
        'src/components/iconfont/iconfont.css',
        'src/components/chosen/chosen.css',
        // 'src/components/font-awesome/css/font-awesome.min.css',
        'src/components/jquery-treetable/css/jquery.treetable.css',
        'src/components/jquery-treetable/css/jquery.treetable.theme.default.css',
        'src/components/jquery-treetable/css/screen.css',
        'src/components/mCustomScrollbar/jquery.mCustomScrollbar.min.css'
    ],
    custom: [
        cssPathPrefix + 'block/**/*.css',
        cssPathPrefix + 'common/**/*.css',
        cssPathPrefix + 'crm/**/*.css',
        cssPathPrefix + 'erp/**/*.css',
        cssPathPrefix + 'hrm/**/*.css',
        cssPathPrefix + 'tr/**/*.css',
        cssPathPrefix + 'variable.less',
        cssPathPrefix + '**/*.less'
    ]
};

gulp.task('lib-css', function() {
    return gulp.src(cssPaths.lib)
        .pipe(minifyCss())
        .pipe(concat('main.min.css'))
        .pipe(gulp.dest(dist + '/css'));
});

gulp.task('custom-css', function() {
    return gulp.src(cssPaths.custom)
        .pipe(less())
        .pipe(minifyCss())
        .pipe(concat(customCssFileName))
        .pipe(gulp.dest(dist + '/css'));
});

/* Gulp Images */
// gulp.task('custom-images', function () {
//     return gulp.src(paths.images)
//         .pipe(gulp.dest(dist + '/img'))
// })

/* Gulp JS */
/**
 * 以下JS的打包，有顺序的要求
 */
var jsPaths = {
    lib: [
        'src/components/angular/angular.min.js',
        'src/components/jquery/dist/jquery.min.js',
        'src/components/chosen/chosen.jquery.js',
        'src/components/mCustomScrollbar/jquery.mCustomScrollbar.concat.min.js',
        'src/components/jquery-treetable/jquery.treetable.js',
        'src/js/common/jquery.star-rating-svg.js',
        'src/components/angular-ui-router/release/angular-ui-router.min.js',
        'src/components/angular-bootstrap/ui-bootstrap-tpls-2.5.0.min.js',
        'src/components/angular-cookies/angular-cookies.min.js',
        'src/components/angular-messages/angular-messages.min.js',
        'src/components/angular-resource/angular-resource.min.js',
        'src/components/angular-sanitize/angular-sanitize.min.js',
        'src/components/lodash/dist/lodash.min.js',
        'src/components/moment/min/moment.min.js',
        'src/components/moment/locale/zh-cn.js',
        'src/components/bootstrap/dist/js/bootstrap.min.js',
        // 'src/components/bootstrap-switch/dist/js/bootstrap-switch.min.js',
        'src/components/jstree/dist/jstree.min.js',
        'src/components/Chart.js/Chart.min.js',
        // 'src/components/WdatePicker/WdatePicker.js',
        'src/components/angular-bootstrap-calendar/dist/js/angular-bootstrap-calendar-tpls.min.js'
    ],
    custom: [
        'src/js/*.js',
        'src/js/common/*.js',
        '!src/js/common/qrcode.js',
        'src/data/services.js',
        'src/js/services/*.js',
        'src/js/blocks/**/*.js',
        'src/js/directives/**/*.js',
        'src/js/controllers/**/*.js',
        'src/js/components/**/*.js'
    ]
};

gulp.task('lib-js', function() {
    return gulp.src(jsPaths.lib)
        .pipe(uglify())
        .pipe(concat('main.min.js'))
        .pipe(gulp.dest(dist + '/js'));
});

gulp.task('custom-js', function() {
    return gulp.src(jsPaths.custom)
        .pipe(concat(customJsFileName))
        .pipe(minifyJS({
            ext: {
                src: '-debug.js',
                min: '.js'
            },
            mangle: false
        }))
        .pipe(gulp.dest(dist + '/js'));
});

gulp.task('build-jsp', function() {
    gulp.src(['src/index-debug.jsp'])
        .pipe(replace(/<!-- build:libcss -->(.|\n|\r|\f|\t|\v)*?<!-- endbuild:libcss -->/g,
            '<link rel="stylesheet" type="text/css" href="dist/css/main.min.css">'))
        .pipe(replace(/<!-- build:css -->(.|\n|\r|\f|\t|\v)*?<!-- endbuild:css -->/g,
            '<link rel="stylesheet" type="text/css" href="dist/css/' + customCssFileName + '">'))
        .pipe(replace(/<!-- build:libjs -->(.|\n|\r|\f|\t|\v)*?<!-- endbuild:libjs -->/g,
            '<script type="text/javascript" src="dist/js/main.min.js"></script>'))
        .pipe(replace(/<!-- build:js -->(.|\n|\r|\f|\t|\v)*?<!-- endbuild:js -->/g,
            '<script type="text/javascript" src="dist/js/' + customJsFileNames + '"></script>'))
        .pipe(concat('index.jsp'))
        .pipe(gulp.dest('src/'));
});

//html压缩
gulp.task('html', function() {
    return gulp.src(paths.templates)
        .pipe(gulpRemoveHtml())
        .pipe(removeEmptyLines({ removeComments: true }))
        .pipe(minifyHTML())
        .pipe(rev())
        .pipe(gulp.dest(dist + '/templates'))
        .pipe(rev.manifest('rev-html-manifest.json'))
        .pipe(gulp.dest('rev'));
});

//printhtml压缩
gulp.task('printhtml', function() {
    return gulp.src(paths.printhtml)
        .pipe(gulpRemoveHtml())
        .pipe(removeEmptyLines({ removeComments: true }))
        .pipe(minifyHTML())
        .pipe(rev())
        .pipe(gulp.dest(dist + '/printhtml'))
        .pipe(rev.manifest('rev-printhtml-manifest.json'))
        .pipe(gulp.dest('rev'));
});

//imgmd5，压缩后并用md5进行命名，下面使用revCollector进行路径替换
gulp.task('images', function() {
    return gulp.src(paths.images)
        // .pipe(cache(imagemin({ progressive: true, interlaced: true, svgoPlugins: [{ removeViewBox: false }], use: [pngquant()] })))
        .pipe(rev()) //文件名加MD5后缀
        .pipe(gulp.dest(dist + '/img')) //输出到css目录
        .pipe(rev.manifest('rev-img-manifest.json')) //生成一个rev-manifest.json
        .pipe(gulp.dest('rev')); //将 rev-manifest.json 保存到 rev 目录内
});



gulp.task('revimg', function() {
    //css，主要是针对img替换
    return gulp.src(['rev/rev-img-manifest.json', dist + '/css/*.css'])
        .pipe(revCollector({ replaceReved: true, dirReplacements: { '../../img': '../../dist/img', '(../img': '(../../dist/img' } }))
        .pipe(gulp.dest(dist + '/css'));
});

gulp.task('revhtml', function() {
    return gulp.src(['rev/rev-img-manifest.json', dist + '/templates/**/*.html'])
        .pipe(revCollector({
            replaceReved: true,
            dirReplacements: {
                'img': 'dist/img'
            }
        }))
        .pipe(gulp.dest(dist + '/templates'));
});

gulp.task('revJs', function() {
    return gulp.src(['rev/*.json', dist + '/js/**/*.js', '!src/dist/js/eWorkUI.min.js'])
        .pipe(revCollector({
            replaceReved: true,
            dirReplacements: {
                'img/': 'dist/img/',
                'templates/': 'dist/templates/',
                '/printhtml': '/dist/printhtml'
            }
        }))
        .pipe(gulp.dest(dist + '/js'));
});


// gulp.task('build-html', function() {
//     //css，主要是针对img替换
//     return gulp.src(['rev/rev-html-manifest.json', '../../../../resources/plugins/right/menus.json'])
//         .pipe(revCollector({replaceReved:true,dirReplacements:{'"templates/':'"dist/templates'}}))
//         .pipe(replace('"/templates','"/dist/templates'))
//         .pipe(concat('menus-rev.json'))
//         .pipe(gulp.dest('../../../../resources/plugins/right'));
// });

gulp.task('build-html', function() {
    //css，主要是针对img替换
    return gulp.src(['rev/rev-html-manifest.json', '../../classes/plugins/right/menus.json'])
        .pipe(revCollector({ replaceReved: true, dirReplacements: { '"templates/': '"dist/templates' } }))
        .pipe(replace('"/templates', '"/dist/templates'))
        .pipe(concat('menus-rev.json'))
        .pipe(gulp.dest('../../classes/plugins/right'));
});

/**
 * Watch custom files
 */
// gulp.task('watch', function () {
//     livereload.listen()
//     gulp.watch([cssPaths.custom], ['custom-css'])
//     gulp.watch([jsPaths.custom], ['custom-js'])
//     gulp.watch([paths.images], ['custom-images'])
// })

/**
 * Gulp Tasks
 */
gulp.task('build', function(cb) {
    gulpSequence(
        'clean',
        'lib-css',
        'custom-css',
        'lib-js',
        'custom-js',
        'images',
        'html',
        'printhtml',
        'revimg',
        'revhtml',
        'revJs',
        'build-html',
        'build-jsp'
    )(cb);
});
gulp.task('default', ['build']);
// gulp.task('build-custom', ['custom-css'])
// gulp.task('build', ['build-custom'])
// gulp.task('default', ['build'])