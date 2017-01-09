/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
//    config.filebrowserImageBrowseUrl = "http://localhost:8080/SFA_CLIENT/lib/kcfinder/browse.php?type=images";
//    config.filebrowserUploadUrl = "http://localhost:8080/SFA_CLIENT/lib/kcfinder/upload.php?type=files";

    // 不要なプラグイン
    config.removePlugins = 'flash,div';
    // カスタマイズのプラグイン
    config.extraPlugins = "addimage,addpersonal,addemploy,savetemplate,addsignature";
    // フォントの設定
    config.font_names='メイリオ;ヒラギノ角ゴ;ＭＳ Ｐゴシック;ＭＳ Ｐ明朝;ＭＳ ゴシック;ＭＳ 明朝;Arial/Arial, Helvetica, sans-serif;Comic Sans MS/Comic Sans MS, cursive;Courier New/Courier New, Courier, monospace;Georgia/Georgia, serif;Lucida Sans Unicode/Lucida Sans Unicode, Lucida Grande, sans-serif;Tahoma/Tahoma, Geneva, sans-serif;Times New Roman/Times New Roman, Times, serif;Trebuchet MS/Trebuchet MS, Helvetica, sans-serif;Verdana/Verdana, Geneva, sans-serif';
};
