



import java.io.*;
import java.net.*;
public class Server {
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    //construter
    public Server(){
        try{
            server=new ServerSocket(7777);
            System.out.println("server is ready to accept connection");
            System.out.println("waiting...");
            socket=server.accept();
            
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
                    System.out.println("client terminted the chat");
                    socket.close();
                    break;
                }
                System.out.println("client :"+msg);
               
            }
              } catch(Exception e){
                   // e.getStackTrace();
                   System.out.println("connection closed...");
                 }
            
        };
        new Thread(r1).start();
        
    }
    public  void startWriting(){
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
        System.out.println("this is server ..goingto start server");
          new Server();
        
    }
    
}
