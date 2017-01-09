/**
 * CKEditorのカスタムツールバーボタンのテスト
 */
CKEDITOR.plugins.add( 'savetemplate', {
    icons: 'savetemplate',
    init: function( editor ) {
        // Plugin logic goes here...
        editor.addCommand( 'savetemplate', {
            exec: function( editor ) {
                //alert( 'Executing a command for the editor name "' + editor.name + '"!' );
                //editor.insertHtml("<p>{salesName}</p>");
//                if (confirm("ライブラリを上書き保存しますが、よろしいですか？")) {
//                    alert("保存しました。");
//                }
                // ライブラリウィンドウを表示する。
                var dummyObject = {
                        create:  function() {
                            // 何もしない
                        }
                };
                var collection = new LibListCollection(dummyObject, -1);
                _ghWins.doLibWin(0, 0, _ghWins.getMailWinFormData(editor.getData()), collection);
            }
        } );
        editor.ui.addButton( 'savetemplate', {
            label: 'ライブラリとして保存',
            command: 'savetemplate',
            toolbar: 'others'
        });
    }
});
