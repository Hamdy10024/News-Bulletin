package Client;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RemoteReader extends  RemoteClient {
    private RemoteServer look_up;
    private int sseq,rseq,obval;
    private Integer registry;
    public RemoteReader(Integer id,Integer regPort) {

        super(id);
        registry = regPort;
        try {
            fstream = new FileWriter(file);
            String line = "rseq\tsseq\tObval..\n";
            fstream.write(line);
            //fstream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean request() {
        try {
            System.out.println("RMI Reading");
            System.setProperty("java.rmi.server.hostname",this.ServerIP);
            Registry registry = LocateRegistry.getRegistry(this.ServerIP, this.registry);
            look_up = (RemoteServer) registry.lookup("MyServer");
            Integer []res = look_up.readData(id);
            rseq = res[0];
            sseq = res[1];
            obval = res[2];
            log();

        }catch (RemoteException | NotBoundException  e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected boolean log() {
        String line = rseq+"\t"+sseq+"\t"+obval+"\n";
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
