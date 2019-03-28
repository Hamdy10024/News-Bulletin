package Main;

import java.io.*;
import java.util.Properties;

import Server.FileObject.SharedInt;
import Server.ServerBuilder;
import com.jcraft.jsch.*;

import java.util.List;
public class Start {

	public static void startClient(String user,String host,String password,String type,int id,int reqs,
								   String IP,String port,String rmi,String con) throws JSchException, IOException {
		// read/write  id nreqs ip port RMI/Socket
		SSHRunner runner = new SSHRunner();
		runner.connect(user,host,password);
		runner.setCommand("javac Client/*.java && java Client.Client "+type+" "+id+" "+reqs+" "+IP+" "+
				port+" "+rmi+" "+con+"\n");
		Thread nthread = new Thread(runner);

		nthread.start();

	}
	public  static  void startServer(String user,String host,String password,
									 String port,String readers,String writers, String  reqs,String Ip,String RMIPort,
									 String type) throws JSchException {
		// port readers writers reqs rmiport type
		SSHRunner runner = new SSHRunner();
		runner.connect(user,host,password);
		runner.setCommand("javac Server/*.java && java Server.Server "+port+" "+readers+" "+writers+" "+reqs+" "+Ip+" "+
				RMIPort+" "+type+"\n");
		Thread nthread = new Thread(runner);

		nthread.start();
	}
	public static void startClients(PropParser parser,String con) throws JSchException, IOException {
		List<String > readers = parser.getReaders();

		List<String > writers = parser.getWriters();
		List<String > readerPass = parser.getReaderPass();

		List<String > writerPass = parser.getWriterPass();
		String server = parser.getServerAddress();
		String []names = server.split("@");
		String serverAdd = names[1];
		String pass = parser.getServerPass();
		startServer(names[0],names[1],pass,parser.getServerPort(),parser.getNumReaders()+"",
				parser.getNumWriters()+"",parser.getRequests()+"",
				serverAdd,parser.getRegistry(),con);
		int n = 0;
		for(int i=0;i<readers.size();i++) {
			names = readers.get(i).split("@");
			pass = readerPass.get(i);
			startClient(names[0],names[1],pass,"Reader",n++,parser.getRequests(),
					serverAdd,parser.getServerPort(),parser.getRegistry(),con);
		}
		for(int i=0;i<writers.size();i++) {
			names = writers.get(i).split("@");
			pass = writerPass.get(i);
			startClient(names[0],names[1],pass,"Writer",n++,parser.getRequests(),
					serverAdd,parser.getServerPort(),parser.getRegistry(),con);
		}

	}
	public static void main(String[] args) throws IOException {
		Properties props = new Properties();
		props.load( Start.class.getClassLoader().getResourceAsStream("system.properties"));
		PropParser parser = new PropParser(props);

//
//		NewsServer server = builder.getServer();
//
//    	Thread serverThread = new Thread(server);
//		serverThread.start();
//		System.setProperty("java.security.policy","file:./test.policy");
//		System.setProperty("java.rmi.server.hostname",parser.getServerAddress());
//
//		if (System.getSecurityManager() == null) {
//			System.setSecurityManager(new SecurityManager());
//		}
//
//		Registry registr = LocateRegistry.createRegistry(registry);
//		registr.rebind("//localhost/MyServer",server);
//

		try {


			startClients(parser,"RMI");

		} catch (JSchException e1) {
			e1.printStackTrace();
		}
//		LocateRegistry.createRegistry(registry);
//		Naming.rebind("//localhost/MyServer",server);
//
//		System.out.println("help");
	}
}
