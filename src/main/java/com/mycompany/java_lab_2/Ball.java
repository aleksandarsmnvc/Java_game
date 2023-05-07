/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_lab_2;

/**
 *
 * @author aleks
 */
public class Ball {
    int x,y;
    int speed;
    int r;

    public Ball(int x, int y, int speed, int r) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.r = r;
    }
    public void moveBall(int x,int y){
        this.x=x;
        this.y=y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
