/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.java_lab_2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

/**
 *
 * @author aleks
 */
public class VModel extends javax.swing.JPanel implements IObserver {

    /**
     * Creates new form VModel
     */
     Model m = BModel.build();
    GameAction action;

    public void setAction(GameAction action) {
        this.action = action;
    }
    
    ClientFrame cf;

    public void setCf(ClientFrame cf) {
        this.cf = cf;
    }
    
    @Override
    public void ref()
    {
        this.repaint();
    }
    private void DrawBall(Ball b1,Graphics2D g2d){
        g2d.setColor(Color.red);
        g2d.fillOval(b1.x, b1.y, 2*b1.r, 2*b1.r);
    }
    private void DrawStrela(Strela s1,Graphics2D g2d){
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(s1.x,s1.y,s1.x+s1.length-6,s1.y);
        int x_cord[]=new int[3];
        int y_cord[]=new int[3];
        x_cord[0]=s1.x+s1.length-6;
        x_cord[1]=x_cord[0];
        x_cord[2]=s1.x+s1.length;
        y_cord[0]=s1.y-3;
        y_cord[1]=s1.y+3;
        y_cord[2]=s1.y;
        Polygon p=new Polygon(x_cord,y_cord,3);
        g2d.fillPolygon(p);
    }
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g); 
        Graphics2D g2d = (Graphics2D)g;
        for(Ball b:m.getAllBall())
        {
            DrawBall(b,g2d);
        }
        for(Strela strela:m.getAllStrela())
        {
            DrawStrela(strela,g2d);
        }
    }
    
    /**
     * Creates new form VModel
     */
    public VModel() {
        initComponents();
        m.addObserver(this);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}