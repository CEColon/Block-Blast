import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class BreakoutMain {

    public static String[] panels = {"title", "game"};
    public static String current = "";
    public static boolean active = true;
    public static JPanel parent = new JPanel();
    public static TitleScreen title = new TitleScreen();
    public static BreakoutPanel game = new BreakoutPanel();

    public static void main(String[] args) {
        initialize();
    }

    private static void initialize() {
        JFrame breakoutFrame = new JFrame("Block Blast");

        parent.add(game);

        breakoutFrame.add(parent,BorderLayout.CENTER);
        breakoutFrame.setSize(800, 700);
        breakoutFrame.pack();
        breakoutFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        breakoutFrame.setVisible(true);

        current = "game";

        actionChecker(breakoutFrame);
    }

    private static void actionChecker(JFrame frm) {
        while(active) {
            if(current.equals(panels[0])) {
                if(title.playButtonHit()) {
                    changePanel(title, game, frm);
                    current = "game";
                    title.off();
                }
            }
            else {
                //nothing for now
            }
        }
    }

    private static void changePanel(JPanel current, JPanel panel, JFrame frm) {
        parent.removeAll();
        parent.add(panel,BorderLayout.CENTER);
        parent.revalidate();
        parent.repaint();
        frm.pack();
    }
}