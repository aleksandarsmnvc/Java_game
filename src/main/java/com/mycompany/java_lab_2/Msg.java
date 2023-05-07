/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.java_lab_2;

/**
 *
 * @author aleks
 */
public class Msg {
    GameAction pa;
    String message;
    public Msg(GameAction pa)
    {
        this.pa=pa;
        this.message="";
    }
    public Msg(GameAction pa,String message) {
        this.pa = pa;
        this.message=message;
    }
    @Override
    public String toString() {
        return "Msg{" + "pa=" + pa +" message=" + message + '}';
    }
    
    
}
