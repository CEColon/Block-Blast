import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Ball{
    private double x;
    private double y;
    private int radius;
    private double xSpeed;
    private double ySpeed;
    private double speedmultiplier;

    public Ball(int x, int y, int radius, int xSpeed, int ySpeed) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.speedmultiplier = 1.0;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public int getRadius() {
        return radius;
    }
    public double getXSpeed() {
        return xSpeed;
    }
    public double getYSpeed() {
        return ySpeed;
    }
    public void paintComponent(Graphics g) {
        g.setColor(new Color(0, 255, 255));
        g.fillOval((int)x - radius, (int)y - radius, radius * 2, radius * 2);
    }
    public void update() {
        x += xSpeed;
        y += ySpeed;
    }
    public void changeDirection(double d) {
        double rad = d * (Math.PI/180);
        xSpeed += (5*speedmultiplier*Math.sin(rad*(6/5)));
        ySpeed = -1*(5*speedmultiplier*Math.cos(rad*(6/5)));
    }
    public void reverseXSpeed() {
        xSpeed *= -1;
    }
    public void reverseYSpeed() {
        ySpeed *= -1;
    }
    public void increaseSpeedMultiplier() {
        if(speedmultiplier <= 2.0) {
            speedmultiplier += 1.0;
            System.out.println(speedmultiplier);
        }
    }
}