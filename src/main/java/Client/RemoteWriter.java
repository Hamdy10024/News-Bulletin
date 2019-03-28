package Client;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

public class RemoteWriter extends RemoteClient {
    private int rseq,sseq;
    private RemoteServer look_up;
    private  Integer registry;
    public RemoteWriter(Integer id,Integer regPort) {

        super(id);
        registry = regPort;
        try {
            fstream = new FileWriter(file);
            String line = "rseq\tsseq\n";
            fstream.write(line);
            //fstream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean request() {
        try {
            System.setProperty("java.rmi.server.hostname",this.ServerIP);
            Registry registry = LocateRegistry.getRegistry(this.ServerIP, this.registry);
            look_up = (RemoteServer) registry.lookup("MyServer");
            Random rand = new Random();
            int newsID = rand.nextInt();
            Integer []res = look_up.writeData(id,newsID);
            rseq = res[0];
            sseq = res[1];
            log();

        }catch (RemoteException | NotBoundException  e) {
            return false;
        }
        return true;
    }

    @Override
    protected boolean log() {
        String line = rseq+"\t"+sseq+"\n";
        try {

            fstream.write(line);
            System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
