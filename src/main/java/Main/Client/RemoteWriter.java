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
import java.util.Random;

public class RemoteWriter extends RemoteClient {
    private int rseq,sseq;
    private RemoteServer look_up;
    public RemoteWriter(Integer id) {
        super(id);
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
            look_up = (RemoteServer) Naming.lookup("//localhost/MyServer");
            Random rand = new Random();
            int newsID = rand.nextInt();
            Integer []res = look_up.writeData(id,newsID);
            rseq = res[0];
            sseq = res[1];
            log();

        }catch (RemoteException | NotBoundException | MalformedURLException e) {
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
