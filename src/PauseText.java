import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class PauseText {

    public PauseText(){}

    public void paintComponent(Graphics g) {
        Font f = new Font("Courier", Font.BOLD, 30);
        FontMetrics fm = g.getFontMetrics(f);
        g.setColor(new Color(255, 255, 255));
        g.setFont(f);

        String scoreBoard = "Game Paused";
        int x = 250 - fm.stringWidth(scoreBoard) / 2;
        int y = 350;
        g.drawString(scoreBoard, x, y);

        String scoreBoard2 = "Hit ESC to Continue";
        int x2 = 250 - fm.stringWidth(scoreBoard) / 2;
        int y2 = 400;
        g.drawString(scoreBoard2, x2, y2);
    }

}