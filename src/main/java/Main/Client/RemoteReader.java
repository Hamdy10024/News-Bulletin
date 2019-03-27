package Main.Client;

import Main.RMI.RemoteServer;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RemoteReader extends  RemoteClient {
    private RemoteServer look_up;
    private int sseq,rseq,obval;
    public RemoteReader(Integer id) {
        super(id);
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
            look_up = (RemoteServer) Naming.lookup("//localhost/MyServer");
            Integer []res = look_up.readData(id);
            rseq = res[0];
            sseq = res[1];
            obval = res[2];
            log();

        }catch (RemoteException | NotBoundException | MalformedURLException e) {
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
