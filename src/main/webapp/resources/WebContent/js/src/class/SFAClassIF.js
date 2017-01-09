/**
 * 反響ツリーから操作される（処理がつながっている）画面要素の共通インターフェースを定義
 * ただし、プロパティは持たせないこと。今回の実装方法では正しく動かないため。
 * また、各実装クラスでIFのメソッドを使う場合はオーバーライドして使うこと。
 */
function GHClassIF() {
}
/**
 * 反響ツリーから顧客IDの配列を受け取り、そのデータをもとに情報を表示する。
 * @param personalIds : array[number]
 */
GHClassIF.prototype.showPage = function(personalIds) {
    console.log("インターフェースのメソッドshowPageが呼び出された", personalIds);
}