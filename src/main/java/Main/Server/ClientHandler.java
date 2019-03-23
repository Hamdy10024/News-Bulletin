package Main.Server;

import Main.FileObject.SharedInt;
import Main.FileObject.SharedLog;
import Main.FileObject.SharedObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Properties;
import java.util.Random;

public class ClientHandler extends Thread {
    private SharedLog readLog;
    private  SharedLog writeLog;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private SharedObject<Integer> object;
    private SharedInt readers;
    private Socket conn;
    private  Integer sSeq;
    private Integer id;
    private boolean isread;

    /**
     * Constructor for client handler
     * Terminates when request is handled.
     */
    public ClientHandler(Integer sseq,Integer id, Socket connection,SharedObject<Integer> o,
                         SharedInt readers) throws IOException {

        readLog = new SharedLog("sSeq oVal rID rNum");
        writeLog = new SharedLog("sSeq oVal wID");
        inputStream = new DataInputStream(connection.getInputStream());
        outputStream = new DataOutputStream(connection.getOutputStream());
        object = o;
        conn = connection;
        this.readers = readers;
        sSeq = sseq;
        this.id = id;
        isread = false;

    }
    /**
     * Handles client request by accepting connection,
     * parsing input from connection and updating object.
     */
    public void run(){
        System.out.println("thread is running...");
        try {
            while (conn.isConnected()) {
//                if (inputStream.available() < 4)
//                    continue;
//                if (!conn.isConnected())
//                    terminate();
                Integer request = inputStream.readInt();
                request -= 808464437; //TODO remove this
                System.out.println("request is "+request);
                if (request == -1)
                    handleRead();
                else
                    handleWrite(request);
                break;
            }
            terminate();
        }catch (IOException e) {
            //terminate();
        }
    }


    /**
     * Handles read requests
     */
    private void handleRead() throws IOException {
        int reader = readers.Increment();

        Integer obVal = object.read();
        Random rand =  new Random();
        try {
            Thread.sleep(rand.nextInt(10000));
        } catch(InterruptedException e) {
            // DO nothing
        }
        outputStream.write(obVal);
        readLog.write(sSeq+"\t"+obVal+"\t"+id+ "\t"+reader+"\n");
        System.out.println(sSeq+"\t"+obVal+"\t"+id+ "\t"+reader+"\n");
        isread = true;

    }

    /**
     * Handles Write requests
     * @param val
     */
    private void handleWrite(Integer val){
        object.write(val);
        writeLog.write(sSeq + "\t"+val+"\t"+id+"\n");
        System.out.println(sSeq + "\t"+val+"\t"+id);
    }

    /**
     * Calls termination after request is satisfied.
     * Should write in Log depending on type and close connection
     */
    private void terminate() throws IOException {
        System.out.println("terminating");

        if(isread)
            readers.Decrement();
        if(conn.isConnected())
            conn.close();


    }

}

