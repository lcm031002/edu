var dist = "deploy/";

var gulp = require('gulp'),
    usemin = require('gulp-usemin'),
    wrap = require('gulp-wrap'),
    connect = require('gulp-connect'),
    watch = require('gulp-watch'),
    minifyCss = require('gulp-minify-css'),
    minifyJs = require('gulp-uglify'),
    concat = require('gulp-concat'),
    less = require('gulp-less'),
    rename = require('gulp-rename'),
    livereload = require('gulp-livereload'),
    minifyHTML = require('gulp-minify-html');

var paths = {
    scripts: 'src/js/**/*.*',
    styles: 'src/less/**/*.{css, less}',
    images: 'src/img/**/*.*',
    templates: 'src/templates/**/*.html',
    index: 'src/index.html',
    bower_fonts: 'src/components/**/*.{ttf,woff,eof,svg,woff2}',
    data:'src/data/**/*.*'
};

/**
 * Handle bower components from index.css
 */
gulp.task('usemin', function() {
    return gulp.src(paths.index)
        .pipe(usemin({
            js: [minifyJs(), 'concat'],
            css: [minifyCss({keepSpecialComments: 0}), 'concat']
        }))
        .pipe(gulp.dest(dist));
});



/**
 * Copy assets
 */
gulp.task('build-assets', ['copy-bower_fonts']);

gulp.task('copy-bower_fonts', function() {
    return gulp.src(paths.bower_fonts)
        .pipe(rename({
            dirname: '/fonts'
        }))
        .pipe(gulp.dest(dist+'lib'));
});

/**
 * Handle custom files
 */
gulp.task('build-custom', ['custom-images', 'custom-js', 'custom-less', 'custom-templates','custom-data']);

gulp.task('custom-images', function() {
    return gulp.src(paths.images)
        .pipe(gulp.dest(dist+'/img'))
        .pipe(livereload());
});

gulp.task('custom-js', function() {
    return gulp.src(paths.scripts)
        .pipe(minifyJs())
        .pipe(concat('eWorkUI.min.js'))
        .pipe(gulp.dest(dist+'/js'))
        .pipe(livereload());
});

gulp.task('custom-less', function() {
    return gulp.src(paths.styles)
        .pipe(less())
        .pipe(concat('eWorkUI.min.css'))
        .pipe(gulp.dest(dist+'/css'))
        .pipe(livereload());
});

gulp.task('custom-templates', function() {
    return gulp.src(paths.templates)
        .pipe(minifyHTML())
        .pipe(gulp.dest(dist+'/templates'))
        .pipe(livereload());
});

gulp.task('custom-data', function() {
    return gulp.src(paths.data)
        .pipe(gulp.dest(dist+'/data'))
        .pipe(livereload());
});


/**
 * Watch custom files
 */
gulp.task('watch', function() {
    livereload.listen();
    gulp.watch([paths.images], ['custom-images']);
    gulp.watch([paths.styles], ['custom-less']);
    gulp.watch([paths.scripts], ['custom-js']);
    gulp.watch([paths.templates], ['custom-templates']);
    gulp.watch([paths.index], ['usemin']);
});

/**
 * Live reload server
 */
gulp.task('webserver', function() {
    connect.server({
        root: 'dist',
        livereload: true,
        port: 8888
    });
});

gulp.task('livereload', function() {
    gulp.src(['deploy/**/*.*'])
        .pipe(watch())
        .pipe(connect.reload());
});



/**
 * Gulp tasks
 */
gulp.task('build', ['usemin', 'build-assets', 'build-custom']);
gulp.task('default', ['build', 'webserver', 'watch']);