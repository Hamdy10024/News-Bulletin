package Main.Server;

import Main.FileObject.SharedInt;
import Main.FileObject.SharedLog;
import Main.FileObject.SharedObject;
import lombok.Setter;

import java.io.IOException;
import java.net.Socket;

public class ClientBuilder {

    @Setter
    Integer sseq;

    @Setter
    Socket connection;
    @Setter
    SharedObject<Integer> o;
    @Setter
    SharedInt readers;
    @Setter
    SharedLog readLog;
    @Setter
    SharedLog writeLog;
    ClientHandler get() throws IOException {
        return new ClientHandler(sseq,connection,o,readers,readLog,writeLog);
    }


}
