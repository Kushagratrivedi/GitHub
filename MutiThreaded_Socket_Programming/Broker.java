
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;


/**
 *
 * @author Himani
 * Broker : IP ADDRESS -> LOCAL HOST, PORT -> 10009
 * FUNCTION: REGISTER - REGISTERS SERVER
 * FUNCTION: UNREGISTER - UNREGISTERS SERVER
 * FUNCTION: LOOKUP - GET SERVER INFORMATION (IP ADDRESS, PORT)
 * FUNCTION: PARSEINPUT - PARSES STRING RECEIVED FROM CLIENT/SERVER
 * PRIVATE STATIC CLASSS SOCKETADDESS - PRIVATE CLASS CONTAINS - PORT NUMBER,
   IPADDRESS OF PARTICULAR SERVER
 * HASHTABLE SERVERS - TABLE THAT CONTAINS SERVER NAME AND SOCKET ADDRESS PAIR
 * INPUT WILL BE RECEIVED FROM SERVER/CLIENT IN FOLLOWING FORMAT:
 * 1. SERVER/SERVERNAME - SERVER WANTS TO UNREGISTER ITSELF
 * 2. CLIENT/SERVERNAME - CLIENT WANTS SERVER INFORAMATION OF SERVERNAME
 * 3. SERVER/SERVERNAME/ADDRESS/PORT - SERVER WANTS TO REGISTER ITSELF
 * 
 * DECREPTION: 
 * BROKER CAN SIMULTANEOUSLY SERVE TO CLIENT AND SERVER AS IT WOULD CREATE
 * NEW THREAD FOR EACH CLIENT AND SERVER.
 * DEPENDING ON REQUEST, BROKER WILL CALL PARSE INPUT METHOD AND PARSE
 * INPUT METHOD WILL CALL APPROPRIATE METHOD, THOSER ARE
 * LOOKUP, REGISTER, UNREGISTER
 * 
 * 
 * 
 * 
 */
public class Broker extends Thread
{
    //CONTAINS ALL SERVERS NAME AND SOCKETADDRESS
    private static final Hashtable<String, SocketAddress> servers = new 
        Hashtable<>();
    private static Broker broker;
    protected Socket clientSocket;
    
    public static void main(String args[])
    {
        ServerSocket brokerSocket =  null;
        try
        {
           //BROKER CREATED ON 10009
           brokerSocket = new ServerSocket(10009); 
           System.out.println("Broker created");
           try
           {
                while(true)
                {
                    broker = new Broker(brokerSocket.accept());
                }
           }
           catch(IOException e)
           {
               System.err.println("Accept Failed");
               System.exit(1);
           }
           
            
        }
        catch(IOException e)
        {
            System.err.println("Broker could not listen to port : 10009");
            System.exit(1);
        }
        finally
        {
            
            try 
            {
                brokerSocket.close();
            } 
            catch (IOException ex) 
            {
                System.err.println("Socket cannot be closed");
            }
            
            
        }
    }
    
    
    private Broker(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
        start();
    }
    
    @Override
    public void run()
    {
        System.out.println("New Thread has started");
        try
        {
            BufferedReader in;
            try(PrintWriter out = 
                    new PrintWriter(this.clientSocket.getOutputStream(), true))
            {
                in = new BufferedReader(new InputStreamReader(
                        this.clientSocket.getInputStream()));
                String inputline;
                inputline = in.readLine();
                //client - client/servername
                //server - server/servername
                //server - server/servername/address/port
                String output = parseInput(inputline);
                System.out.println("Server: " + inputline);
                out.println(output);
                
            }
            in.close();
            this.clientSocket.close();
        }
        catch(IOException e)
        {
            System.err.println("Error: Problem in Communication");
            System.exit(1);
        }
        
    }
    //PUT THE NAME OF THE SERVER AND SOCKET ADDRESS INTO THE HASHTABLE
    public void register(String name, String ip, String port)
    {
        SocketAddress s = new SocketAddress(Integer.parseInt(port), ip);
        if(!servers.containsKey(name))
            servers.put(name, s);
        else
            throw new IllegalArgumentException("Server with this name already"
                    + " exist, cannot register");
    }
    //REMOVES THE SERVER INFORMATION FROM THE HASHTABLE
    public void unregister(String name)
    {
        if(!servers.containsKey(name))
            throw new java.util.NoSuchElementException("Server Not found, "
                    + "Cannot unregister");
        servers.remove(name);
    }
    
    //GET THE INFOMATION OF SERVER FROM THE HASHTABLE
    public SocketAddress lookup(String name)
    {
        if(!servers.containsKey(name))
            throw new java.util.NoSuchElementException("Server does not exist");
        return servers.get(name);
    }
    //PARSE INPUT THAT IS COMING FROM THE CLIENT/SERVER
    private String parseInput(String inputline) 
    {
        String [] splittedParts = inputline.split("/");
        //String output = null;
        if(splittedParts.length == 2 
                && splittedParts[0].equalsIgnoreCase("client"))
        {
            Broker.SocketAddress sa = this.lookup(splittedParts[1]);
            System.out.println("BROKER: REQUEST FROM CLIENT FOR "
                    + "SERVER: "+ splittedParts[1]);
            
            return sa.toString();
        }
        else if(splittedParts.length == 2 
                && splittedParts[0].equalsIgnoreCase("server"))
        {
            this.unregister(splittedParts[1]);
            System.out.println("BROKER: UNREGISTRATION REQUEST FROM SERVER:"
                    + " "+splittedParts[1]);
            return "Successful: Unregistration";
        }
        else if(splittedParts.length == 4 
                && splittedParts[0].equalsIgnoreCase("server"))
        {
            
            this.register(splittedParts[1], splittedParts[2], splittedParts[3]);
            System.out.println("BROKER: REGISTRATION REQUEST FROM SERVER: NAME "
                    + "-> "+splittedParts[1]+" IP ADDRESS: "+ splittedParts[2]+""
                    + " PORT: "+ splittedParts[3]);
            return "Successful: Registration";
        }
        return "Error: Input is invalid";
        
    }
    
    //SOCKET ADDRESS IS PRIVATE CLASS, CANNOT BE ACCESSED USING ANY OTHER CLASS
   
    
    /************************************************
     *              SocketAddress
     ************************************************/
    static class SocketAddress
    {
        int port;
        String ip;
        SocketAddress(int port, String ip)
        {
            this.port = port;
            this.ip = ip;
        }
        @Override
        public String toString()
        {
            return this.port+"/"+this.ip;
        }
    }
    
    
    
    
}
