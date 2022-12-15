


import java.io.*;
import java.net.*;


public class Client {
    Socket socket;
     BufferedReader br;
    PrintWriter out;
    
   
    public Client(){
         try{
            System.out.println("sending requert to server");
            socket=new Socket("127.0.0.1",7777);
            System.out.println("connection done.");
             br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());
            
            startReading();
            startWriting();
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
     public void startReading(){
        //thread read karke data rahega
        Runnable r1=()->{
            System.out.println("redar starting");
             try{
            while(true){
               
                String msg=br.readLine();
                if(msg.equals("exit"))
                {
                    System.out.println("server terminted the chat");
                    socket.close();
                    break;
                }
                System.out.println("server :"+msg);
                
                 }
            }catch(Exception e){
                   // e.getStackTrace();
                   System.out.println("connection closed...");
            }
            
            
        };
        new Thread(r1).start();
        
    }
      public void startWriting(){
        System.out.println("writer started...");
          Runnable r2=()->{
              try{
              while(!socket.isClosed()){
                  
                      BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
                      String content=br1.readLine();
                      out.println(content);
                      out.flush();
                     if(content.equals("exit")){
                          socket.close();
                          break;
                      } 
                      
                      
                 
                }
              System.out.println("connection closed...");
               }catch(Exception e){
                    e.getStackTrace();
              }
            
            
        };
          new Thread(r2).start();
        
    }
    
     public static void main(String[]args){
        System.out.println("this is client ..goingto start client");
         new Client();
        
    }
    
    
}
