/**
 * CKEditorのカスタムツールバーボタンのテスト
 */
CKEDITOR.plugins.add( 'addsignature', {
    icons: 'addsignature',
    init: function( editor ) {
        // Plugin logic goes here...
        editor.addCommand( 'addsignature', {
            exec: function( editor ) {
                // 署名編集ウィンドウを表示する。
                _ghWins.doSignatureWin();
            }
        } );
        editor.ui.addButton( 'addsignature', {
            label: '署名の編集',
            command: 'addsignature',
            toolbar: 'others'
        });
    }
});