package Main.Client;

public abstract class Client {
    protected int id;
    protected String ServerIP;
    protected Integer ServerPort;
    protected String file;
    public Client(Integer id) {
        this.id = id;
        file = "log"+id+".log";
    }
    public void setServer(String serverIP,Integer serverPort) {
        this.ServerIP = serverIP;
        this.ServerPort = serverPort;
    }
    public abstract boolean request();

    protected abstract boolean log();

}
