import javax.swing.*;

/**
 * Created by zzhfeng on 2017/7/7.
 */
class MyButton extends JButton{
     MyButton(String title,int x , int y, int width, int height){
        setText(title);
        setBounds(x,y,width,height);
    }
}
