package Shooting;

import java.awt.Color;
import java.awt.Graphics;

/**
* 敵弾クラス<p>
* 移動処理、描画処理など
*/
public class Bullet extends GameObject
{
	double direction;
	double speed;
	double speedX;
	double speedY;

	/**
	 * コンストラクタ
	 */
	Bullet()
	{
		//初期化時はactiveでない
		active = false;
	}

	/**
	 * 動作を規定する。メインループ１周につき一回呼ばれる
	 */
	public void move()
	{
		x += speedX;
		y += speedY;

		if ( (x < 0)||(600 < x)||(y < 0)||(700 < y) )
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
		g.drawRect((int)(x-3), (int)(y-3), (int)6, (int)6);
	}

	/**
	 * インスタンスを有効にする。インスタンスの使い回しをしているので、
	 * 初期化処理もここで行う。
	 * @param ix 生成する位置(X座標)
	 * @param iy 生成する位置(Y座標)
	 * @param idirection 向き(単位は度　0-360)
	 * @param ispeed 速度(単位はピクセル)
	 */
	public void activate(double ix, double iy, double idirection, double ispeed)
	{
		x = ix;
		y = iy;
		direction = idirection;
		speed = ispeed;
		active = true;			//弾のインスタンスを有効にする


		//高速化のため、極座標からXY速度に変換しておく。
		double radian;
		radian = Math.toRadians(direction);	//度をラジアンに変換
		speedX = speed * Math.cos(radian);
		speedY = speed * Math.sin(radian);
	}

	/**
	 * 全方位に弾を撃つ
	 */
	public static void FireRound(double x, double y)
	{
		for (int i = 0; i < 360; i += 120 )
		{
			ObjectPool.newBullet(x, y, i, 3);
		}
	}

	//プレイヤーの位置に向けて弾を撃つ
	public static void FireAim(double x, double y, Player player)
	{
		double degree = Math.toDegrees(Math.atan2(player.y - y, player.x - x));
		ObjectPool.newBullet(x, y, degree, 4);
		ObjectPool.newBullet(x, y, degree+20, 4);
		ObjectPool.newBullet(x, y, degree-20, 4);
	}
}

