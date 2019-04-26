package Shooting;
import java.awt.Color;
import java.awt.Graphics;

/**
*パーティクルクラス<p>
*（爆発や、敵に弾が当たったときの効果）<p>
*移動処理、描画処理など
*/
public class Particle extends GameObject
{
	double direction;
	double speed;
	double speedX;
	double speedY;
	int size;

	Particle()
	{
		active = false;
	}

	/**
	 * 動作を規定する。メインループ１周につき一回呼ばれる
	 */
	public void move()
	{
		x += speedX;
		y += speedY;
		size--;

		if ( (x < 0)||(600 < x)||(y < 0)||(700 < y) )
		{
			active = false;
		}
		if (size < 0)
		{
			active = false;
		}
	}

	/**
	 * 描画処理。
	 * １ループで一回呼ばれる。
	 * @param g 描画先グラフィックハンドル
	 */
	public void draw(Graphics g)
	{
		g.setColor(Color.red);
		g.drawOval((int)(x-size/2), (int)(y-size/2), size, size);
	}

	//初期化処理もここで行う（オブジェクトを使い回しているので）
	public void activate(double ix, double iy, double idirection, double ispeed)
	{
		x = ix;
		y = iy;
		direction = idirection;
		speed = ispeed;
		active = true;			//弾のインスタンスを有効にする
		size = 30;

		//高速化のため、極座標からXY速度に変換しておく。
		double radian;
		radian = Math.toRadians(direction);	//度をラジアンに変換
		speedX = speed * Math.cos(radian);
		speedY = speed * Math.sin(radian);
	}
}

