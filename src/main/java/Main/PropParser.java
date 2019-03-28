package Main;


import java.util.ArrayList;
import java.util.List;
;
import java.util.Properties;

public class PropParser {
    public int getNumReaders() {
        return numReaders;
    }

    public int getNumWriters() {
        return numWriters;
    }

    public int getRequests() {
        return requests;
    }

    public List<String> getReaderPass() {
        return readerPass;
    }

    public List<String> getReaders() {
        return readers;
    }

    public List<String> getWriterPass() {
        return writerPass;
    }

    public List<String> getWriters() {
        return writers;
    }

    public String getRegistry() {
        return registry;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public String getServerPort() {
        return serverPort;
    }

    public String getServerPass() {
        return serverPass;
    }

    private int numReaders;

    private int numWriters;

    private int requests;

    private List<String> readers;

    private List<String> writers;
    private List<String> readerPass;
    private List<String> writerPass;
    private String serverAddress;
    private String serverPort;
    private String registry;
    private String serverPass;

    public PropParser(Properties props) {
        readers = new ArrayList<String>();
        writers = new ArrayList<>();
        readerPass = new ArrayList<>();
        writerPass = new ArrayList<>();
        serverAddress = props.getProperty("RW.server");
        serverPort = props.getProperty("RW.server.port");
        numReaders =  Integer.valueOf(props.getProperty("RW.numberOfReaders"));

        numWriters =  Integer.valueOf(props.getProperty("RW.numberOfWriters"));

        requests = Integer.valueOf(props.getProperty("RW.numberOfAccesses"));
        for (int i = 0; i < numReaders; i++) {
            readers.add(props.getProperty("RW.reader" + i));
        }
        System.out.println(readers.size());
        for (int i = 0; i < numWriters; i++) {
            writers.add(props.getProperty("RW.writer" + i));
        }
        for (int i = 0; i < numReaders; i++) {
            readerPass.add(props.getProperty("RW.readerPass" + i));
        }
        System.out.println(readers.size());
        for (int i = 0; i < numWriters; i++) {
            writerPass.add(props.getProperty("RW.writerPass" + i));
        }
        registry = props.getProperty("RW.rmiRegistry.port");

        serverPass = props.getProperty("RW.serverPass");
    }
}
