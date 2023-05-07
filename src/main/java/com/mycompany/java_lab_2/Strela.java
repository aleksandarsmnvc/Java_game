/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_lab_2;

/**
 *
 * @author aleks
 */
public class Strela {
    int x,y;
    int speed,length;

    public Strela(int x, int y, int speed,int length) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.length=length;
    }
    public void moveStrela(int x,int y){
        this.x=x;
        this.y=y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public int getLength() {
        return length;
    }
}
