package Shooting;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
*タイトルクラス<p>
*タイトル画面描画<p>
*ゲームオーバー画面表示
*/
public class Title
{
	//アニメーション用カウンタ
	int count;
	Font titleFont;
	Font infoFont;
	Font overFont;

	/**
	 * コンストラクタ<p>
	 * タイトル用に、フォントクラスのインスタンスを生成する
	 */
	Title()
	{
		count = 0;
		titleFont = new Font("fantasy", Font.BOLD, 30);
		infoFont = new Font("fantasy", Font.BOLD, 20);
		overFont = new Font("fantasy", Font.BOLD, 50);

	}

	/**
	 * タイトル画面の描画処理。
	 * １ループで一回呼ばれる。
	 * @param g 描画先グラフィックハンドル
	 */
	public void drawTitle(Graphics g)
	{
		g.setColor(Color.white);
		g.setFont(titleFont);
		g.drawString("しゅ～てぃんぐげーむ",120,250);

		g.setFont(infoFont);
		g.drawString("SPACEを押してスタート",165,480);
	}

	/**
	 * ゲームオーバーの描画処理。
	 * １ループで一回呼ばれる。
	 * @param g 描画先グラフィックハンドル
	 */
	public void drawGameover(Graphics g)
	{
		g.setColor(Color.white);
		g.setFont(overFont);
		g.drawString("GAMEOVER",150,300);
	}

	public void drawData(Graphics g)
	{
		g.setColor(Color.white);
		g.setFont(overFont);
		g.drawString("DATABASE",150,300);
	}

}

