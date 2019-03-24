package Main.Server;

import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

import Main.FileObject.SharedInt;
import Main.FileObject.SharedLog;
import Main.FileObject.SharedObject;

public class NewsServer extends Thread {

	private SharedObject<Integer> object;
	private  SharedInt curReaders;
	private String port_number;
	private int num_readers, num_writers;
	private SharedLog readLog,writeLog;
	private Map<String,Integer> ids;
    private int sseq = 0;
    private int maxReqs;
    private ClientBuilder builder ;
	public NewsServer(String port_number,Integer readerNum,Integer writerNum,
                      List<String> readers,List<String> writers,int reqs)  {
		this.port_number = port_number;
		num_readers = readerNum;
		num_writers = writerNum;
		readLog = new SharedLog("sSeq oVal rID rNum\n");
		writeLog = new SharedLog("sSeq oVal wID\n");
		object = new SharedInt();
		curReaders = new SharedInt(0);
		ids = new HashMap<>();
		for(int i = 0 ;i<readerNum;i++)
		    ids.put(readers.get(i),i+1);
        for(int i = 0 ;i<writerNum;i++)
            ids.put(writers.get(i),i+1+readerNum);
		maxReqs = reqs * (readerNum+writerNum);
		builder = new ClientBuilder();
		builder.o=object;
		builder.readers = curReaders;
		builder.readLog = readLog;
		builder.writeLog = writeLog;

	}
	public NewsServer(String port_number,Integer readerNum,Integer writerNum,
                      List<String> readers,List<String> writers,int reqs,Integer obj) {

	    this(port_number,readerNum,writerNum,readers,writers,reqs);
        object = new SharedInt(obj);

	}

	private void runServer() throws IOException, InterruptedException {
		ServerSocket server_socket ;
        sseq = 0;
		server_socket = new ServerSocket(Integer.parseInt(port_number));
		List<Thread> handlers = new ArrayList<>();
        while (sseq < maxReqs) {
            Socket client = server_socket.accept();
            System.out.println("Next request "+client.getInetAddress().toString());
			builder.id =  ids.get(client.getInetAddress());;
			builder.sseq = ++sseq;
			builder.connection = client;
            ClientHandler handler = builder.get();
            handlers.add(handler);
            handler.start();
        }
        for(Thread handler:handlers) {
            handler.join();
        }
	}
	private  void exportReport() throws IOException {
        FileWriter fw = new FileWriter("server.log");

        fw.write(readLog.exportLog());
        fw.write(writeLog.exportLog());
        fw.close();

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
}
