package Main.Client;

import lombok.Setter;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class Writer extends Client {
    @Setter
    private Integer newsID;
    public Writer(Integer id) {
        super(id);
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
        Path path = Paths.get(file);
        String line = rseq+"\t"+sseq+"\n";
        try {

            Files.write(path, line.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;    }
}