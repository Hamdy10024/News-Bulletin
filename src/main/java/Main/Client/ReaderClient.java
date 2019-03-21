package Main.Client;

public class ReaderClient extends GenericClient {


    public ReaderClient(String Client_ID){
        super(Client_ID);
    }

    public void execute() {
        StringBuilder identifier = new StringBuilder("RW.reader");
        identifier.append(super.getID());


    }

    public boolean request() {


        return false;
    }
}
