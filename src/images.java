import java.util.Timer;
import java.util.TimerTask;

public class images {
    public int c = 0;
    public boolean flag = false;

    public void moving(){
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (MainClass.isGoing){
                        if (c > 1) c = 0;
                        else c++;
                    } else c = 1;
                    switch (c) {
                        case 0:
                            switch (MainClass.side) {
                                case "r":
                                    MainClass.label.setIcon(MainClass.man1);
                                    break;
                                case "l":
                                    MainClass.label.setIcon(MainClass.man3);
                                    break;
                                case "u":
                                    MainClass.label.setIcon(MainClass.man5);
                                    break;
                                case "d":
                                    MainClass.label.setIcon(MainClass.man7);
                                    break;
                            }
                            break;
                        case 1: default:
                            switch (MainClass.side) {
                                case "r":
                                    MainClass.label.setIcon(MainClass.man2);
                                    break;
                                case "l":
                                    MainClass.label.setIcon(MainClass.man4);
                                    break;
                                case "u":
                                    MainClass.label.setIcon(MainClass.man6);
                                    break;
                                case "d":
                                    MainClass.label.setIcon(MainClass.man8);
                                    break;
                            }
                            break;
                    }
                }
            }, 1000, 250);

    }

    public void update(){
        moving();
    }
}
