package Shooting;
import java.awt.Graphics;


/**
*ゲームオブジェクトの管理クラス<p>
*プレイヤーや弾、敵などのインスタンスを持ち<p>
*オブジェクト同士の相互作用（衝突処理など）を一括管理する。
*/
public class ObjectPool
{

	/**
	 * 敵インスタンスをあらかじめ生成し、ためておくための配列。
	 */
	static Bullet[] bullet;
	/**
	 * 敵弾インスタンスをあらかじめ生成し、ためておくための配列。
	 */
	static Enemy[] enemy;
	/**
	 * プレイヤーの弾インスタンスをあらかじめ生成し、ためておくための配列。
	 */
	static MyBullet[] mybullet;
	/**
	 * 爆発インスタンスをあらかじめ生成し、ためておくための配列。
	 */
	static Particle[] particle;

	/**
	 * プレイヤーのインスタンス。
	 */
	Player player;

	//定数
	static final int DIST_PLAYER_TO_BULLET = 18;
	static final int DIST_PLAYER_TO_ENEMY = 26;
	static final int DIST_ENEMY_TO_MYBULLET = 16;

	//最大数の設定
	static final int BULLET_MAX = 100;
	static final int ENEMY_MAX = 100;
	static final int PARTICLE_MAX = 100;
	static final int MYBULLET_MAX = 5;


	/**
	 * コンストラクタ
	 */
	ObjectPool()
	{
		//プレイヤーを作る
		player = new Player(300, 600, 4);
		player.active = true;

		//弾の配列を確保し、配列の要素分インスタンスを作る
		bullet = new Bullet[BULLET_MAX];
		for(int i = 0; i < bullet.length; i++)
		{
				bullet[i] = new Bullet();
		}

		//敵の配列を確保し、配列の要素分インスタンスを作る
		enemy = new Enemy[ENEMY_MAX];
		for(int i = 0; i < enemy.length; i++)
		{
				enemy[i] = new Enemy(player);
		}

		//プレイヤーの弾の配列を確保し、配列の要素分インスタンスを作る
		mybullet = new MyBullet[MYBULLET_MAX];
		for(int i = 0; i < mybullet.length; i++)
		{
				mybullet[i] = new MyBullet();
		}

		//爆発の配列を確保し、配列の要素分インスタンスを作る
		particle = new Particle[PARTICLE_MAX];
		for(int i = 0; i < particle.length; i++)
		{
				particle[i] = new Particle();
		}
	}

	/**
	 * 描画処理。すべてのゲームオブジェクトを描画する。
	 */
	public void drawAll(Graphics g)
	{
        doGameObjects(g, bullet);
        doGameObjects(g, enemy);
        doGameObjects(g, mybullet);
        doGameObjects(g, particle);
		player.draw(g);
	}

	/**
	 * ゲームオブジェクトの配列を参照し、
	 * activeになっているインスタンスを移動・描画する
	 */
    public void doGameObjects(Graphics g, GameObject[] objary)
    {
        for (int i = 0; i < objary.length; i++) {

            if (objary[i].active == true)
			{
                objary[i].move();
                objary[i].draw(g);				//委譲
            }
        }
    }

    /**
     * 弾の生成・初期化（実際は配列のインスタンスを使い回す）
     * @param ix 生成先x座標
     * @param iy 生成先y座標
     * @param idirection 動かす方向
     * @param ispeed 動かす速度
     * @return 弾のID（空きが無ければ-1）
     */
	public static int newBullet(double ix, double iy, double idirection, double ispeed)
	{
		for (int i = 0; i < BULLET_MAX; i++)
		{
			if ((bullet[i].active) == false)
			{
				bullet[i].activate(ix, iy, idirection, ispeed);
				return i;
			}
		}
		return -1;		//見つからなかった
	}

    /**
     * 敵の生成・初期化（実際は配列のインスタンスを使い回す）
     * @param ix 生成先x座標
     * @param iy 生成先y座標
     * @return 敵のID（空きが無ければ-1）
     */
	public static int newEnemy(double ix, double iy)
	{
		for (int i = 0; i < ENEMY_MAX; i++)
		{
			if ((enemy[i].active) == false)
			{
				enemy[i].activate(ix, iy);
				return i;
			}
		}
		return -1;		//見つからなかった
	}

