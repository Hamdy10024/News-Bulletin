package Client;

import java.io.FileWriter;
import java.io.IOException;

public abstract class AbstractClient {
    protected int id;
    protected String ServerIP;
    protected Integer ServerPort;
    protected String file;
    protected FileWriter fstream;
    public AbstractClient(Integer id) {
        this.id = id;
        file = "log"+id+".log";
    }
    public void setServer(String serverIP,Integer serverPort) {
        this.ServerIP = serverIP;
        this.ServerPort = serverPort;
    }
    public abstract boolean request();

    protected abstract boolean log();

    public void close() throws IOException {
        fstream.close();
    }

}
