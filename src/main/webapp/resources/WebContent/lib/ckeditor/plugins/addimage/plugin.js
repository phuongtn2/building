/**
 * CKEditorのカスタムツールバーボタンのテスト
 */
CKEDITOR.plugins.add( 'addimage', {
    icons: 'addimage',
    init: function( editor ) {
        // Plugin logic goes here...
        editor.addCommand( 'addimage', {
            exec: function( editor ) {
                _ghWins.doImageUploadWin(editor);
            }
        } );
        editor.ui.addButton( 'addimage', {
            label: '画像の挿入',
            command: 'addimage',
            toolbar: 'others'
        });
    }
});