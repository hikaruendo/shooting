package Shooting;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
*描画先コンポーネントクラス(Canvasを継承)<p>
*メインループもここ
*/
public class MyCanvas extends Canvas implements Runnable, ActionListener{
	ObjectPool objectpool;
	KeyInput keyinput;
	Image imgBuf;
	Graphics gBuf;
	Random random;
	Title title;
	Score score;
	Level level;
	Button bu = new Button("ButtonS");
	Button bu1 = new Button("ButtonR");
	Label la = new Label();

	/**
	 * シーン管理変数<p>
	 * 0:タイトル画面 1:ゲームのメイン画面
	 */
	public int scene;
	static final int SCENE_TITLE = 0;
	static final int SCENE_GAMEMAIN = 1;
	static final int SCENE_DATA = 2;


	public boolean gameover;
	int counter;


	//押されている
	static final int SHOT_PRESSED = 1;
	//今押されたばかり
	static final int SHOT_DOWN = 2;
	int shotkey_state;
	int datakey_state;


	/**
	 * コンストラクタ
	 */
	MyCanvas()
	{
		//キーリスナ実装
		keyinput = new KeyInput();
		addKeyListener(keyinput);
		setFocusable(true);				//フォーカスを取得できるようにする。
		random = new Random();			//乱数オブジェクト
		title = new Title();
		score = new Score();
		level = new Level();

	}

	/**
	 * 初期化処理<p>
	 * アプリケーションの開始時、またはリスタート時に呼ばれ、<br>
	 * ゲーム内で使われる変数の初期化を行う。
	 */
	public void init()
	{
		objectpool = new ObjectPool();
		Score.loadScore();

		//シーンはタイトル画面
		scene = SCENE_TITLE;
		gameover = false;
		//レベルの初期化
		Level.initLevel();
		//スコアの初期化
		Score.initScore();
	}

	/**
	 * 外部からスレッドを初期化する。
	 */
	public void initThread()
	{
		Thread thread = new Thread(this);
		thread.start();
	}

	/**
	 * 描画処理<p>
	 * repaint()の際に呼ばれて、
	 * オフスクリーンバッファから画像をコピーし表示する。
	 * @param g 描画先グラフィックハンドル
	 */
	public void paint(Graphics g)
	{
		//オフスクリーンバッファの内容を自分にコピー
		g.drawImage(imgBuf, 0, 0, this);
	}

	/**
	 * メインループ
	 */
	public void run()
	{
		//オフスクリーンバッファ作成
		imgBuf = createImage(600, 700);
		gBuf = imgBuf.getGraphics();

		for(counter = 0; ; counter++)
		{
			shotkey_state = keyinput.checkShotKey();
			datakey_state = keyinput.checkDataKey();
			//バッファをクリア
			gBuf.setColor(Color.black);
			gBuf.fillRect(0, 0, 600, 700);

			//シーン遷移用の変数で分岐
			switch (scene)
			{
				//タイトル画面
				case 0:
					title.drawTitle(gBuf);
					score.drawScore(gBuf);
					score.drawHiScore(gBuf);

					//スペースキーが押された
					if (shotkey_state == SHOT_DOWN)
					{
						//メイン画面に行く
						scene = SCENE_GAMEMAIN;
					}else if(datakey_state == SHOT_DOWN)
					{
						scene = SCENE_DATA;
					}
					break;

				//ゲームのメイン画面
				case 1:
					gameMain();
					break;

				case 2:
					dataMain();
					break;
			}

			//再描画を要求
			repaint();

			try{
				Thread.sleep(15);				//ループのウェイト
			}
			catch(InterruptedException e)
			{}
		}
	}

	/**
	 * 画面をいちいちクリアしないようにするため、
	 * updateメソッドをオーバーライドする。
	 * @param g 更新先グラフィックハンドル
	 */
	public void update(Graphics g)
	{
		paint(g);
	}

	/**
	 * ゲーム画面のメイン処理
	 */
	void gameMain()
	{
		//ゲームオーバーか？
		if (objectpool.isGameover())
		{
			//ゲームオーバー文字を表示
			title.drawGameover(gBuf);
			if (shotkey_state == SHOT_DOWN)
			{
				//スコアをハイスコアに適用、必要があればセーブ
				Score.compareScore();

				//ゲームを再初期化
				init();
			}
		}

		//衝突の判定
		objectpool.getColision();
		objectpool.movePlayer(keyinput);

		//敵出現間隔：レベルに応じて短くなる
		if (counter % (100 - Level.getLevel() * 10) == 0)
		{
			ObjectPool.newEnemy(100 + random.nextInt(300), 0);
		}

		//500フレーム経過するとレベルが上昇
		if ((counter % 500) == 0)
		{
			Level.addLevel();
		}

		level.drawLevel(gBuf);

		//スペースキーが押されており、かつカウンタが３の倍数なら弾を撃つ（等間隔に弾を撃てる）
		if ((shotkey_state == SHOT_PRESSED)&&(counter % 3 == 0))
		{
			objectpool.shotPlayer();
		}

		//ゲームオブジェクトの一括描画処理
		objectpool.drawAll(gBuf);
		//スコア描画
		score.drawScore(gBuf);
		score.drawHiScore(gBuf);

	}

	void dataMain()
	{

		bu.setBounds(20,10,60,20);
		bu1.setBounds(90,10,60,20);
		la.setBounds(20,40,200,20);


		bu.addActionListener(this);
	    bu1.addActionListener(this);

		//title.drawData(gBuf);
		if (shotkey_state == SHOT_DOWN)
		{
			objectpool = new ObjectPool();
			Score.loadScore();

			//シーンはタイトル画面
			scene = SCENE_TITLE;
			gameover = false;
		}
	}

//	private void add(Button bu12) {
//		// TODO 自動生成されたメソッド・スタブ
//
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();

	    if(e.getSource() == bu){

	    	la.setText(str + " がクリックされました");

	    }else if(e.getSource() == bu1){

	    	la.setText(str + " がクリックされました");

	    }

	}
}

