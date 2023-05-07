/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_lab_2;
import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author aleks
 */
public class clientAtServer implements Runnable {
    Socket cs;
    MainServer ms;
    static ArrayList<clientAtServer> klienti= new ArrayList<>();
    int kol_vistrelov;
    int points;
    int id;
    String name;
    Boolean name_good=false;
    Boolean ready=false;
    Boolean hitting=false;
    InputStream is;
    OutputStream os;
    DataInputStream dis;
    DataOutputStream dos;
    
    Gson gson = new Gson();

    Model m = BModel.build();
    
    public clientAtServer(Socket cs, MainServer ms,int id,String name) {
        this.cs = cs;
        this.ms = ms;
        this.id=id;
        this.name=name;
        kol_vistrelov=0;
        points=0;
        try {
            os = cs.getOutputStream();
            dos = new DataOutputStream(os);
        } catch (IOException ex) {
            Logger.getLogger(clientAtServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        Checking(name);
    }
    private Boolean CheckPlayerName(String name)
    {
        System.out.println("Searching name:"+name);
        for(clientAtServer client:klienti)
        {
            if(client.name.equals(name))return false;
            System.out.println(client.name);
        }
        return true;
    }
    private void Checking(String name)
    {
       
                    
                    if(CheckPlayerName(name))
                    {
                        try {
                            dos.writeUTF("Successfully connected");
                            klienti.add(this);
                        } catch (IOException ex) {
                            Logger.getLogger(clientAtServer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        name_good=true;
                    }
                    else
                    {
                        try {
                            dos.writeUTF("There is user with this name");
                        } catch (IOException ex) {
                            Logger.getLogger(clientAtServer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
    }
    @Override
    public void run() {
        try {
            is = cs.getInputStream();
            dis = new DataInputStream(is);
            System.out.println("Cilent thread started");
            while(true)
            {
                String s = dis.readUTF();
                System.out.println("Msg: " + s);
                
                Msg msg = gson.fromJson(s, Msg.class);
                if(msg.pa==GameAction.CONNECT)
                {
                    Checking(msg.message);
                }
                if(msg.pa ==GameAction.HIT)
                {
                    if(ready)hitting=true;
                }
                
                if(msg.pa == GameAction.PAUSE)
                {
                    ready=false;
                }
                if (msg.pa == GameAction.READY) {
                    ready=true;
                    if(ms.allReady())
                    {
                        synchronized(ms)
                        {
                            ms.notifyAll();
                            System.out.println("Notified!!!");
                        }
                    }
                    System.out.println("Ready changed from"+ name);
                }

                
            }
        } catch (IOException ex) {
            Logger.getLogger(clientAtServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    void sendAllToClient(){
        try {
            ResAction ra = new ResAction();
            ra.allStrela = m.getAllStrela();
            ra.allBall=m.getAllBall();
            ra.points=this.points;
            ra.kol_vistrelov=this.kol_vistrelov;
            ra.game_running=ms.game_running;
            if(!ra.game_running){ra.winner=ms.winner;}
            String s = gson.toJson(ra);
            dos.writeUTF(s);
        } catch (IOException ex) {
            Logger.getLogger(clientAtServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
