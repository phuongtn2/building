var webpack = require('webpack'),
    path    = require('path'),
    glob    = require('glob');

// options is optional
var imports = [];
[
    'WebContent/js/src/class/data/**/*.js',
    'WebContent/js/src/class/*.js',
    'WebContent/js/src/class/window/*.js'
].forEach(function (pattern) {
    var files = glob.sync(pattern);
    // do import files
    files.forEach(function (file, index) {
        if (file.match('GHClassIF')) { return; }
        var xxx = file.match(/[^\/]+(?=\.js$)/) + '_' + index;
        imports.push(xxx + '=' + file.replace('WebContent/js/src/', ''));return false;
    });
});
imports = imports.join(',');

module.exports = {
    context: path.resolve('WebContent/js/src'),
    entry: {
        schedule: 'schedule.js'
    },
    output: {
        path: path.resolve('WebContent/codebase'),
        filename: '[name].js'
    },
    module: {
        loaders: [
            {
                test: require.resolve('jquery'),
                loader: 'expose?$!expose?jQuery!imports?define=>false'
            },
            /*{
                test: /ServicesAssetsWindow\.js$/,
                loader: 'exports?ServicesAssetsWindow'
            },*/
            {
                test: /dhtmlx\.js$/,
                loader: 'script'
            },
            {
                test: /dhtmlxvault\.js/,
                loader: 'imports?dhtmlx!script'
            },
            {
                test: /dhtmlxscheduler\.js$/,
                loader: 'imports?dhtmlx!script'
            },
            {
                test: /jquery\.zclip\.min\.js$/,
                loader: 'imports?jquery!exports?ZeroClipboard'
            },
            {
                test: /xdate\.js$/,
                loader: 'expose?XDate!imports?jquery!exports?XDate'
            },
            {
                test: /gh\.const\.js$/,
                loader: 'imports?lodash!script'
            },
            {
                test: /gh\.message\.js$/,
                loader: 'imports?gh_const!script'
            },
            {
                test: /gh\.config\.js$/,
                loader: 'imports?gh_global,dhtmlx,dhtmlxvault!script'
            },
            {
                test: /gh\.global\.js$/,
                loader: 'imports?gh_message!script'
            },
            {
                test: /gh\.util\.js$/,
                loader: 'imports?gh_config,xdate!script'
            },
            {
                test: /gh\.data\.js$/,
                loader: 'imports?gh_util' + (imports ? ',' + imports : '') + '!script'
            },
            {
                test: /gh\.wins\.js$/,
                loader: 'script'
            },
            {
                test: /class\/?(?:window\/.+\.js$|data\/.+\.js$|.+\.js$)'/,
                loader: 'script'
            }
        ]
    },
    resolve: {
        root: path.resolve('WebContent/js/src'),
        modulesDirectories: [
            'node_modules',
            '.'
        ],
        alias: {
            dhtmlx:      path.resolve('WebContent/codebase/dhtmlx.js'),
            dhtmlxvault: path.resolve('WebContent/codebase/dhtmlxvault.js'),
            dhxScheduler:path.resolve('WebContent/codebase/sources/dhtmlxscheduler.js'),
            ckeditor:    path.resolve('WebContent/lib/ckeditor/ckeditor.js'),
            zclip:       path.resolve('WebContent/lib/zclip/jquery.zclip.min.js'),
            xdate:       path.resolve('WebContent/lib/xdate/xdate.js'),
            gh_config:   path.resolve('WebContent/js/src/gh.config.js'),
            gh_const:    path.resolve('WebContent/js/src/gh.const.js'),
            gh_data:     path.resolve('WebContent/js/src/gh.data.js'),
            gh_global:   path.resolve('WebContent/js/src/gh.global.js'),
            gh_message:  path.resolve('WebContent/js/src/gh.message.js'),
            gh_util:     path.resolve('WebContent/js/src/gh.util.js'),
            gh_wins:     path.resolve('WebContent/js/src/gh.wins.js')
            //services_asset:     path.resolve('WebContent/js/src/class/window/ServicesAssetsWindow.js')
        }
    }
};
