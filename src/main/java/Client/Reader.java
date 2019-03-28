package Client;

import java.io.*;
import java.net.Socket;

public class Reader extends AbstractClient {
    public Reader(Integer id) {
        super(id);

        try {
            fstream = new FileWriter(file);
            String line = "rseq\tsseq\tObval\n";
            fstream.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
   private int sseq,rseq,obval;
    @Override
    public boolean request() {
        try {
            Socket connection = new Socket(ServerIP, ServerPort);
            OutputStream outToServer = connection.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            out.writeInt(-1);
            out.writeInt(0);
            out.writeInt(id);
            InputStream inFromServer = connection.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            rseq = in.readInt();
            sseq = in.readInt();
            obval = in.readInt();
            log();
//            System.out.println("Server response : " + in.readUTF());
            connection.close();
        } catch (IOException e) {
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
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
