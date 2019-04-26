package Shooting;

//java.appletパッケージをimport宣言
//java.awtパッケージもimport宣言
//java.awt.eventパッケージもimport宣言
import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class EventSample35 extends Applet implements ActionListener{
  //ボタンとラベルの作成
 Button bu  = new Button("ButtonA");
 Button bu1 = new Button("ButtonB");
 Button bu2 = new Button("ButtonC");
 Label la = new Label();

 public void init(){

 //アプレットの背景色を指定
   setBackground(new Color(0, 255, 255));

//レイアウトの指定

   setLayout(null);

 add(bu);
 add(bu1);
 add(bu2);
 add(la);

 //ボタンとラベルの位置を指定

  bu.setBounds(20,10,60,20);
  bu1.setBounds(90,10,60,20);
  bu2.setBounds(160,10,60,20);
  la.setBounds(20,40,200,20);

  //ボタンにActionListenerを登録します。
  bu.addActionListener(this);
  bu1.addActionListener(this);
  bu2.addActionListener(this);
}
//イベント処理メソッドをオーバーライドします。

public void actionPerformed(ActionEvent e){

  String str = e.getActionCommand();

  if(e.getSource() == bu){

        la.setText(str + " がクリックされました");

 }else if(e.getSource() == bu1){

         la.setText(str + " がクリックされました");
}else if(e.getSource() == bu2){

         la.setText(str + " がクリックされました");

     }

  }
  }

