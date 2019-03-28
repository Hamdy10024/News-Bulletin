package Server;

import Server.FileObject.SharedInt;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static  void startSocket(Integer port,Integer readers,Integer writers, Integer  reqs) throws InterruptedException, RemoteException {
        ServerBuilder builder = new ServerBuilder();
        builder.setCurReaders(new SharedInt(0));
        builder.setPortNumber(port+"");
        builder.setNumReaders(readers);
        builder.setNumWriters(writers);
        builder.setMaxReqs(reqs);
        System.out.println("Starting Serveer");
        NewsServer server = builder.getServer();

        Thread serverThread = new Thread(server);
        serverThread.start();
        serverThread.join();
    }
    public static void  startRMI(Integer port,Integer readers,Integer writers, Integer  reqs,
                                 String Ip,Integer RMIPort) throws RemoteException, AlreadyBoundException {
        ServerBuilder builder = new ServerBuilder();
        builder.setCurReaders(new SharedInt(0));
        builder.setPortNumber(port+"");
        builder.setNumReaders(readers);
        builder.setNumWriters(writers);
        builder.setMaxReqs(reqs);
        Integer registry = (RMIPort);
        NewsServer server = builder.getServer();
        System.setProperty("java.rmi.server.hostname",Ip);
        System.out.println("Starting RMI Serveer");
		Registry registr = LocateRegistry.createRegistry(registry);
		registr.bind("MyServer",server);

    }
    public static void main(String[] args) throws IOException, AlreadyBoundException {
        if(args.length < 7) { // port readers writers reqs Ip rmiport type
            System.out.println(args.length);
            return;
        }
        if(args[6].trim().equals("RMI")) {
            startRMI(Integer.valueOf(args[0]),Integer.valueOf(args[1]),Integer.valueOf(args[2]),Integer.valueOf(args[3]),
                    (args[4]),Integer.valueOf(args[5]));
        }
        else {
            try {
                startSocket(Integer.valueOf(args[0]),Integer.valueOf(args[1]),Integer.valueOf(args[2]),Integer.valueOf(args[3]));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    }
