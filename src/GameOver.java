import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by azama on 5/7/2017.
 */
public class GameOver {
    public static JFrame frame = new JFrame();
    public static JPanel panel = new JPanel();
    public static JLabel label[] = {new JLabel(),new JLabel(), new JLabel()};
    public static JTextField nickname = new JTextField();
    public static JButton submit = new JButton();
    public static ghosts display;
    public static MainClass disp;
    public static int life, points, updPoints;

    public GameOver(final ghosts main,int life,int points){
        this.display = main;
        this.life = life;
        this.points = points;
        display();
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nickname.getText().isEmpty()) JOptionPane.showMessageDialog(null,"You haven't entered any nickname, please enter a nickname","Error",JOptionPane.ERROR_MESSAGE );
                else {
                    FileDo.writeFile(nickname.getText(),updPoints);
                    JOptionPane.showMessageDialog(null,"Your score saved!","Done!",JOptionPane.PLAIN_MESSAGE );
                    new LastRecords(GameOver.this);
                    frame.dispose();
                }
            }
        });
    }
    public GameOver(final MainClass main, int life, int points){
        this.disp = main;
        this.life = life;
        this.points = points;
        display();
        label[0].setText("You won!!!");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nickname.getText().isEmpty()) JOptionPane.showMessageDialog(null,"You haven't entered any nickname, please enter a nickname","Error",JOptionPane.ERROR_MESSAGE );
                else {
                    FileDo.writeFile(nickname.getText(),updPoints);
                    JOptionPane.showMessageDialog(null,"Your score saved!","Done!",JOptionPane.PLAIN_MESSAGE );
                    new LastRecords(GameOver.this);
                    frame.dispose();
                }
            }
        });
    }
    public void display() {
        label[0].setText("Game Over!");
        label[0].setFont(new Font("Serif", Font.PLAIN, 25));
        label[0].setBounds(450, 170, 550, 30);
        if (life > 0) {
            updPoints = points * life;
            label[1].setText("Your Score: " + updPoints);
        }
        else {
            updPoints = points;
            label[1].setText("Your Score: " + updPoints);
        }
        label[1].setFont(new Font("Serif", Font.PLAIN, 20));
        label[1].setBounds(450, 210, 550, 30);
        label[2].setText("Enter your name: ");
        label[2].setBounds(285, 275, 200, 20);
        panel.add(label[2]);
        submit.setText("Save!");
        panel.add(label[0]);
        panel.add(label[1]);
        panel.add(nickname);
        panel.add(submit);
        submit.setBounds(465, 315, 100, 30);
        nickname.setBounds(415, 270, 200, 30);
        panel.setLayout(null);
        frame.add(panel);
        frame.setVisible(true);
        frame.setSize(1000, 646);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
