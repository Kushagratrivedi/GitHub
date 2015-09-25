import java.net.*; 
import java.io.*; 

public class CryptographyServer extends Thread
{ 
 protected Socket clientSocket;
/**
 * FOR ENCRYPTION DECRYPTION METHOD - PLEASE READ CAESER CIPHER 
 * @param args
 * @throws IOException 
 */
 public static void main(String[] args) throws IOException 
   { 
    ServerSocket serverSocket = null; 

    try { 
        //SERVER SOCKET
         serverSocket = new ServerSocket(10005); 
         System.out.println ("Connection Socket Created");
         try { 
              while (true)
                 {
                  System.out.println ("Waiting for Connection");
                  new CryptographyServer (serverSocket.accept()); 
                 }
             } 
         catch (IOException e) 
             { 
              System.err.println("Accept failed."); 
              System.exit(1); 
             } 
        } 
    catch (IOException e) 
        { 
         System.err.println("Could not listen on port: 10005."); 
         System.exit(1); 
        } 
    finally
        {
         try {
              serverSocket.close(); 
             }
         catch (IOException e)
             { 
              System.err.println("Could not close port: 10005."); 
              System.exit(1); 
             } 
        }
   }

 private CryptographyServer (Socket clientSoc)
   {
    clientSocket = clientSoc;
    start();
   }

 @Override
 public void run()
   {
    System.out.println ("New Communication Thread Started");

    try { 
         PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), 
                                      true); 
         BufferedReader in = new BufferedReader( 
                 new InputStreamReader( clientSocket.getInputStream())); 

         String inputLine; 
         CaeserCipher cc = new CaeserCipher();
         while ((inputLine = in.readLine()) != null) 
             {
                 if (inputLine.equals("Bye.")) 
                  break; 
                String output = "";
                String [] s = inputLine.split("/");
                if(s[0].equalsIgnoreCase("encrypt"))
                {
                    output = cc.encrypt(s[1]);
                }
                else 
                {
                    output = cc.decrypt(s[1]);
                }
                
                //INPUT AT SERVER
              System.out.println ("Server: " + inputLine); 
              out.println(output); 
              
             } 

         out.close(); 
         in.close(); 
         clientSocket.close(); 
        } 
    catch (IOException e) 
        { 
         System.err.println("Problem with Communication Server");
         System.exit(1); 
        } 
    }
} 