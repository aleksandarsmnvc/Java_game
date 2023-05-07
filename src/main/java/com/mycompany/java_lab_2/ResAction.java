/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_lab_2;

import java.util.ArrayList;

/**
 *
 * @author aleks
 */
public class ResAction {
    ArrayList<Strela> allStrela;
    ArrayList<Ball> allBall;
    int points;
    int kol_vistrelov;
    Boolean game_running;
    String winner="";
    @Override
    public String toString() {
        String s = "ResAction{" + "allStrela="; 
        if(allStrela!=null) {
            for (Strela str : allStrela) {
                s += str.toString();
            }
        }
        if(allBall!=null) {
            s+=" allBall=";
            for(Ball b:allBall) {
                s+=b.toString();
            }
        }
        s+=" points="+points+" kol_vistrelov="+kol_vistrelov+" game_running="+game_running+" winner="+winner;
        s += '}';
        return s;
    }
}
