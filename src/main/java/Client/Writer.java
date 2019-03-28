package Client;


import java.io.*;
import java.net.Socket;
import java.util.Random;

public class Writer extends AbstractClient {

    private Integer newsID;
    public void setNewsID(Integer nid) {
        newsID = nid;
    }
    public Writer(Integer id) {
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
    private int rseq,sseq;
    @Override
    public boolean request() {
        try {
            Socket connection = new Socket(ServerIP, ServerPort);
            OutputStream outToServer = connection.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            Random rand = new Random();
            newsID = rand.nextInt();
            out.writeInt(1);
            out.writeInt(newsID);
            out.writeInt(id);
            InputStream inFromServer = connection.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            rseq = in.readInt();
            sseq = in.readInt();
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
