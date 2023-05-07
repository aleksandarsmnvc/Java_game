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
public class Model {
    ArrayList<Strela> allStrela = new ArrayList<>();
    ArrayList<Ball> allBall=new ArrayList();
    ArrayList<IObserver> allObservers = new ArrayList<>();
    
    void ref()
    {
        for (IObserver o : allObservers) {
            o.ref();
        }
    }
    
    
    public void setAllStrela(ArrayList<Strela> allStrela){
        this.allStrela = allStrela;
        ref();
    }
    public void setAllBall(ArrayList<Ball> allBall)
    {
        this.allBall=allBall;
        ref();
    }
    
    public  void addObserver(IObserver o)
    {
        allObservers.add(o);
    }
    
    public void addBall(Ball b)
    {
        allBall.add(b);
        ref();
    }
    public void addStrela(Strela p)
    {
        allStrela.add(p);
        ref();
    }
    
    /*public void move(MyPoint p)
    {
        allPoints.get(allPoints.size()-1).move(p.getX(), p.getY());
        ref();
    }*/

    public ArrayList<Strela> getAllStrela() {
        return allStrela;
    }
    public ArrayList<Ball> getAllBall()
    {
        return allBall;
    }
}