    /**
     * プレイヤー弾の生成・初期化（実際は配列のインスタンスを使い回す）
     * @param ix 生成先x座標
     * @param iy 生成先y座標
	 * @return プレイヤー弾のID（空きが無ければ-1）
     */
	public static int newMyBullets(double ix, double iy)
	{
		for (int i = 0; i < MYBULLET_MAX; i++)
		{
			if ((mybullet[i].active) == false)
			{
				mybullet[i].activate(ix, iy);
				return i;
			}
		}
		return -1;		//見つからなかった
	}

    /**
     * 爆発の生成・初期化（実際は配列のインスタンスを使い回す）
     * @param ix 生成先x座標
     * @param iy 生成先y座標
     * @param idirection 動かす方向
     * @param ispeed 動かす速度
	 * @return 爆発のID（空きが無ければ-1）
     */
	public static int newParticle(double ix, double iy, double idirection, double ispeed)
	{
		for (int i = 0; i < PARTICLE_MAX; i++)
		{
			if ((particle[i].active) == false)
			{
				particle[i].activate(ix, iy, idirection, ispeed);
				return i;
			}
		}
		return -1;		//見つからなかった
	}

	/**
	 * プレイヤーが弾を撃つ
	 */
	public void shotPlayer()
	{
		//プレーヤーが可視の時だけ弾が打てる
		if (player.active)
		{
			newMyBullets(player.x, player.y);
		}
	}

	/**
	 * プレイヤーが移動する処理
	 * @param keyinput キー入力クラスのインスタンス。
	 */
	public void movePlayer(KeyInput keyinput)
	{
		player.move(keyinput.getXDirection(), keyinput.getYDirection());
	}

	/**
	 * ２点間の距離を返す
	 * @param ga ゲームオブジェクト
	 * @param gb 比較先ゲームオブジェクト
	 * @return 距離
	 */
	public double getDistance(GameObject ga, GameObject gb)
	{
		//三平方の定理
		double Xdiff = Math.abs(ga.x - gb.x);
		double Ydiff = Math.abs(ga.y - gb.y);
		return Math.sqrt(Math.pow(Xdiff,2) + Math.pow(Ydiff,2));
	}

	/**
	 * 衝突判定
	 */
	public void getColision()
	{
		//弾vsプレイヤーの衝突
        for (int i = 0; i < bullet.length; i++) {
			if ((bullet[i].active)&&(player.active))
			{
				//あたり判定
				if (getDistance(player, bullet[i]) < DIST_PLAYER_TO_BULLET)
				{
					//プレイヤー消滅（見えなくするだけ）
					player.active = false;

					//爆発を生成
					for (int j = 0; j < 360; j += 20)
					{
						newParticle(player.x, player.y, j, 2);
					}

					//弾消滅
					bullet[i].active = false;
				}
			}
        }

		//プレイヤーの弾vs敵の衝突
        for (int i = 0; i < enemy.length; i++) {
			if (enemy[i].active == true)
			{
				for (int j = 0; j < mybullet.length; j++)
				{
					if (mybullet[j].active == true)
					{
						//あたり判定
						if (getDistance(enemy[i], mybullet[j]) < DIST_ENEMY_TO_MYBULLET)
						{
							newParticle(mybullet[j].x, mybullet[j].y, 270, 2);
							//敵の体力を減らす
							enemy[i].hit();
							//弾消滅
							mybullet[j].active = false;
						}
					}
				}
			}
        }

		//敵vsプレイヤーの衝突
        for (int i = 0; i < enemy.length; i++) {
			if ((enemy[i].active)&&(player.active))
			{
				//あたり判定
				if (getDistance(player, enemy[i]) < DIST_PLAYER_TO_ENEMY)
				{
					//プレイヤー消滅（見えなくするだけ）
					player.active = false;

					//爆発を生成
					for (int j = 0; j < 360; j += 20)
					{
						newParticle(player.x, player.y, j, 2);
					}
					//敵は消滅しない
				}
			}
        }

	}

	/**
	 * ゲームオーバーかどうかを返す
	 * @return ゲームオーバーならtrue
	 */
	public boolean isGameover()
	{
		return !player.active;
	}



}
