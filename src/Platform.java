import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Platform{
    private int x;
    private int y;
    private boolean coolDown;

    public Platform(int x, int y) {
        this.x = x;
        this.y = y;
        this.coolDown = false;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void update(int change) {
        x += change;
    }
    public void paintComponent(Graphics g) {
        g.setColor(new Color(255, 255, 0));
        g.fillRect(x - 50, y - 5, 100, 10);
    }
    public void setCooldown(boolean b) {
        coolDown = b;
    }
    public boolean onCooldown() {
        return coolDown;
    }
}