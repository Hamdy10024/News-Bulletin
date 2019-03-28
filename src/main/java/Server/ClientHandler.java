package Server;

import Server.FileObject.SharedInt;
import Server.FileObject.SharedLog;
import Server.FileObject.SharedObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {
    private SharedLog readLog;
    private  SharedLog writeLog;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private SharedObject<Integer> object;
    private SharedInt readers;
    private Socket conn;
    private  Integer rSeq,sSeq;
    private Integer id;
    private boolean isread;

    /**
     * Constructor for client handler
     * Terminates when request is handled.
     */
    public ClientHandler(Integer sseq, Socket connection,SharedObject<Integer> o,
                         SharedInt readers,SharedLog readLog,SharedLog writeLog) throws IOException {

        this.readLog =readLog;
        this.writeLog = writeLog;
        inputStream = new DataInputStream(connection.getInputStream());
        outputStream = new DataOutputStream(connection.getOutputStream());
        object = o;
        conn = connection;
        this.readers = readers;
        rSeq = sseq;
        sSeq = 0;
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
                int news = inputStream.readInt();
                id = inputStream.readInt();
            //    request -= 808464437; //TODO remove this
             //   id -= 808464437; //TODO remove this
                System.out.println("request is "+request + " from "+id);
                if (request == -1)
                    handleRead();
                else
                    handleWrite(news);
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

         sSeq = object.getsseq();
        outputStream.writeInt(rSeq);
        outputStream.writeInt(sSeq);
        outputStream.writeInt(obVal);
        readLog.write(sSeq+"\t"+obVal+"\t"+id+ "\t"+reader+"\n");
        System.out.println(sSeq+"\t"+obVal+"\t"+id+ "\t"+reader+"\n");
        isread = true;

    }

    /**
     * Handles Write requests
     * @param val
     */
    private void handleWrite(Integer val) throws IOException {
        object.write(val);
        sSeq = object.getsseq();
        outputStream.writeInt(rSeq);
        outputStream.writeInt(sSeq);
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

