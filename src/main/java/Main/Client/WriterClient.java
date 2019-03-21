package Main.Client;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class WriterClient extends GenericClient {
    private int news_ID;

    public WriterClient(String Client_ID, int newsID){
        super(Client_ID);
        news_ID = newsID;
    }

    public void execute() {
        StringBuilder identifier = new StringBuilder("RW.reader");
        identifier.append(super.ID);
        StringBuilder outputFile = new StringBuilder("log");
        outputFile.append(super.ID);
        log(outputFile.toString(),"Client Type : Reader");
        StringBuilder name = new StringBuilder("Client Name : ");
        name.append(super.ID);
        log(outputFile.toString(),name.toString());

        request();
    }

    public boolean request() {
        String server = (String)super.prop.getProperty("RW.Server");
        Integer port = Integer.parseInt(super.prop.getProperty("RW.Server.Port"));
        try {
            Socket connection = new Socket(server, port);
            OutputStream outToServer = connection.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            out.writeInt(1);
            out.writeInt(this.news_ID);
            Random rand =  new Random();
            try {
                Thread.sleep(rand.nextInt(10000));
            } catch(InterruptedException e) {
                // DO nothing
            }
            InputStream inFromServer = connection.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            System.out.println("Server response : " + in.readUTF());
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
