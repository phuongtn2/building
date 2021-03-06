/**
 * SFAのメッセージ出力定数クラス
 */
var _MSG = (function(){
    "use strict";
        // 本番モード
        return {
            //共通
            COMMON : {
                UNLOAD: "SFA/CRMのページの確認です。"
            },
            //ログイン
            S01F000 : {
            },
            //ログアウト
            S01F005 : {
            },
            //メニュー
            S01F010 : {
            },
            //Top
            S01F020 : {
            },
            //担当者ウィンドウ
            S01F500 : {
            },
            //希望地域ウィンドウ
            S01F510 : {
            },
            //現場名ウィンドウ
            S01F520 : {
            },
            //物件ウィンドウ
            S01F530 : {
            },
            //沿線ウィンドウ
            S01F540 : {
            },
            //駅ウィンドウ
            S01F545 : {
            },
            //郵便番号ウィンドウ
            S01F550 : {
            },
            //顧客検索ウィンドウ表示
            S01F560 : {
            },
            //追客履歴ウィンドウ表示
            S01F570 : {
            },
            //問合せ物件ウィンドウ表示
            S01F580 : {
            },
            //反響ツリー
            S02F010 : {
            },
            //簡易検索
            S02F020 : {
            },
            //詳細検索
            S02F030 : {
            },
            //顧客メイン
            S03F010 : {
                M_001 : "顧客ステータスを契約にして保存すると、契約済み顧客となりますが、本当によろしいですか？"
            },
            //顧客詳細ウィンドウ
            S03F020 : {
            },
            //担当者へ転送ウィンドウ
            S03F030 : {
            },
            //クレームウィンドウ
            S03F040 : {
            },
            //契約一覧
            S03F050 : {
            },
            //新規契約
            S03F060 : {
            },
            //契約増減
            S03F070 : {
            },
            //解約
            S03F080 : {
            },
            //対応履歴タブ
            S03F090 : {
                M_001 : "対象の対応履歴を削除します。<br>この機能は間違えて登録してしまった場合以外は使用しないでください。<br>(不正に削除した場合、削除した理由を提出していただきます。)"
            },
            //電話つながるグラフ
            S03F100 : {
            },
            //電話掛けTop
            S04F010 : {
            },
            //一斉メールTop
            S05F010 : {
            },
            //メール作成ウィンドウ
            S05F020 : {
            },
            // library management
            S05F030 : {
                M_001 : "フォルダの階層は10階層までしか作成できません。"
                ,M_002 : "選択しているフォルダは削除できません。<br>システムフォルダやフォルダの中にライブラリを持っているフォルダは削除できません。"
                ,M_003 : "選択しているライブラリを削除する権限がないため、削除できません。"
                ,M_004 : "同じ名前のフォルダがあるため、処理ができません。"
                ,M_005 : "同じ名前のライブラリがあるため、処理ができません。"
                ,M_006 : "このフォルダー以下に含まれる全ファイルを表示するため、表示に時間がかかります。<br>よろしいですか？"
                ,M_007 : "選択しているフォルダは名前の変更ができません。<br>システムフォルダやごみ箱は変更できません。"
                ,M_008 : "該当フォルダにはフォルダを作成することができません。"
                ,M_009 : "他の事業部のフォルダー内は操作することができません。"
                ,M_010 : "選択したライブラリを操作する権限がありません。"
                ,M_011 : "他の事業部のライブラリは操作することができません。"
            },
            //ライブラリ選択ウィンドウ
            S05F050 : {
            },
            //ライブラリ作成／編集ウィンドウ
            S05F060 : {
            },
            //未送信管理
            S05F070 : {
            },
            //DMTop
            S06F010 : {
            },
            //案内一覧
            S07F010 : {
            },
            //案内予約登録／変更ウィンドウ
            S07F020 : {
                M_001: "ルールで決まっている７日よりも前の予約を登録しようとしていますが、よろしいですか？"
            },
            //行動実績Top
            S09F010 : {
            },
            //帳票Top
            S09F020 : {
            },
            //案内予定表
            S09F500 : {
            },
            //課毎契約一覧
            S09F505 : {
            },
            //今週の売上
            S09F510 : {
            },
            //月次報告-店舗×媒体契約率など
            S09F515 : {
            },
            //鳴り物件一覧
            S09F520 : {
            },
            //案内集計
            S09F525 : {
            },
            //営業の追客実績集計
            S09F530 : {
            },
            //月次報告-ポータルサイト評価
            S09F535 : {
            },
            //反響数集計（現：反響簿）
            S09F540 : {
            },
            //役員会資料用データ
            S09F545 : {
            },
            //経営ボード会議用データ
            S09F550 : {
            },
            //販売MGR会議用データ
            S09F555 : {
            },
            //センター長会議資料用データ
            S09F560 : {
            },
            //契約一覧データ
            S09F565 : {
            },
            //営業マン別対応履歴（戸建）
            S09F570 : {
            },
            //反響媒体別件数
            S09F700 : {
            },
            //成績表（１）
            S09F705 : {
            },
            //成績表（２）
            S09F710 : {
            },
            //成績表（３）
            S09F715 : {
            },
            //成績表（４）
            S09F720 : {
            },
            //販売進捗状況
            S09F725 : {
            },
            //業務予定書
            S09F730 : {
            },
            //見込み客リスト
            S09F735 : {
            },
            //交渉件数
            S09F740 : {
            },
            //物件別交渉件数
            S09F745 : {
            },
            //期間内進捗状況表
            S09F750 : {
            },
            //週間来場者分析
            S09F755 : {
            },
            //価格表（進捗表）
            S09F760 : {
            },
            //契約一覧
            S09F765 : {
            },
            //指示・報告Top
            S10F010 : {
                M_001 : "指示報告を完了するためには、報告を入力してください。"
            },
            //指示・報告タブ
            S10F020 : {
            },
            //指示ウィンドウ
            S10F030 : {
            },
            //担当変更Top
            S11F010 : {
            },
            //担当変更ウィンドウ
            S11F020 : {
            },
            //レコメンド物件タブ
            S12F010 : {
            },
            //問合せ物件タブ
            S12F020 : {
                M_001 : "問合せ物件を削除します。よろしいですか？",
                M_002 : "本当にこのイベントを削除してもよろしいですか？"
            },
            //成績表Top
            S13F010 : {
            },
            //顧客登録・検索
            C01F010 : {
                M_001 : "選択した問合せ物件を削除しますか？"
                ,M_002 : "現在編集中の顧客情報は破棄されます。よろしいですか？"
                ,M_003 : "姓とかな（姓）のどちらかは必ず入力してください。"
                ,M_004 : "反響日は必ず入力してください。"
                ,M_005 : "担当者は必ず入力してください。"
                ,M_006 : "媒体は必ず入力してください。"
                ,M_007 : "選択した反響に変更します。よろしいですか？"
                ,M_008 : "画面下の重複エントリー一覧から対象となる行を選択してください。"
                ,M_009 : "希望地域の文字数が多すぎます。"
                ,M_010 : "再反基準日は必ず入力してください。"
                ,M_011 : "登録しました。引き続き編集しますか？"
                ,M_012 : "顧客を削除します。よろしいですか？"
            },
            //顧客検索結果
            C01F020 : {
                M_001 : "再反基準日は必ず入力してください。"
            },
            //Web未処理反響一覧
            C01F030 : {
                M_001 : "該当の反響情報を削除しますか？<br>(削除を行うと、反響情報のデータが全て消えます。)",
                M_002 : "該当の反響情報を登録しますか？"
            },
            //顧客の結合
            C01F040 : {
                M_001 : "結合処理を実行します。サブ情報は削除されます。一度削除したデータは元に戻せません。結合処理を実行してもよろしいですか？",
                M_002 : "その顧客情報は既に選択されています。",
                M_003 : "他事業部間の顧客同士は結合ができません。",
                M_004 : "現在編集中の顧客情報は破棄されます。よろしいですか？"
            },
            //リレーション登録・解除
            C01F050 : {
            },
            //担当者へ連絡ウィンドウ
            C01F060 : {
            },
            //リンク
            C01F070 : {
            },
            //リンク（マスタメンテ）タブ
            M01F010 : {
            },
            //現場名マスタ
            M01F020 : {
            },
            //物件マスタ
            M01F030 : {
            },
            //反響媒体マスタ
            M01F040 : {
            },
            //組織マスタ
            M01F050 : {
                M_001 : "組織は5階層までしか作成できません。"
                ,M_002 : "選択した組織を削除しますか？"
                ,M_003 : "削除しようとした組織には子組織または所属するユーザ存在するため削除できません。<br>小組織がある場合は、先にそちらを削除してください。<br>また所属するユーザがいれば、そのユーザを別の組織に移動させてください。"
            },
            //ユーザマスタ
            M01F060 : {
            },
            //カレンダーマスタ
            M01F070 : {
            },
            //目標管理マスタ
            M01F080 : {
            }
        };
}());
