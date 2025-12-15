import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class RightPanel {
    private int x;
    private int y;

    public RightPanel() {
        this.x = 501;
        this.y = 0;
    }

    public void paintComponent(Graphics g) {
        try {
            BufferedImage image = null;
            image = ImageIO.read(new File("imgs/RightBoard.png"));
            g.drawImage(image, x, y, null);

            image = ImageIO.read(new File("imgs/gamelabel.png"));
            g.drawImage(image, x+49, y+550, null);
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }
}