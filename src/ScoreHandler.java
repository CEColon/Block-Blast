import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class ScoreHandler {
    private int points;
    private boolean booster;
    private int boosterTime;
    public ScoreHandler() {
        points = 0;
        booster = false;
        boosterTime = 0;
    }

    public void increaseBy(int n) {
        if(booster) {
            points += 2 * n;
        }
        else {
            points += n;
        }
    }

    public void paintComponent(Graphics g) {
        Font f = new Font("Courier", Font.BOLD, 30);
        FontMetrics fm = g.getFontMetrics(f);
        g.setColor(new Color(255, 255, 255));
        g.setFont(f);

        String scoreBoard = "Points:";
        int x = 650 - fm.stringWidth(scoreBoard) / 2;
        int y = 350;
        g.drawString(scoreBoard, x, y);

        String scoreBoard2 = "" + points;
        if(booster) {
            scoreBoard2 = scoreBoard2 + " x2";
            g.setColor(new Color(255, 255, 0));
            boosterTime++;
        }
        int x2 = 650 - fm.stringWidth(scoreBoard2) / 2;
        int y2 = 390;
        g.drawString(scoreBoard2, x2, y2);

        if(booster) {
            f = new Font("Courier", Font.BOLD, 18);
            fm = g.getFontMetrics(f);
            g.setFont(f);

            String scoreBoard3 = "Double Points: " + (3000 - boosterTime);
            int x3 = 650 - fm.stringWidth(scoreBoard3) / 2;
            int y3 = 310;
            g.drawString(scoreBoard3, x3, y3);
        }

        if(boosterTime == 3000) {
            booster = false;
            boosterTime = 0;
        }
    }

    public void enableBooster() {
        booster = true;
        boosterTime = 0;
    }
}