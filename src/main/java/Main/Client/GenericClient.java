package Main.Client;

public abstract class GenericClient  {
    private String ID;
    private String ServerIP;
    private String ServerPort;

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

    public String getServerPort(){
        return new String(this.ServerPort);
    }


    public abstract void execute();
    public abstract boolean log();
    public abstract boolean request();

}
