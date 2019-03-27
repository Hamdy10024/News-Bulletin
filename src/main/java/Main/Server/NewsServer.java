package Main.Server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import java.util.List;

import Main.FileObject.SharedInt;
import Main.FileObject.SharedLog;
import Main.FileObject.SharedObject;
import Main.RMI.RemoteServer;

public class NewsServer  extends UnicastRemoteObject implements RemoteServer,Runnable  {
    private static final long serialVersionUID = 1L;

	private SharedObject<Integer> object;
	private  SharedInt curReaders;
	private String port_number;
	private int num_readers, num_writers;
	private SharedLog readLog,writeLog;
    private int sseq = 0;
    private int maxReqs;
    private ClientBuilder builder ;
	public NewsServer(String port_number,Integer readerNum,Integer writerNum,
                      int reqs) throws RemoteException {
		super();
		this.port_number = port_number;
		num_readers = readerNum;
		num_writers = writerNum;
		readLog = new SharedLog("sSeq\toVal\trID\trNum\n","readers.log");
		writeLog = new SharedLog("sSeq\toVal\twID\n","writers.log");
		object = new SharedInt();
		curReaders = new SharedInt(0);

		maxReqs = reqs * (readerNum+writerNum);
		builder = new ClientBuilder();
		builder.o=object;
		builder.readers = curReaders;
		builder.readLog = readLog;
		builder.writeLog = writeLog;

	}
	public NewsServer(String port_number,Integer readerNum,Integer writerNum,
                      int reqs,Integer obj) throws RemoteException {

	    this(port_number,readerNum,writerNum,reqs);
        object = new SharedInt(obj);

	}

	private void runServer() throws IOException, InterruptedException {
		ServerSocket server_socket ;
        sseq = 0;
		server_socket = new ServerSocket(Integer.parseInt(port_number));
		List<Thread> handlers = new ArrayList<>();
        while (sseq < maxReqs) {
            Socket client = server_socket.accept();
			builder.sseq = ++sseq;
			builder.connection = client;
            ClientHandler handler = builder.get();
            handlers.add(handler);
            handler.start();
        }
        for(Thread handler:handlers) {
            handler.join();
        }
        exportReport();
	}
	private  void exportReport() throws IOException {
//        FileWriter fw = new FileWriter("server.log");
//
//        fw.write(readLog.exportLog());
//        fw.write(writeLog.exportLog());
//        fw.close();
		readLog.exportLog();
		writeLog.exportLog();

    }
	@Override
	public void run() {
		try {
			runServer();
		} catch (IOException e)
		{
			//
			e.printStackTrace();
		} catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

	@Override
	public Integer[] readData(Integer id) throws RemoteException {
        System.out.println(id + " is trying to contact!");
        int seq;
        curReaders.Increment();
        synchronized (this) {
             seq = ++sseq;
        }
        Integer []out =  new Integer[3];
        out[0] = seq;
        int readers = curReaders.read();

        out[2] = object.read();

        out[1] = object.getsseq();

        String line = out[0]+"\t"+out[2]+"\t"+id+"\t"+readers+"\n";
        curReaders.Decrement();
        System.out.println(line);
        readLog.write(line);
        unbindRMI();
		return out;
	}

	@Override
	public Integer[] writeData(Integer id, Integer data) throws RemoteException {
        System.out.println(id + " is trying to contact!");
        int seq;
        synchronized (this) {
            seq = ++sseq;
        }
        Integer []out =  new Integer[2];
        out[0] = seq;
        object.write(data);
        out[1] = object.getsseq();
        String line = out[0]+"\t"+data+"\t"+id+"\n";
        System.out.println(line);
        readLog.write(line);
        unbindRMI();

        return out;
	}

	private void unbindRMI() throws RemoteException {
        if(sseq >=maxReqs) {
            try {
                Naming.unbind("//localhost/MyServer");
                System.out.println("unbinding");
                exportReport();
            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
