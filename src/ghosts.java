import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ghosts extends limiter {
    public static int[] ghostsStatus = {1,1,1,1,1,1,1,1,1,1};
    public static String[] ghostsSide;
    public static int numGhosts;
    public static boolean[] ghostsRandom;
    public static int[][] ghostsPosition;
    public static int[][][] ghostsFixedPosition;
    public static int[] ghostsFollowing;
    public static int[] ghostsBugFixes;
    public static int iconCode[] = new int[10];
    public static Random rnd = new Random();
    public static int x,y,x2,y2;

    public ghosts(int x, int y, int x2, int y2, int numGhosts){
        this.x = x;
        this.x2 = x2;
        this.y = y;
        this.y2 = y2;


        ghostsPosition = new int[numGhosts][2];
        for (int o = 0; o < numGhosts; o++){
            if (o % 2 == 0) ghostsPosition[o] = new int[]{x,y};
            else ghostsPosition[o] = new int[]{x2,y2};
        }
        //ghostsPosition = new int[][]{{x,y},{x,y},{x,y},{x,y},{x,y},{x,y},{x,y},{x,y},{x,y},{x,y}};
        ghostsFixedPosition = new int[numGhosts][3][2];
        ghostsBugFixes = new int[numGhosts];
        ghostsRandom = new boolean[]{true,true,true,true,true,true,true,true,true,true}; //testing
        MainClass.isGoing = false;
        ghostsFollowing = new int[numGhosts];
        for (int u = 0; u < ghostsFollowing.length; u++){
            ghostsFollowing[u] = 0;
        }
        ghostsSide = new String[]{getRandomSide(),getRandomSide(),getRandomSide(),getRandomSide(),getRandomSide(),getRandomSide(),getRandomSide(),getRandomSide(),getRandomSide(),getRandomSide()};
        this.numGhosts = numGhosts;
        if (numGhosts <= 10){
            for (int i = 0; i < numGhosts; i++){
                int rand = rnd.nextInt(4);
                MainClass.lg[i].setIcon(MainClass.ghosts[rand][getIconCode(ghostsSide[i])]);
                iconCode[i] = rand;
                //MainClass.lg[i].setBounds(x,y + MainClass.forInfoPanel,33,50);
                MainClass.panel.add(MainClass.lg[i]);
            }
        }
        //MainClass.frame.getContentPane().add(MainClass.panel);
        go();
    }



    public static int getIconCode(String side){
        switch (side) {
            case "r":
                return 1;
            case "l":
                return 0;
            case "u":
                return 2;
            case "d":
                return 3;
            default:
                return 0;
        }
    }

    public static String getRandomSide() {
        String[] chars = {"r", "l", "u", "d"};
        return chars[rnd.nextInt(4)];
    }
    public static void changeLastPosition(int ghostNumber, int[] position) {
        if (ghostsFixedPosition[ghostNumber][0] != null){
            int[][] lastPosition = ghostsFixedPosition[ghostNumber].clone();
            ghostsFixedPosition[ghostNumber][0] = position;
            for (int r = 1; r <= 2; r++){
                ghostsFixedPosition[ghostNumber][r] = lastPosition[r - 1];
            }
        } else ghostsFixedPosition[ghostNumber][0] = position;
    }
    public static boolean permissionToGo(String side, int ghostNumber){
        if (ghostsRandom[ghostNumber]){

            if (checker(side,ghostNumber,false)) {
                return true;
            } else return false;
        } else {
            String[] mansDirection = MansDirectionXY(ghostNumber);
            String sideToGo;
            if (side.equals(mansDirection[1]) ) {
                sideToGo = mansDirection[0];
            }else {
                sideToGo = mansDirection[1];
            }
            //System.out.println("Wants to go: " + sideToGo);
            if (checker(side,ghostNumber,false)){
                if (checkWallWithPosition(sideToGo, ghostsPosition[ghostNumber], 7)){
                    ghostsSide[ghostNumber] = sideToGo;
                    return true;
                } else {
                    return true;
                }
            } else return false;
        }
    }
    protected static String[] giveAnotherAxis(String side){
        if (side.equals("r") || side.equals("l")) return new String[]{"u", "d"};
        else return new String[]{"r", "l"};
    }

    protected static boolean isAliveMan(int numGhost){
        int[][] realPosition = getRealPosition(ghostsPosition[numGhost][0],ghostsPosition[numGhost][1]);
        int[][] MansRealPosition = getRealPosition(MainClass.x, MainClass.y);
        if ((realPosition[1][0] <= MansRealPosition[1][2]) && (realPosition[1][4] >= MansRealPosition[1][3]) &&  (MansRealPosition[0][1] >= realPosition[0][0]) && (MansRealPosition[0][3] <= realPosition[0][4])){
            return false;
        } else return true;
    }
    public void go() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (MainClass.isGoingGhosts) {
                    for (int j = 0; j < numGhosts; j++) {
                        if (!isAliveMan(j)){
                            MainClass.life--;
                            ghostsPosition = new int[numGhosts][2];
                            for (int o = 0; o < numGhosts; o++){
                                if (o % 2 == 0) ghostsPosition[o] = new int[]{x,y};
                                else ghostsPosition[o] = new int[]{x2,y2};
                            }
                            int pos = rnd.nextInt(3);
                            if (pos == 0){
                                MainClass.x = 210;
                                MainClass.y = 295;
                                MainClass.label.setBounds(210,295 + MainClass.forInfoPanel,33,50);
                            } else if (pos == 1) {
                                MainClass.x = 760;
                                MainClass.y = 295;
                                MainClass.label.setBounds(760,295 + MainClass.forInfoPanel,33,50);
                            } else {
                                MainClass.x = 480;
                                MainClass.y = 545;
                                MainClass.label.setBounds(480,545 + MainClass.forInfoPanel,33,50);
                            }
                            MainClass.updateScreen();
                MainClass.timerToStart = 0;
                if ( MainClass.life == 0) {
                    new GameOver(ghosts.this, MainClass.life, MainClass.points);
                    MainClass.frame.dispose();
                }
                break;
            }
            if (permissionToGo(ghostsSide[j],j)){
                switch (ghostsSide[j]) {
                    case "r":
                        ghostsPosition[j][0] += 1;
                        MainClass.lg[j].setBounds(ghostsPosition[j][0],ghostsPosition[j][1] + MainClass.forInfoPanel,33,50);
                        break;
                    case "l":
                        ghostsPosition[j][0] -= 1;
                        MainClass.lg[j].setBounds(ghostsPosition[j][0],ghostsPosition[j][1] + MainClass.forInfoPanel,33,50);
                        break;
                    case "u":
                        ghostsPosition[j][1] -= 1;
                        MainClass.lg[j].setBounds(ghostsPosition[j][0],ghostsPosition[j][1] + MainClass.forInfoPanel,33,50);
                        break;
                    case "d":
                        ghostsPosition[j][1] += 1;
                        MainClass.lg[j].setBounds(ghostsPosition[j][0],ghostsPosition[j][1] + MainClass.forInfoPanel,33,50);
                        break;
                }
            } else {
                if (ghostsRandom[j]){
                    ghostsSide[j] = getRandomSide();
                } else {
                    String[] mansDirection = MansDirectionXY(j);
                    if (ghostsSide[j].equals(mansDirection[1]) ) {
                        ghostsSide[j] = mansDirection[0];
                    }else {
                        ghostsSide[j] = mansDirection[1];
                    }
                }
                MainClass.lg[j].setIcon(MainClass.ghosts[iconCode[j]][getIconCode(ghostsSide[j])]);
            }
        }
    }
}
        }, 1000, 10);
                }

