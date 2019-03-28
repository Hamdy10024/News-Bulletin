package Server;

import Server.FileObject.SharedInt;

import java.rmi.RemoteException;

public class ServerBuilder {

   public void setCurReaders(SharedInt s) {
       curReaders = s;

   }
    private SharedInt curReaders;

    public void setNumReaders(int numReaders) {
        this.numReaders = numReaders;
    }

    public void setNumWriters(int numWriters) {
        this.numWriters = numWriters;
    }

    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    public void setMaxReqs(int maxReqs) {
        this.maxReqs = maxReqs;
    }

    private String portNumber;
    private int numReaders;
    private int numWriters;
    private int maxReqs;


    public ServerBuilder() {
    }

    public NewsServer getServer() throws RemoteException {
        return new NewsServer(portNumber,numReaders,numWriters,maxReqs);

    }
    public NewsServer getServer(int init) throws RemoteException {
        return new NewsServer(portNumber,numReaders,numWriters,maxReqs,init);

    }
}
