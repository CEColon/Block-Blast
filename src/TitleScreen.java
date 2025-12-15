import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class TitleScreen extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    private static Button playButton = new Button(300, 370, 200, 60, new Color(200, 0, 0), "Play");
    private static boolean playButtonHit = false;
    private static boolean enabled = true;

    public TitleScreen() {
        setPreferredSize(new Dimension(800, 700));
        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);
    }

    public boolean playButtonHit() {
        return playButtonHit;
    }

    public void off() {
        playButtonHit = false;
        enabled = false;
    }

    public void on() {
        enabled = true;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            BufferedImage image = null;
            image = ImageIO.read(new File("imgs/Titlescreen.png"));
            g.drawImage(image, 0, 0, null);
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }
        playButton.paintComponent(g);
        repaint();
    }

    public void mouseClicked(MouseEvent e) {
        System.out.println(playButtonHit);
        if(playButton.withinDimension(e.getX(), e.getY())) {
            if(enabled){
                System.out.println("button hit");
                playButtonHit = true;
            }
        }
        System.out.println(playButtonHit);
    }

    public void mouseExited(MouseEvent e) {
        //Unused
    }

    public void mouseEntered(MouseEvent e) {
        //Unused
    }

    public void mouseReleased(MouseEvent e) {
        //Unused
    }

    public void mousePressed(MouseEvent e) {
        //Unused
    }

    public void mouseMoved(MouseEvent e) {
        //Unused
    }

    public void mouseDragged(MouseEvent e) {
        //Unused
    }

    public void keyPressed(KeyEvent e) {
        //Unused
    }
    public void keyReleased(KeyEvent e) {
        //Unused
    }
    public void keyTyped(KeyEvent e) {
        //Unused
    }

}