public static String[] MansDirectionXY(int numGhost){
        String[] position = new String[2];
        if (ghostsPosition[numGhost][0] >= MainClass.x){
        if (ghostsPosition[numGhost][1] + 2 >= MainClass.y && ghostsPosition[numGhost][1] - 2 <=  MainClass.y){
        position[0] = "l";
        position[1] = "l";
        return position;
        } else position[0] = "l";
        } else {
        if (ghostsPosition[numGhost][1] + 2 >= MainClass.y && ghostsPosition[numGhost][1] - 2 <=  MainClass.y){
        position[0] = "r";
        position[1] = "r";
                return position;
            } else position[0] = "r";
        }
        if (ghostsPosition[numGhost][1] >= MainClass.y){
            if (ghostsPosition[numGhost][0] + 2 >= MainClass.x && ghostsPosition[numGhost][0] - 2 <=  MainClass.x){
                position[0] = "u";
                position[1] = "u";
                return position;
            } else position[1] = "u";
        } else {
            if (ghostsPosition[numGhost][0] + 2 >= MainClass.x && ghostsPosition[numGhost][0] - 2 <=  MainClass.x){
                position[0] = "d";
                position[1] = "d";
                return position;
            } else position[1] = "d";
        }
        //System.out.println("Given: " + position[0] + ", " +  position[1]);
        return position;
    }

}
