package Server;

import Server.FileObject.SharedInt;
import Server.FileObject.SharedLog;
import Server.FileObject.SharedObject;

import java.io.IOException;
import java.net.Socket;

public class ClientBuilder {


    Integer sseq;
    public void setSseq(Integer ss) {
        sseq = ss;
    }

    Socket connection;

    public void setConnection(Socket s) {
        connection = s;
    }

    SharedObject<Integer> o;
    public void setO(SharedObject<Integer> a) {
        o =a;
    }

    public  void setReaders(SharedInt s) {
        readers = s;
    }
    SharedInt readers;
    public  void setReadLog(SharedLog s) {
        readLog = s;
    }
    SharedLog readLog;
    public  void setWriteLog(SharedLog s) {
        writeLog = s;
    }
    SharedLog writeLog;
    ClientHandler get() throws IOException {
        return new ClientHandler(sseq,connection,o,readers,readLog,writeLog);
    }


}
