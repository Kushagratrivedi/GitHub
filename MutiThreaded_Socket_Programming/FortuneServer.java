import java.net.*; 
import java.io.*; 

public class FortuneServer extends Thread
{ 
 protected Socket clientSocket;

 /*
 * INSTRUCTION : SERVER NEVER SHUT DOWN, IF THERE IS ANY KIND OF PROBLEM WITH CLIENT CONNECTION
 * FOR EXAMPLE: GOOGLE WILL NOT GO DOWN IF CONNECTION BETWEEN YOUR PC AND GOOGLE MACHINE WILL BREAK.
 * 
 */
 
 
 public static void main(String[] args) throws IOException 
   { 
    ServerSocket serverSocket = null; 

    try { 
        
        //SERVER PORT
         serverSocket = new ServerSocket(10008); 
         System.out.println ("Connection Socket Created");
         try { 
              while (true)
                 {
                     //SERVER STARTED
                  System.out.println ("Fortune Server Waiting for Connection");
                  // ACCEPTING REQUEST - FOR EACH CLIENT NEW THREAD
                  FortuneServer fortuneServer = new FortuneServer 
        (serverSocket.accept()); 
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
         System.err.println("Could not listen on port: 10008."); 
         System.exit(1); 
        } 
    finally
        {
         try {
             //ERROR - SERVER SOCKET CLOSE 
              serverSocket.close(); 
             }
         catch (IOException e)
             { 
              System.err.println("Could not close port: 10008."); 
              System.exit(1); 
             } 
        }
   }

 private FortuneServer (Socket clientSoc)
   {
       //CLIENT THREAD CREATED
    clientSocket = clientSoc;
    // FOR EACH CLIENT - DIFFERENT THREAD
    start();
   }

 @Override
 public void run()
   {
    System.out.println ("New Communication Thread Started");

    try 
    { 
        BufferedReader in; 
        //OUTPUT STREAM 
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), 
                true)) 
        {
            in = new BufferedReader( 
                    new InputStreamReader( clientSocket.getInputStream()));
            String inputLine;
            //READ NUMBER OF FORTUNE COOKIES CLIENT WANTS
            while ((inputLine = in.readLine()) != null)
            {
                System.out.println("Hello");
                if (inputLine.equals("Bye."))
                    break;
                int number = Integer.parseInt(inputLine);
                System.out.println ("Server: " + inputLine);
                // SENDS RANDOMLY GENERATED COOKIES
                out.println(new FortuneCookies().getCookies(number));
                
            }
        }
        //CLIENT CLOSE
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