package Client;

import java.io.IOException;
import java.rmi.NotBoundException;

public class Client {

    private static RemoteServer look_up;

    public static void main(String []args) throws IOException, NotBoundException {
        if(args.length < 7) // read/write  id nreqs ip port rmiport RMI/Socket

        {
            System.out.println(args.length);
            return;
        }
        AbstractClient client;

        int id = Integer.valueOf(args[1]);
        if(args[0].equals("Reader") && !args[6].equals("RMI"))
             client= new Reader(id);
        else if (!args[0].equals("Reader") && !args[6].equals("RMI"))
             client = new Writer(id);
         else if(args[0].equals("Reader"))
            client = new RemoteReader(id,Integer.valueOf(args[5]));
         else
             client = new RemoteWriter(id,Integer.valueOf(args[5]));
        int port = Integer.valueOf(args[4]);
        client.setServer(args[3],port);
        int reqs = Integer.valueOf(args[2]);
        for(int i=0;i<reqs;i++)
            client.request();
        client.close();

   }
}
