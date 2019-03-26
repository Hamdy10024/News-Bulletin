package Main;

import Main.Client.Client;
import Main.Client.Reader;
import Main.Client.ReaderClient;
import Main.Client.Writer;
import org.omg.CORBA.INTERNAL;

public class startClient {
    public static void main(String []args) {
        if(args.length < 5) // prog read/write id nreqs ip port

        {
            System.out.println(args.length);
            return;
        }
        Client client;

        if(args[0].equals("Reader")) {
            // run reader client
            int id = Integer.valueOf(args[1]);
             client= new Reader(id);
        }
        else
        {
            // run writer client
            int id = Integer.valueOf(args[1]);
             client = new Writer(id);
        }
        int port = Integer.valueOf(args[4]);
        client.setServer(args[3],port);
        int reqs = Integer.valueOf(args[2]);
        for(int i=0;i<reqs;i++)
            client.request();
    }
}
