import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Button {
    private int x;
    private int y;
    private int width;
    private int height;
    private Color c;
    private String s;

    public Button(int x, int y, int width, int height, Color c, String s){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.c = c;
        this.s = s;
    }

    public void paintComponent(Graphics g) {
        g.setColor(c);
        g.fillRoundRect(x, y, width, height, 30, 30);

        Font f = new Font("Courier", Font.BOLD, 20);
        FontMetrics fm = g.getFontMetrics(f);
        g.setColor(new Color(0, 0, 0));
        g.setFont(f);

        int xPos = (width / 2) - (fm.stringWidth(s) / 2) + x;
        int yPos = (height / 2) - (fm.getHeight() / 2) + y + 20;
        g.drawString(s, xPos, yPos);
    }

    public boolean withinDimension(int xPos, int yPos) {
        return(xPos > x && xPos < x + width && yPos > y && yPos < y+height);
    }

}