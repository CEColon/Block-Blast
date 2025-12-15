import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.util.*;
import java.io.*;
import javax.swing.Timer;
import javax.sound.sampled.*;
import java.awt.event.MouseEvent;

public class BreakoutPanel extends JPanel implements KeyListener, MouseMotionListener{
    private Ball b;
    private Platform p;
    private Timer t;
    private int tickTimes;
    private int coolDownTicks;
    private int platformHits;
    private ArrayList<Ball> queue;
    private ArrayList<Ball> balls;
    private ArrayList<Ball> deletion;
    private static SoundHandler sH = new SoundHandler();
    private static ScoreHandler score = new ScoreHandler();
    private static RightPanel rp = new RightPanel();
    private static NotificationHandler nh = new NotificationHandler();
    private static PauseText pt = new PauseText();
    private static int[][] board;
    private boolean paused;

    public BreakoutPanel() {
        try{
            board = createBoard();
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }
        setPreferredSize(new Dimension(800, 700));
        setBackground(new Color(0, 0, 0));
        this.b = new Ball(250, 600, 5, 0, 5);
        this.p = new Platform(250, 650);
        this.tickTimes = 0;
        this.coolDownTicks = 0;
        this.platformHits = -1;
        this.balls = new ArrayList<Ball>();
        this.queue = new ArrayList<Ball>();
        this.deletion = new ArrayList<Ball>();
        balls.add(b);
        blockSetup();
        setFocusable(true);
        addKeyListener(this);
        addMouseMotionListener(this);
        this.paused = false;
        this.t = new Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for( Ball ba : balls ) {
                    ba.update();
                }
                if(p.onCooldown()) {
                    coolDownTicks--;
                    if(coolDownTicks == 0) {
                        p.setCooldown(false);
                    }
                }
                for( Ball ba : balls )  {
                    if(checkPlatformCollision(ba, p)) {
                        if(!p.onCooldown()) {
                            playSound(1);
                            ba.changeDirection(ba.getX() - p.getX());
                            p.setCooldown(true);
                            coolDownTicks = 2;
                            //ba.increaseSpeedMultiplier();
                            platformHits++;
                            if(platformHits == 4) {
                                int[] empty = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                                for(int r = 34; r > 0; r--) {
                                    board[r] = board[r-1];
                                    board[r-1] = empty;
                                }
                                int[] full = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
                                for(int c = 0; c < full.length; c++) {
                                    if(Math.random()*10 <= 1) {
                                        full[c] = 6;
                                    }
                                    if(Math.random()*40 <= 1) {
                                        full[c] = 4;
                                    }
                                }
                                board[0] = full;
                                //ba.increaseSpeedMultiplier();
                                platformHits = 0;
                            }
                            if(Math.random()*10 <= 1) {
                                specialSetup();
                            }
                        }
                    }
                    if(checkLeftEdgesCollision(ba) && ba.getXSpeed() < 0) {
                        playSound(1);
                        ba.reverseXSpeed();
                    }
                    if(checkRightEdgesCollision(ba) && ba.getXSpeed() > 0) {
                        playSound(1);
                        ba.reverseXSpeed();
                    }
                    if(checkTopCollision(ba) && ba.getYSpeed() < 0) {
                        playSound(1);
                        ba.reverseYSpeed();
                    }
                    for(int i = 0; i < board.length; i++) {
                        for(int j = 0; j < board[i].length; j++) {
                            if(board[i][j] > 0) {
                                if(checkGeneralBlockCollision(ba, j, i)) {
                                    if(checkBlockCollisionX(ba, j) && ba.getXSpeed() > 0 && board[i][j] != 6)
                                        ba.reverseXSpeed();
                                    if(checkBlockCollisionY(ba, i) && ba.getYSpeed() > 0 && board[i][j] != 6)
                                        ba.reverseYSpeed();
                                    if(checkBlockCollisionX2(ba, j) && ba.getXSpeed() < 0 && board[i][j] != 6)
                                        ba.reverseXSpeed();
                                    if(checkBlockCollisionY2(ba, i) && ba.getYSpeed() < 0 && board[i][j] != 6)
                                        ba.reverseYSpeed();
                                    playSound(3);
                                    if(board[i][j] == 2) {
                                        Ball b = new Ball(j*50+25, i*20+10, 5, -3, -3);
                                        queue.add(b);
                                        playSound(2);
                                        score.increaseBy(50);
                                        nh.addNotif(1);
                                    }
                                    if(board[i][j] == 3) {
                                        bombDestruction(i, j);
                                    }
                                    if(board[i][j] == 1 || board[i][j] == 4 || board[i][j] == 41 || board[i][j] == 42 || board[i][j] == 43) {
                                        score.increaseBy(10);
                                    }
                                    if(board[i][j] == 7) {
                                        score.increaseBy(100);
                                        nh.addNotif(3);
                                    }
                                    if(board[i][j] == 8) {
                                        score.increaseBy(1000);
                                        nh.addNotif(4);
                                    }
                                    if(board[i][j] == 9) {
                                        score.increaseBy(10000);
                                        nh.addNotif(5);
                                    }
                                    if(board[i][j] == 6) {
                                        playSound(7);
                                        score.increaseBy(5);
                                    }
                                    if(board[i][j] == 5) {
                                        score.increaseBy(20);
                                        playSound(6);
                                        nh.addNotif(2);
                                        score.enableBooster();
                                    }
                                    if(board[i][j] != 4 && board[i][j] != 41 && board[i][j] != 42) {
                                        board[i][j] = 0;
                                    }
                                    if(board[i][j] == 42) {
                                        board[i][j] = 43;
                                    }
                                    if(board[i][j] == 41) {
                                        board[i][j] = 42;
                                    }
                                    if(board[i][j] == 4) {
                                        board[i][j] = 41;
                                    }
                                }
                            }
                        }
                    }
                }
                repaint();
                for( Ball ba : queue ) {
                    balls.add(ba);
                }
                for( Ball ba : balls ) {
                    if(ba.getY() + ba.getRadius() > 700) {
                        deletion.add(ba);
                    }
                }
                for( Ball ba : deletion ) {
                    balls.remove(ba);
                    nh.addNotif(0);
                    playSound(4);
                }
                deletion.clear();
                queue.clear();
                tickTimes++;
            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        p.paintComponent(g);
        for( Ball ba : balls ) {
            ba.paintComponent(g);
        }
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                SpecialBlock sbl;
                switch(board[i][j]) {
                    case 1:
                        Block bl = new Block(j*50, i*20);
                        bl.paintComponent(g);
                        break;
                    case 2:
                        sbl = new SpecialBlock(j*50, i*20, 2);
                        sbl.paintComponent(g);
                        break;
                    case 3:
                        sbl = new SpecialBlock(j*50, i*20, 3);
                        sbl.paintComponent(g);
                        break;
                    case 4:
                        sbl = new SpecialBlock(j*50, i*20, 4);
                        sbl.paintComponent(g);
                        break;
                    case 5:
                        sbl = new SpecialBlock(j*50, i*20, 5);
                        sbl.paintComponent(g);
                        break;
                    case 6:
                        sbl = new SpecialBlock(j*50, i*20, 6);
                        sbl.paintComponent(g);
                        break;
                    case 7:
                        sbl = new SpecialBlock(j*50, i*20, 7);
                        sbl.paintComponent(g);
                        break;
                    case 8:
                        sbl = new SpecialBlock(j*50, i*20, 8);
                        sbl.paintComponent(g);
                        break;
                    case 9:
                        sbl = new SpecialBlock(j*50, i*20, 9);
                        sbl.paintComponent(g);
                        break;
                    case 41:
                        sbl = new SpecialBlock(j*50, i*20, 41);
                        sbl.paintComponent(g);
                        break;
                    case 42:
                        sbl = new SpecialBlock(j*50, i*20, 42);
                        sbl.paintComponent(g);
                        break;
                    case 43:
                        sbl = new SpecialBlock(j*50, i*20, 43);
                        sbl.paintComponent(g);
                        break;

                }
            }
        }
        rp.paintComponent(g);
        score.paintComponent(g);
        nh.paintComponent(g);
        if(paused) {
            pt.paintComponent(g);
        }
        this.repaint();
    }

    public void specialSetup() {
        boolean blockFound = false;
        while(!blockFound) {
            int i = (int)(Math.random()*board.length);
            int j = (int)(Math.random()*board[0].length);
            if(board[i][j] == 1) {
                if(Math.random()*100 <= 1) {
                    board[i][j] = 9;
                }
                else if(Math.random()*10 <= 1) {
                    board[i][j] = 8;
                }
                else if(Math.random()*5 <= 1) {
                    board[i][j] = 3;
                }
                else if(Math.random()*3 <= 1){
                    board[i][j] = 5;
                }
                else if(Math.random()*3 <= 1) {
                    board[i][j] = 2;
                }
                else {
                    board[i][j] = 7;
                }
                blockFound = true;
            }
        }
    }

    public void blockSetup() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(board[i][j] == 1) {
                    if(Math.random()*30 <= 1) {
                        board[i][j] = 6;
                    }
                    if(Math.random()*40 <= 1) {
                        board[i][j] = 4;
                    }
                }
            }
        }
    }

    public void bombDestruction(int i, int j) {
        board[i][j] = 0;
        score.increaseBy(20);
        for(int k = i-1; k <= i+1; k++) {
            for(int l = j-1; l <= j+1; l++) {
                if(k == -1 || k == 35 || l == -1 || l == 10)
                    continue;
                if(board[k][l] == 2) {
                    Ball b = new Ball(j*50+25, i*20+10, 5, -3, -3);
                    queue.add(b);
                    playSound(2);
                    score.increaseBy(50);
                    nh.addNotif(1);
                }
                if(board[k][l] == 3) {
                    bombDestruction(k, l);
                }
                if(board[k][l] == 1 || board[k][l] == 4 || board[k][l] == 41 || board[k][l] == 42 || board[k][l] == 43) {
                    score.increaseBy(10);
                }
                if(board[i][j] == 7) {
                    score.increaseBy(100);
                    nh.addNotif(3);
                }
                if(board[i][j] == 8) {
                    score.increaseBy(1000);
                    nh.addNotif(4);
                }
                if(board[i][j] == 9) {
                    score.increaseBy(10000);
                    nh.addNotif(5);
                }
                if(board[k][l] == 6) {
                    score.increaseBy(5);
                }
                if(board[i][j] == 5) {
                    score.increaseBy(20);
                    playSound(6);
                    nh.addNotif(2);
                    score.enableBooster();
                }
                if(board[k][l] != 4 && board[k][l] != 41 && board[k][l] != 42) {
                    board[k][l] = 0;
                }
                if(board[k][l] == 42) {
                    board[k][l] = 43;
                }
                if(board[k][l] == 41) {
                    board[k][l] = 42;
                }
                if(board[k][l] == 4) {
                    board[k][l] = 41;
                }
                playSound(5);
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            if(tickTimes == 0)
                t.start();
        }
        else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if(paused) {
                t.restart();
            }
            else {
                paused = true;
                t.stop();
            }
        }
    }
    public void keyReleased(KeyEvent e) {
        //Unused
    }
    public void keyTyped(KeyEvent e) {
        //Unused
    }

    public boolean checkPlatformCollision(Ball b, Platform p) {
        return(b.getX() + b.getRadius() >= p.getX() - 50 && b.getX() - b.getRadius() <= p.getX() + 50 && b.getY() + b.getRadius() >= p.getY() - 5 && b.getY() + b.getRadius() <= p.getY() + 5);
    }
    public boolean checkLeftEdgesCollision(Ball b) {
        return(b.getX() - b.getRadius() <= 0);
    }
    public boolean checkRightEdgesCollision(Ball b) {
        return(b.getX() + b.getRadius() >= 500);
    }
    public boolean checkTopCollision(Ball b) {
        return(b.getY() - b.getRadius() <= 0);
    }
    public boolean checkBlockCollisionX(Ball b, int x) {
        return(b.getX() + b.getRadius() >= x*50+1 && b.getX() + b.getRadius() <= x*50+7);
    }
    public boolean checkBlockCollisionY(Ball b, int y) {
        return(b.getY() + b.getRadius() >= y*20+1 && b.getY() + b.getRadius() <= y*20+7);
    }
    public boolean checkBlockCollisionX2(Ball b, int x) {
        return(b.getX() - b.getRadius() >= x*50+43 && b.getX() - b.getRadius() <= x*50+49);
    }
    public boolean checkBlockCollisionY2(Ball b, int y) {
        return(b.getY() - b.getRadius() >= y*20+13 && b.getY() - b.getRadius() <= y*20+19);
    }
    public boolean checkGeneralBlockCollision(Ball b, int x, int y) {
        return(b.getX() + b.getRadius() >= x*50+1 && b.getX() - b.getRadius() <= x*50+49 && b.getY() + b.getRadius() >= y*20+1 && b.getY() - b.getRadius() <= y*20+19);
    }

    public void playSound(int type) {
        switch(type) {
            case 1:
                sH.play("sfx/platform_hit.wav");
                break;
            case 2:
                sH.play("sfx/sblock1_hit.wav");
                break;
            case 3:
                sH.play("sfx/block_hit.wav");
                break;
            case 4:
                sH.play("sfx/ball_loss.wav");
                break;
            case 5:
                sH.play("sfx/sblock2_hit.wav");
                break;
            case 6:
                sH.play("sfx/sblock4_hit.wav");
                break;
            case 7:
                sH.play("sfx/sblock5_hit.wav");
                break;
        }
    }

    public int[][] createBoard() throws FileNotFoundException {
        int[][] newBoard = new int[35][10];
        int map = (int)(4);/*Math.random() * 3 + 1*/
        String filename = "maps/map" + map + ".txt";
        File mapInput = new File(filename);
        Scanner f = new Scanner(mapInput);
        int index = 0;
        while(f.hasNextLine()) {
            String row = f.nextLine();
            for(int i = 0; i < 10; i++) {
                if(row.charAt(i) == '1')
                    newBoard[index][i] = 1;
            }
            index++;
        }
        return newBoard;
    }

    public void mouseMoved(MouseEvent e) {
        if(e.getX() <= 500)
            p.update(e.getX() - p.getX());
    }
    public void mouseDragged(MouseEvent e) {
        //Unused
    }

}