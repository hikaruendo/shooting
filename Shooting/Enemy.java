package Shooting;

import java.awt.Color;
import java.awt.Graphics;

/**
*敵クラス<p>
*移動処理、描画処理など
*/
public class Enemy extends GameObject
{
	//生存時間（弾を撃つタイミングに使用）
	int counter = 0;
	//体力
	int katasa;
	//種類
	int type;
	//あたり判定フラグ
	boolean ishit = false;
	//プレイヤーの位置を知っておくため、Playerインスタンスへの参照を保持
	Player player;
	//打ち始めフラグ
	boolean startshoot;
	int shootnum;

	/**
	 * コンストラクタ
	 * @param プレイヤークラスのインスタンス（プレイヤーの位置を把握するため）
	 */
	Enemy(Player iplayer)
	{
		//プレイヤーの位置を把握
		player = iplayer;
		//初期化時はactiveでない
		active = false;
	}

	/**
	 * インスタンスを有効にする。インスタンスの使い回しをしているので、
	 * 初期化処理もここで行う。
	 * @param ix 生成する位置(X座標)
	 * @param iy 生成する位置(Y座標)
	 */
	public void activate(double ix, double iy)
	{
		x = ix;
		y = iy;
		type = (int)(Math.random()*3);
		active = true;			//弾のインスタンスを有効にする
		katasa = 10;
		counter = 0;
		@SuppressWarnings("unused")
		boolean ishit = false;
		shootnum = Level.getLevel();
		startshoot = false;
	}

	/**
	 * プレイヤーの弾と衝突したときの振る舞い
	 */
	public void hit()
	{
		//体力減らす
		katasa--;
		ishit = true;
		if (katasa < 0)
		{
			//得られる得点は敵の種類で変わる
			switch(type)
			{
				case 0:
					Score.addScore(20);
					break;
				case 1:
					Score.addScore(20);
					break;
				case 2:
					Score.addScore(100);
					break;
				default:
			}
			//スコアに加算

			//爆発の生成
			ObjectPool.newParticle(x, y, 45, 2);
			ObjectPool.newParticle(x, y, 135, 2);
			ObjectPool.newParticle(x, y, 225, 2);
			ObjectPool.newParticle(x, y, 315, 2);
			active = false;
		}
	}

	/**
	 * 動作を規定する。メインループ１周につき一回呼ばれる
	 */
	public void move()
	{
		//種類で分岐
		switch(type)
		{
			case 0:
				move_enemy0();
				break;
			case 1:
				move_enemy1();
				break;
			case 2:
				move_enemy2();
				break;
			default:
		}
	}

	/**
	 * 敵その１の動作
	 */
	void move_enemy0()
	{
		counter++;
		y++;
		//ゆらゆら
		x += Math.cos(y/20);

		//画面外に出たら消去
		if ( (700 < y) )
		{
			active = false;
		}

		//一定間隔で弾を撃つ
		if ((counter%80)==0)
		{
			Bullet.FireRound(x, y);
		}
	}

	/**
	 * 敵その２の動作
	 */
	void move_enemy1()
	{
		counter++;
		double p = 70;	//静止までの時間
		double q = 300;	//画面のどこで静止するか
		//二次関数で動きを表現
		y = (-q / Math.pow(p,2) * Math.pow((counter - p), 2) + q);

		//画面外に出たら消去
		if ( (-30 > y) )
		{
			active = false;
		}

		//一定間隔で弾を撃つ
		if ((counter%60)==0)
		{
			//撃ち始め
			startshoot = true;
		}

		//撃ち始めフラグが立っていたら、レベルと等しい回数、弾を撃つ
		if (startshoot)
		{
			if (((counter%5)==0)&&(shootnum>0))
			{
				Bullet.FireAim(x, y, player);
				shootnum--;
			}
		}
	}

	/**
	 * 敵その3の動作
	 */
	void move_enemy2()
	{
		counter++;
		y++;
		x += Math.tan(y/50);

		//画面外に出たら消去
		if ( (700 < y) )
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
		//弾が当たっていたら色をオレンジに
		if (ishit)
		{
			g.setColor(Color.orange);
		}else{
			switch(type)
			{
				case 0:
					g.setColor(Color.red);
					break;
				case 1:
					g.setColor(Color.red);
					break;
				case 2:
					g.setColor(Color.yellow);
					break;
				default:
			}
		}
		ishit = false;
		g.drawRect((int)x-16, (int)y-16, (int)32, (int)32);
	}


}

