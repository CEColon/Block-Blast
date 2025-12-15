import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Block {
    private int x;
    private int y;

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void paintComponent(Graphics g) {
        g.setColor(new Color(127, 127, 127));
        g.fillRect(x+1, y+1, 48, 18);
    }
}