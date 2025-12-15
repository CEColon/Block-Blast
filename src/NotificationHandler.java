import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class NotificationHandler {

    private int x;
    private int y;
    private static ArrayList<Integer> notifQueue = new ArrayList<Integer>();
    private int displayTime;

    public NotificationHandler() {
        this.x = 521;
        this.y = 20;
        this.displayTime = 0;
    }

    public void addNotif(int num) {
        notifQueue.add(num);
    }

    public void paintComponent(Graphics g) {
        try {
            BufferedImage image = null;
            if(notifQueue.size() == 0) {
                image = ImageIO.read(new File("imgs/gamelabelsplash.png"));
            }
            else {
                if(notifQueue.get(0) == 0) {
                    String filename = "imgs/misssplash" + (((displayTime / 10) % 2) + 1) + ".png";
                    image = ImageIO.read(new File(filename));
                }
                if(notifQueue.get(0) == 1) {
                    String filename = "imgs/extraballsplash" + (((displayTime / 10) % 2) + 1) + ".png";
                    image = ImageIO.read(new File(filename));
                }
                if(notifQueue.get(0) == 2) {
                    String filename = "imgs/doublepointssplash" + (((displayTime / 10) % 2) + 1) + ".png";
                    image = ImageIO.read(new File(filename));
                }
                if(notifQueue.get(0) == 3) {
                    String filename = "imgs/100pointssplash" + (((displayTime / 10) % 2) + 1) + ".png";
                    image = ImageIO.read(new File(filename));
                }
                if(notifQueue.get(0) == 4) {
                    String filename = "imgs/1kpointssplash" + (((displayTime / 10) % 2) + 1) + ".png";
                    image = ImageIO.read(new File(filename));
                }
                if(notifQueue.get(0) == 5) {
                    String filename = "imgs/10kpointssplash" + (((displayTime / 10) % 2) + 1) + ".png";
                    image = ImageIO.read(new File(filename));
                }
                displayTime++;
            }
            g.drawImage(image, x, y, null);
            if(displayTime == 100) {
                notifQueue.remove(0);
                displayTime = 0;
            }
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
}