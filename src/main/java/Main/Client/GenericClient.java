package Main.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public abstract class GenericClient  {
    protected String ID;
    protected String IP;
    protected String ServerIP;
    protected String ServerPort;
    protected Properties prop = null;

    public GenericClient(String Client_ID) {
        this.ID = Client_ID;
    }

    public boolean setServer(String SerIP){
        this.ServerIP = SerIP;
        return true;
    }

    public boolean setPort(String SerPort){
        this.ServerPort = SerPort;
        return true;
    }

    public String getID(){
        return new String(this.ID);
    }

    public String getServerIP(){
        return new String(this.ServerIP);
    }

    public String getServerPort() { return new String(this.ServerPort); }

    public String getIP() { return new String(this.IP); }

    /** Task Runner For Client
     */
    public abstract void execute();

    /** Client Reader to read configurations From the Properties file
     *
     * @return True if success False otherwise
     */
    public boolean readProperties(){
        try {
            prop = new Properties();
            prop.load(getClass().getResourceAsStream("system.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /** Client Logger
     * @param file  file to write to
     * @param line  String to write
     * @return True if success False otherwise
     */
    public  boolean log(String file, String line){
        Path path = Paths.get(file);
        try {
            Files.write(path, line.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /** Client Request Method
     *
     * @return True if success False otherwise
     */
    public abstract boolean request();

}
