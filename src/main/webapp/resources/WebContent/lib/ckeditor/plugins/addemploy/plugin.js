/**
 * CKEditorのカスタムツールバーボタンのテスト
 */
CKEDITOR.plugins.add( 'addemploy', {
    requires: 'richcombo',
    init: function( editor ) {
        editor.ui.addRichCombo('addemploy', {
            label: "担当者の挿入",
            title: "担当者の挿入",
            toolbar: 'others',
            panel: {
                css: [ CKEDITOR.skin.getPath( 'editor' ) ].concat( editor.config.contentsCss ),
                multiSelect: false
            },
            init: function () {
                var self = this;
                var content = [{name: "担当者「姓」", value: "{myLastName}"}, {name: "担当者「姓 + 名」", value: "{myLastName} {myFirstName}"}, {name: "担当者「姓(かな)」", value: "{myLastNameKana}"},
                               {name: "担当者「電話番号」", value: "{myTEL}"}, {name: "担当者「メールアドレス」", value: "{myEMail}"},
                               {name: "担当者「携帯電話」", value: "{myMobilephone}"}, {name: "担当者「携帯メールアドレス」", value: "{myMobilephoneMail}"},
                               {name: "担当者「拠点名」", value: "{myLocation}"}, {name: "担当者「拠点電話番号」", value: "{myLocationTEL}"}, {name: "担当者「拠点FAX」", value: "{myLocationFAX}"},
                               {name: "担当者「拠点郵便番号」", value: "{myLocationZIP}"}, {name: "担当者「拠点住所」", value: "{myLocationAddress}"}];
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
