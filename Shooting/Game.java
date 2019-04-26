package Shooting;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
*ゲームクラス（ここから始まる）<p>
*画面の作成
*/
public class Game extends Frame
{
	/**
	 * メインクラス
	 */
	public static void main(String args[])
	{
		//フレームの作成
		new Game();
	}

	/**
	 * 引数なしのコンストラクタ
	 */
	Game()
	{
		//*** ウィンドウの初期化
		//タイトル
		super("しゅ～てぃんぐげーむ");

		//クローズボタンによる終了処理の実装
		addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });

		setSize(600, 700);		//ウィンドウのサイズ

		//*** キャンバスの初期化
		MyCanvas mc = new MyCanvas();
		add(mc);				//フレームにキャンバスを追加
		setVisible(true);		//ウィンドウの表示
		//ゲームデータの初期化
		mc.init();
		//スレッドを作成
		mc.initThread();
	}
}

