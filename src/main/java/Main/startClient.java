package Main;

import Main.Client.Client;
import Main.Client.Reader;
import Main.Client.Writer;
import Main.RMI.RemoteServer;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class startClient {

    private static RemoteServer look_up;

    public static void main(String []args) throws RemoteException, NotBoundException, MalformedURLException {
//        if(args.length < 5) // prog read/write id nreqs ip port
//
//        {
//            System.out.println(args.length);
//            return;
//        }
//        Client client;
//
//        if(args[0].equals("Reader")) {
//            // run reader client
//            int id = Integer.valueOf(args[1]);
//             client= new Reader(id);
//        }
//        else
//        {
//            // run writer client
//            int id = Integer.valueOf(args[1]);
//             client = new Writer(id);
//        }
//        int port = Integer.valueOf(args[4]);
//        client.setServer(args[3],port);
//        int reqs = Integer.valueOf(args[2]);
//        for(int i=0;i<reqs;i++)
//            client.request();
        look_up = (RemoteServer) Naming.lookup("//localhost/MyServer");
        Integer []res = look_up.readData(1);
        System.out.println(res[0]);
   }
}
