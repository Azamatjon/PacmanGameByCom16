import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by azama on 5/7/2017.
 */
public class LastRecords {
    public static JFrame frame = new JFrame();
    public static JPanel panel = new JPanel();
    public static JLabel label[] = {new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel()};
    public static JLabel scoreLabels[] = {new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel()};
    public static JButton restart = new JButton("Restart"),exitGame = new JButton("Exit");
    public static GameOver display;

    public LastRecords(final GameOver main) {
        this.display = main;
        display();
        label[0].setBounds(340, 70, 550, 30);
        label[0].setText("Top 10 Records of the Game: ");
        label[0].setFont(new Font("Serif", Font.PLAIN, 25));
        //panel.add(restart);
        panel.add(exitGame);
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainClass().display();
                frame.dispose();
            }
        });
        exitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        restart.setBounds(370, 480, 110, 40);
        exitGame.setBounds(540, 480, 110, 40);
        panel.add(label[0]);
        int diff = 0;
        for (int s = 0; s < label.length; s++){
            if (s > 0) {
                panel.add(label[s]);
                if (s - 2 >= 0) {
                    panel.add(scoreLabels[s - 2]);
                    scoreLabels[s - 2].setBounds(625, 100 + diff, 300, 25);
                }
                label[s].setBounds(365, 100 + diff, 300, 25);

                diff += 30;
            }

        }
        int v = 2;
        for (String[] record : FileDo.getLastTenRecords()) {
            label[v].setText((v - 1) + ". " + record[0]);
            scoreLabels[v - 2].setText(record[1]);
            v++;
        }
    }
    public void display() {
        panel.setLayout(null);
        frame.add(panel);
        frame.setVisible(true);
        frame.setSize(1000, 646);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
