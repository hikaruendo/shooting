package Shooting;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
*キーボードの入力を管理するクラス<p>
*スペースキーで撃つ<p>
*カーソルキーで移動　と定義されている
*/
public class KeyInput extends KeyAdapter
{
	//キーボード入力の状態を保持するフィールド
	boolean keyup;
	boolean keydown;
	boolean keyleft;
	boolean keyright;

	/**
	 * 押された瞬間を判別するため、0-2の値をとる
	 * 0:押されていない 1:押されている 2:ついさっき押されたばかり
	 */
	int keyshot;
	int keydata;


	KeyInput() {
		keyup = false;
		keydown = false;
		keyleft = false;
		keyright = false;
		keyshot = 0;
		keydata = 0;

	}

	/**
	 * キーが押されたときに呼ばれる処理。
	 * 変数にキー状態を保存する。
	 */
	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode();
		if (keycode == KeyEvent.VK_LEFT)
		{
			keyleft = true;
		}
		if (keycode == KeyEvent.VK_RIGHT)
		{
			keyright = true;
		}
		if (keycode == KeyEvent.VK_UP)
		{
			keyup = true;
		}
		if (keycode == KeyEvent.VK_DOWN)
		{
			keydown = true;
		}
		if (keycode == KeyEvent.VK_SPACE)
		{
			//初めて押された
			if (keyshot == 0)
			{
				//押された瞬間を表すフラグ
				keyshot = 2;
			}
			else
			{
				//押されている状態
				keyshot = 1;
			}
		}
		if (keycode == KeyEvent.VK_D)
		{
			//初めて押された
			if (keydata == 0)
			{
				//押された瞬間を表すフラグ
				keydata = 2;
			}
			else
			{
				//押されている状態
				keydata = 1;
			}
		}

		if (keycode == KeyEvent.VK_ESCAPE)
		{
			System.exit(0);
		}
	}

	/**
	 * 押されていたキーを放したときに呼ばれる処理
	 */
	public void keyReleased(KeyEvent e) {
		int keycode = e.getKeyCode();
		if (keycode == KeyEvent.VK_LEFT)
		{
			keyleft = false;
		}
		if (keycode == KeyEvent.VK_RIGHT)
		{
			keyright = false;
		}
		if (keycode == KeyEvent.VK_UP)
		{
			keyup = false;
		}
		if (keycode == KeyEvent.VK_DOWN)
		{
			keydown = false;
		}
		if (keycode == KeyEvent.VK_SPACE)
		{
			keyshot = 0;
		}
		if (keycode == KeyEvent.VK_D)
		{
			keyshot = 0;
		}
	}

	/**
	 * x軸の入力を取得
	 * @return -1:右　0:なし　1:左
	 */
	public int getXDirection()
	{
		int ret = 0;	//静止状態
		if (keyright)
		{
			ret = 1;
		}
		if (keyleft)
		{
			ret = -1;
		}
		return ret;
	}

	/**
	 * y軸の入力を取得
	 * @return -1:上　0:なし　1:下
	 */
	public int getYDirection()
	{
		int ret = 0;	//静止状態
		if (keydown)
		{
			ret = 1;
		}
		if (keyup)
		{
			ret = -1;
		}
		return ret;
	}

	/**
	 * ショットボタン（＝スペースキー）の状態を取得する
	 * @return 0:押されていない 1:押されている 2:ついさっき押されたばかり
	 */
	public int checkShotKey()
	{
		int ret = keyshot;
		if (keyshot==2)
		{
			keyshot = 1;
		}
		return ret;
	}

	public int checkDataKey()
	{
		int ret = keydata;
		if (keydata==2)
		{
			keydata = 1;
		}
		return ret;
	}


}
