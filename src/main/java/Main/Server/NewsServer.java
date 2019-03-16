package Main.Server;

import Main.FileObject.SharedObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class NewsServer {

    private SharedObject<Integer> object;

    public NewsServer(String [] args) throws IOException {
        String propertiesFile = args[0];
        Properties properties = new Properties();
        properties.load(new FileInputStream(propertiesFile));
        object = new SharedObject<Integer>();

    }

    /**
     * Runs server functionality as stated in documents and writes output in outputfile.
     * @param outputFile File stream to write results in.
     */
    public void start(FileOutputStream outputFile) {
        //TODO: Implement
    }

    /**
     * Runs server functionality as stated in documents and writes output in outputfile.
     * @param outputFile File name to write results in.
     */
    public void start(String outputFile) throws IOException {
        start(new FileOutputStream(outputFile));

    }

    /**
     * Runs server functionality as stated in documents and writes output in outputfile with initial object
     * value.
     * @param outputFile File stream to write results in.
     * @param initial initia value
     */
    public void start(FileOutputStream outputFile,Integer initial) {
        object = new SharedObject<Integer>(initial);
        start(outputFile);


    }
    public void start(String outputFile, Integer initial) throws  IOException {
        start(new FileOutputStream(outputFile),initial);

    }
}
