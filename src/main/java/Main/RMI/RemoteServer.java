package Main.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteServer extends Remote {

    public Integer[] readData(Integer id) throws RemoteException;
    public Integer[] writeData(Integer id ,Integer data) throws RemoteException;


}
