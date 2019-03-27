package Main;

import Main.Client.*;
import Main.RMI.RemoteServer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class startClient {

    private static RemoteServer look_up;

    public static void main(String []args) throws IOException, NotBoundException {
        if(args.length < 6) // read/write  id nreqs ip port RMI/Socket

        {
            System.out.println(args.length);
            return;
        }
        Client client;

        int id = Integer.valueOf(args[1]);
        if(args[0].equals("Reader") && !args[5].equals("RMI"))
             client= new Reader(id);
        else if (!args[0].equals("Reader") && !args[5].equals("RMI"))
             client = new Writer(id);
         else if(args[0].equals("Reader"))
            client = new RemoteReader(id);
         else
             client = new RemoteWriter(id);
        int port = Integer.valueOf(args[4]);
        client.setServer(args[3],port);
        int reqs = Integer.valueOf(args[2]);
        for(int i=0;i<reqs;i++)
            client.request();
        client.close();

   }
}
