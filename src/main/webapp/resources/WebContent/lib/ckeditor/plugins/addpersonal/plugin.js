/**
 * CKEditorのカスタムツールバーボタンのテスト
 */
CKEDITOR.plugins.add( 'addpersonal', {
    requires: 'richcombo',
    init: function( editor ) {
        editor.ui.addRichCombo('addpersonal', {
            label: "個人の挿入",
            title: "個人の挿入",
            toolbar: 'others',
            panel: {
                css: [ CKEDITOR.skin.getPath( 'editor' ) ].concat( editor.config.contentsCss ),
                multiSelect: false
            },
            init: function () {
                var self = this;
                var content = [{name: "個人「姓」", value: "{personal_lastName}様"},{name: "個人「姓+名」", value: "{personal_lastName} {personal_firstName}様"},
                               {name: "個人「副：姓 + 副：名 」", value: "{personal_sub_lastName} {personal_sub_firstName}様"}, {name: "個人「副：姓」", value: "{personal_sub_lastName}様"}];
                _.each(content, function(value, key, object) {
                    // value, html, text
                    self.add(value.value, value.name, value.name);
                });
            },
            onClick: function( value ) {
                editor.focus();
                editor.insertHtml("<p>" + value + "</p>");
            }
        });
    }
});
