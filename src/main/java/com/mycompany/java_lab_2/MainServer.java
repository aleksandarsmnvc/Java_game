/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_lab_2;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import com.google.gson.Gson;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author aleks
 */
public class MainServer {
    Model m = BModel.build();
    Boolean game_running=true;
    int port = 3124;
    int n=2;
    String winner="";
    final static int height=300;
    final static int width=400;
    final int start_x=50;
    final int start_y=(height-40)/(n-1);
    InetAddress ip = null;
    
    Gson gson=new Gson();
    ExecutorService service = Executors.newCachedThreadPool();
    
    ArrayList<clientAtServer> allClients = new ArrayList<>();
    
    
    void bcastAll(){
        for (clientAtServer allClient : allClients) {
           allClient.sendAllToClient();
        }
    }
    public Boolean allReady()
    {
        System.out.println("Checking");
        for(clientAtServer client:allClients)
        {
            if(!client.ready)return false;
        }
        return true;
    }
    private Boolean CheckAll(Socket cs)
    {
        for(clientAtServer client:allClients)
        {
            if(client.cs==cs)return false;
        }
        return true;
    }
    private void CreateElements()
    {
        m.addBall(new Ball(2*width/3,height/2,3,15));
        m.addBall(new Ball(10*width/12,height/2 ,6,7));
        for(int i=0;i<n;i++)
        {
            m.addStrela(new Strela(start_x,20+i*start_y,10,20));
        }
    }
    private void CheckForEnd()
    {
        for(clientAtServer client:allClients)
        {
            if(client.points>=6)
            {
                game_running=false;
                winner=client.name;
                break;
            }
        }
    }
    private Boolean StrelaHitBall(Strela s1,Ball b1){
        if(s1.y>=b1.y&&s1.y<=b1.y+2*b1.r&&s1.x+s1.length>=b1.x&&s1.x+s1.length<=b1.x+b1.r)return true;
        return false;
    }
    private Boolean StrelaHitWall(Strela s1){
        if(s1.x+s1.length>=width)return true;
        return false;
    }
    private void ResetGameState()
    {
        game_running=true;
        for(clientAtServer client:allClients)
        {
            client.hitting=false;
            client.ready=false;
            client.points=0;
            client.kol_vistrelov=0;
        }
        for(int i=0;i<n;i++)
        {
            Strela s=m.allStrela.get(i);
            s.moveStrela(start_x,20+i*start_y);
        }
        Ball small_b=m.allBall.get(1);
        Ball big_b=m.allBall.get(0);
        small_b.moveBall(10*width/12,height/2);
        big_b.moveBall(2*width/3,height/2);
    }
    private void next()
    {
        Strela strela;
        Ball big_ball=m.allBall.get(0);
        Ball small_ball=m.allBall.get(1);
        for(int i=0;i<n;i++)
        {
            clientAtServer client=allClients.get(i);
            strela=m.allStrela.get(i);
            if(StrelaHitBall(strela,big_ball))
        {
            strela.moveStrela(start_x,20+i*start_y);
            big_ball.moveBall(2*width/3,height/2);
            client.hitting=false;
            client.points+=2;
            client.kol_vistrelov++;
        }
        else if(StrelaHitBall(strela,small_ball))
        {
            strela.moveStrela(start_x,20+i*start_y);
            small_ball.moveBall(10*width/12,height/2);
            client.hitting=false;
            client.points+=4;
            client.kol_vistrelov++;
        }
        else if(StrelaHitWall(strela))
        {
            strela.moveStrela(start_x,20+i*start_y);
            client.hitting=false;
            client.kol_vistrelov++;
        }
        else
        {
        if(big_ball.y+2*big_ball.r+big_ball.speed>height||big_ball.y+big_ball.speed<0){
            big_ball.speed=-big_ball.speed;
        }
        big_ball.moveBall(big_ball.x,big_ball.y+big_ball.speed);
        if(small_ball.y+2*small_ball.r+small_ball.speed>height||small_ball.y+small_ball.speed<0){
            small_ball.speed=-small_ball.speed;
        }
        small_ball.moveBall(small_ball.x,small_ball.y+small_ball.speed);
        if(client.hitting){
            strela.moveStrela(strela.x+strela.speed,strela.y);
        }
        }
        }
    }
    public void ServerStart(){
        ServerSocket ss;
        try {
            
      

            m.addObserver(()->{bcastAll();});
            
            ip = InetAddress.getLocalHost();
            ss = new ServerSocket(port, 0, ip);
            System.out.append("Server start\n");
            int id=0;
            while(id!=n)
            {
                Socket cs;
                cs = ss.accept();
                DataInputStream dis=new DataInputStream(new BufferedInputStream(cs.getInputStream()));
                String s=dis.readUTF();
                Msg msg=gson.fromJson(s,Msg.class);
                clientAtServer c=new clientAtServer(cs,this,id++,msg.message);
                allClients.add(c);
                service.submit(c);
            }
            
        } catch (IOException ex) {
        }
        CreateElements();
        System.out.println("Elements created");
        while(true)
        {
            if(allReady())
            {
                System.out.println("Game started");
                next();
                CheckForEnd();
                bcastAll();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                try {
                    synchronized(this)
                    {
                        wait();
                        System.out.println("Wait ended");
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(!game_running)ResetGameState();
    }
    }
    
    
    
    public static void main(String[] args) {
        new MainServer().ServerStart();
    }
}
