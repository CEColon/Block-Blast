import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class SpecialBlock {
    private int x;
    private int y;
    private int type;
    private int stage;

    public SpecialBlock(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void paintComponent(Graphics g) {
        try {
            BufferedImage image = null;
            switch(type) {
                case 2:
                    image = ImageIO.read(new File("imgs/sblock1.png"));
                    break;
                case 3:
                    image = ImageIO.read(new File("imgs/sblock2.png"));
                    break;
                case 4:
                    image = ImageIO.read(new File("imgs/sblock3.png"));
                    break;
                case 5:
                    image = ImageIO.read(new File("imgs/sblock4.png"));
                    break;
                case 6:
                    image = ImageIO.read(new File("imgs/sblock5.png"));
                    break;
                case 7:
                    image = ImageIO.read(new File("imgs/sblock6.png"));
                    break;
                case 8:
                    image = ImageIO.read(new File("imgs/sblock7.png"));
                    break;
                case 9:
                    image = ImageIO.read(new File("imgs/sblock8.png"));
                    break;
                case 41:
                    image = ImageIO.read(new File("imgs/sblock3_cracked1.png"));
                    break;
                case 42:
                    image = ImageIO.read(new File("imgs/sblock3_cracked2.png"));
                    break;
                case 43:
                    image = ImageIO.read(new File("imgs/sblock3_cracked3.png"));
                    break;

            }
            g.drawImage(image, x+1, y+1, null);
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
}