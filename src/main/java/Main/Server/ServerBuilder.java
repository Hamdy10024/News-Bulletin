package Main.Server;

import Main.FileObject.SharedInt;
import Main.FileObject.SharedLog;
import Main.FileObject.SharedObject;
import lombok.Setter;
import java.util.List;

public class ServerBuilder {

    @Setter
    private SharedInt curReaders;
    @Setter
    private String portNumber;
    @Setter
    private int numReaders;
    @Setter
    private int numWriters;
    @Setter
    private int maxReqs;
    @Setter
    private  List<String> readers;
    @Setter
    private List<String>writers;

    public ServerBuilder() {
    }

    public NewsServer getServer() {
        return new NewsServer(portNumber,numReaders,numWriters,readers,writers,maxReqs);

    }
    public NewsServer getServer(int init) {
        return new NewsServer(portNumber,numReaders,numWriters,readers,writers,maxReqs,init);

    }
}
