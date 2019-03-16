package Main.Server;

import Main.FileObject.SharedLog;

public class ClientHandler {
    private SharedLog readLog;
    private  SharedLog writeLog;

    //TODO: set parameters to include client socket/data to connect and sseq and request type

    /**
     * Constructor for client handler
     * Terminates when request is handled.
     */
    public ClientHandler(){
        readLog = new SharedLog("sSeq oVal rID rNum");
        writeLog = new SharedLog("sSeq oVal wID");
        handle();
    }

    /**
     * Handles client request by accepting connection,
     * parsing input from connection and updating object.
     */
    private void handle() {
        // TODO:some code
        terminate();
    }

    /**
     * Handles read requests
     */
    private void handleRead(){
        //TODO
    }

    /**
     * Handles Write requests
     * @param val
     */
    private void handleWrite(Integer val){
        //TODO
    }

    /**
     * Calls termination after request is satisfied.
     * Should write in Log depending on type and close connection
     */
    private void terminate() {
        // TODO:write in log

    }

}
