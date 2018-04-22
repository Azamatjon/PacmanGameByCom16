
import org.jetbrains.annotations.Nullable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.*;


public class MainClass {
    public static int c = 0, x = 370, y = 340;
    public static String side = "r";
    public static JFrame frame = new JFrame();
    public static JLabel[] lg = {new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel()};
    public static JLabel lmap = new JLabel();
    public static JLabel label = new JLabel(),score_ = new JLabel();
    public static JLabel[] lifes = {new JLabel(),new JLabel(),new JLabel()};
    public static JLabel[][] coins = new JLabel[24][200];
    public static JPanel panel = new JPanel();
    public static int level = 1;
    public static images animate;
    public static ImageIcon[] man = new ImageIcon[4];
    public static int WIDTH = 1005;
    public static int HEIGHT = 675;
    public static ImageIcon man1,man2,man3,man4,man5,man6,man7,man8,coin;
    public static straight straigh;
    public static boolean isGoing = false;
    public static boolean isGoingGhosts = true;
    public static ImageIcon[] gmaps = {new ImageIcon(getImage("1_1.png",true)),new ImageIcon(getImage("1_2.png",true))};
    public static ImageIcon[][] ghosts = {{new ImageIcon(getImage("1.png",false)),new ImageIcon(getImage("1_2.png",false)),new ImageIcon(getImage("1_3.png",false)),new ImageIcon(getImage("1_4.png",false))},{new ImageIcon(getImage("2.png",false)),new ImageIcon(getImage("2_2.png",false)),new ImageIcon(getImage("2_3.png",false)),new ImageIcon(getImage("2_4.png",false))},{new ImageIcon(getImage("3.png",false)),new ImageIcon(getImage("3_2.png",false)),new ImageIcon(getImage("3_3.png",false)),new ImageIcon(getImage("3_4.png",false))},{new ImageIcon(getImage("4.png",false)),new ImageIcon(getImage("4_2.png",false)),new ImageIcon(getImage("4_3.png",false)),new ImageIcon(getImage("4_4.png",false))}};
    public static int points = 0;
    public static String listened = new String();
    public static String lastPressed = "";
    public static Random rnd = new Random();
    public int timeBetweenLevels = 100;
    public int fixedTime;
    public static int life = 3;
    public int timeToWait = 0;
    public static int timerToStart = 0;
    public static int forInfoPanel = 50;
    @Nullable
    public static BufferedImage getImage(String filename, boolean isMap) {
        try {
            /* Code Change */
            File in;
            if (isMap) in = new File("images/maps/" + filename);
            else in = new File("images/persons/" + filename);
            return ImageIO.read(in);
        } catch (IOException e) {
            System.out.println("(" + filename + ") " + e);
            System.exit(1);
        }
        return null;
    }
    public void load(){
        man1 = new ImageIcon(getImage("man_r.png",false));
        man2 = new ImageIcon(getImage("man_r_2.png",false));

        man3 = new ImageIcon(getImage("man_l.png",false));
        man4 = new ImageIcon(getImage("man_l_2.png",false));

        man5 = new ImageIcon(getImage("man_u.png",false));
        man6 = new ImageIcon(getImage("man_u_2.png",false));

        man7 = new ImageIcon(getImage("man_d.png",false));
        man8 = new ImageIcon(getImage("man_d_2.png",false));

        coin = new ImageIcon(getImage("bonuses/coin.png",false));
        /* ----------------- GHOSTS ------------------------*/
    }
    public static void updateScreen(){
        int lf = 0;
        for (int g = 0; g < lifes.length; g++){
            //System.out.println("worked: "+ life);
            if (lf < life) {
                lf++;
                lifes[g].setVisible(true);
            }
            else  lifes[g].setVisible(false);
        }
        score_.setText("Score: " + points);
    }
    public void display() {
        frame.add(new TestPanel());
        frame.pack();
        panel.setBackground(Color.BLACK);
        load();
        for (int g = 0; g < 24; g++) {
            for (int j = 0; j < limiter.coinsInParts[g].length; j++) {
                coins[g][j] = new JLabel();
                coins[g][j].setIcon(coin);
                coins[g][j].setBounds(limiter.coinsInParts[g][j][0], limiter.coinsInParts[g][j][1] + forInfoPanel,5,5);
                panel.add(coins[g][j]);
            }
        }
        label.setIcon(man1);

        panel.setLayout(null);
        int pos = rnd.nextInt(3);
        if (pos == 1){
            MainClass.x = 210;
            MainClass.y = 295;
            MainClass.label.setBounds(210,295 + MainClass.forInfoPanel,33,50);
        } else if (pos == 0) {
            MainClass.x = 760;
            MainClass.y = 295;
            MainClass.label.setBounds(760,295 + MainClass.forInfoPanel,33,50);
        } else {
            MainClass.x = 480;
            MainClass.y = 545;
            MainClass.label.setBounds(480,545 + MainClass.forInfoPanel,33,50);
        }
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // Set the start position
        frame.setSize(WIDTH, HEIGHT + 50);
        frame.setLocationRelativeTo(null);
        //Set a default close action
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set a title
        frame.setTitle("GAME title");

        //Disable resize
        frame.setResizable(false);
        frame.setVisible(true);
        panel.add(label);



        animate = new images();
        animate.update();
        int numGhosts = 5;
        ghosts ghsts = new ghosts(440,295 + forInfoPanel,520,295 + forInfoPanel, numGhosts);
        straigh.go();
        frame.getContentPane().add(panel);
        map.setMap();

        int differenceBetweenLifes = 43;
        for (int g = 0; g < lifes.length; g++){
            lifes[g].setIcon(man1);
            lifes[g].setBounds((g != 0?(20 + (differenceBetweenLifes * g)): 20), 8, 33,33);
            panel.add(lifes[g]);
        }
        score_.setText("Score: " + points);
        score_.setFont(new Font("Serif", Font.PLAIN, 15));
        score_.setForeground(Color.white);
        score_.setBounds(900, 8, 150, 33);
        panel.add(score_);
        java.util.Timer timer = new java.util.Timer();
        fixedTime = timeBetweenLevels;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (timerToStart >= 70){
                    if (points >= 850) {
                        new GameOver(MainClass.this, life, points);
                        frame.dispose();
                    }
                    //System.out.println(listened);
                    fixedTime--;
                    if (fixedTime <= 0) {
                        if (level < 5) level++; // maximum 6 levels
                        fixedTime = timeBetweenLevels;
                    }
                    int[] whichIsFollowing = new int[ghsts.ghostsFollowing.length];
                    int v = 0;
                    //System.out.println();
                    for (int u = 0; u < ghsts.ghostsFollowing.length; u++) {
                        if (ghsts.ghostsFollowing[u] != 0) {
                            //System.out.print("Ghost #" + v + " left-time: " + ghsts.ghostsFollowing[u] + " | ");
                            if (ghsts.ghostsBugFixes[u] == 0) ghsts.ghostsFollowing[u]--;
                            whichIsFollowing[v] = u;
                            v++;
                        } else {
                            ghsts.ghostsRandom[u] = true;
                        }
                    }
                    if (v == 0 && timeToWait > 0) timeToWait--;
                    //System.out.println("Following: " + v + ", Time-Wait: " + timeToWait + " | Level: " + level);
                    if (v <= level && timeToWait <= 0) {
                        for (int h = 0; h < level; h++) {
                            if (level < 6) {
                                int rand = rnd.nextInt(ghsts.ghostsFollowing.length);
                                ghsts.ghostsFollowing[rand] = level * 7;
                                ghsts.ghostsRandom[rand] = false;
                                //System.out.println("Assigned ghost #" + rand + " to: " + level * 4 + "s");
                            } else break;

                        }

                        timeToWait = rnd.nextInt(level * 3);
                    }
                    if (isGoingGhosts) {
                        for (int f = 0; f < numGhosts; f++) {
                            if (ghsts.ghostsBugFixes[f] > 0) {
                                ghsts.ghostsBugFixes[f]--;
                                if (ghsts.ghostsBugFixes[f] == 0 && ghsts.ghostsFollowing[f] > 0) {
                                    ghsts.ghostsRandom[f] = false;
                                    //System.out.println("vyshel iz komy");
                                }
                            }

                            if (!ghsts.ghostsRandom[f]) {
                                //System.out.println("worked: " + ghsts.ghostsPosition[f][0]);
                                ghsts.changeLastPosition(f, ghsts.ghostsPosition[f]);
                                //System.out.print("Ghost #" + f + " positions: ");
                                /*for(int[] d: ghsts.ghostsFixedPosition[f]){
                                    System.out.print(d[0] + ", " + d[1] + " | ");
                                }*/
                                //System.out.println();
                                if (ghsts.ghostsFixedPosition[f][0][0] == ghsts.ghostsFixedPosition[f][2][0] && ghsts.ghostsFixedPosition[f][0][1] == ghsts.ghostsFixedPosition[f][2][1]) {
                                    ghsts.ghostsRandom[f] = true;
                                    ghsts.ghostsBugFixes[f] = rnd.nextInt(2) + 1;
                                    //ghsts.ghostsFollowing[f] = 0;
                                    //System.out.println("zastryalas");
                                    ghsts.ghostsFixedPosition[f] = new int[3][2];
                                }
                            }
                        }
                    }
                } else {
                    if (timerToStart == 0){
                        ghsts.ghostsRandom = new boolean[]{true,true,true,true,true,true,true,true,true,true}; //testing
                        ghsts.ghostsPosition = new int[ghsts.numGhosts][2];
                        for (int o = 0; o < ghsts.numGhosts; o++){
                            if (o % 2 == 0) ghsts.ghostsPosition[o] = new int[]{440,295};
                            else ghsts.ghostsPosition[o] = new int[]{520,295};
                        }
                    }
                    timerToStart++;
                }
            }
        }, 1000, 250);
    }

    public void GameOver(){
        //new GameOver(MainClass.this);
        frame.dispose();
    }
    public static class TestPanel extends JPanel implements KeyListener {

        /* *-------------------- key rememberer ----------------------*/

        public static boolean doublePressed(String pressedNow){
            if (lastPressed.equals(pressedNow)) {
                lastPressed = "";
                return true;
            } else {
                lastPressed = pressedNow;
                return false;
            }
        }
        public static boolean isAnotherAxis(String s, String entered){
                        if (s.equals("r") || s.equals("l")){
                            if (entered.equals("u") || entered.equals("d")) return true;
                        } else if (s.equals("u") || s.equals("d")){
                            if (entered.equals("r") || entered.equals("l")) return true;
                        }
                        return false;
        }
        public static boolean isOpposite(String s, String entered){
            if (s.equals("r")){
                if (entered.equals("l")) return true;
                else return false;
            } else if (s.equals("l")){
                if (entered.equals("r")) return true;
                else return false;
            } else if (s.equals("u")){
                if (entered.equals("d")) return true;
                else return false;
            } else if (s.equals("d")){
                if (entered.equals("u")) return true;
                else return false;
            } else return false;
        }
        protected static void saveClicks(String clicked){
            //System.out.println("clicked: " + clicked);
            /*if (doublePressed(clicked)) {
                System.out.println("Wanted to save: " + clicked);
                listened = "";
            }
            else */if (isAnotherAxis(clicked,side)){
                if (!clicked.equals(listened)){
                    //System.out.println("Saved: " + clicked);
                    listened = clicked;
                }
                //System.out.println("Wanted to save: " + clicked);
            } else if (isOpposite(clicked, side)){
                side = clicked;
            } else listened = "";
            //System.out.println(listened);
            //change(clicked);
        }

        /*---------------------end key rememberer -----------------------*/


        public TestPanel() {
            this.addKeyListener(this);
            this.setFocusable(true);
            this.requestFocusInWindow();

        }

        public void change(String side){
            MainClass.side = side;
            MainClass.isGoing = true;
        }
        @Override
        public void keyPressed(KeyEvent e) {
            int gr = e.getKeyCode();
            if (gr == KeyEvent.VK_LEFT){
                if (limiter.checkWall("l", 0, true, 7)) {
                    change("l");
                    listened = "";
                } else {
                    saveClicks("l");
                }
            }
            if (gr == KeyEvent.VK_RIGHT){
                if (limiter.checkWall("r", 0, true, 7)) {
                    change("r");
                    listened = "";
                } else {
                    saveClicks("r");
                }
            }
            if (gr == KeyEvent.VK_UP){
                if (limiter.checkWall("u", 0, true, 7)) {
                    change("u");
                    listened = "";
                } else {
                    saveClicks("u");
                }
            }
            if (gr == KeyEvent.VK_DOWN){
                if (limiter.checkWall("d", 0, true, 7)) {
                    change("d");
                    listened = "";
                } else {
                    saveClicks("d");
                }
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new MainClass().display());
    }
}