package Main;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
;
import java.util.Properties;

public class PropParser {
    @Getter
    private int numReaders;

    @Getter
    private int numWriters;

    @Getter
    private int requests;

    @Getter
    private List<String> readers;

    @Getter
    private List<String> writers;
    @Getter
    private String serverAddress;
    @Getter
    private String serverPort;



    public PropParser(Properties props) {
        readers = new ArrayList<String>();
        writers = new ArrayList<>();
        serverAddress = props.getProperty("RW.server");
        serverPort = props.getProperty("RW.server.port");
        numReaders =  Integer.valueOf(props.getProperty("RW.numberOfReaders"));

        numWriters =  Integer.valueOf(props.getProperty("RW.numberOfWriters"));

        requests = Integer.valueOf(props.getProperty("RW.numberOfAccesses"));
    }
}
