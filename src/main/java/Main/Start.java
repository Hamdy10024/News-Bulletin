package Main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import Main.Client.GenericClient;
import Main.Client.ReaderClient;
import Main.Client.WriterClient;
import Main.FileObject.SharedInt;
import Main.Server.NewsServer;
import Main.Server.ServerBuilder;

public class Start {
	public static void main(String[] args) throws IOException, InterruptedException {
		Properties props = new Properties();
		props.load( Start.class.getClassLoader().getResourceAsStream("system.properties"));
		PropParser parser = new PropParser(props);
		ServerBuilder builder = new ServerBuilder();
		builder.setCurReaders(new SharedInt(0));
		builder.setPortNumber(parser.getServerPort());
		builder.setNumReaders(parser.getNumReaders());
		builder.setNumWriters(parser.getNumWriters());
		builder.setMaxReqs(parser.getRequests());

		builder.setWriters(parser.getWriters());
		builder.setReaders(parser.getReaders());

		NewsServer server = builder.getServer();
		server.start();
		//
//		@Setter
//		private  List<String> readers;
//		@Setter
//		private List<String>writers;
//		Properties props = new Properties();
//		InputStream input = Start.class.getClassLoader().getResourceAsStream("system.properties");
//		props.load(input);
//		// String serverIP = props.getProperty("RW.server");
//		String serverPort = props.getProperty("RW.server.port");
//		Integer readersNum = Integer.parseInt(props.getProperty("RW.numberOfReaders"));
//		Integer writersNum = Integer.parseInt(props.getProperty("RW.numberOfWriters"));
//		// String accessNum = props.getProperty("RW.numberOfAccesses");
//		List<String> readers = new ArrayList<>();
//		List<String> writers = new ArrayList<>();
//		List<String> readersPasswords = new ArrayList<>();
//		List<String> writersPasswords = new ArrayList<>();
//		for (int i = 0; i < readersNum; i++) {
//			readers.add(props.getProperty("RW.reader" + i));
//		}
//		for (int i = 0; i < readersNum; i++) {
//			readersPasswords.add(props.getProperty("PW.reader" + i));
//		}
//		for (int i = 0; i < writersNum; i++) {
//			writers.add(props.getProperty("RW.writer" + i));
//		}
//		for (int i = 0; i < writersNum; i++) {
//			writersPasswords.add(props.getProperty("PW.writer" + i));
//		}
//
//		NewsServer newsServer = new NewsServer(serverPort, readersNum, writersNum);
//
//
//		newsServer.start();
//
//		List<GenericClient> clients = new ArrayList<>();
//
//		for (int i = 0; i < readers.size(); i++) {
//			String user = readers.get(i);
//			ReaderClient client = new ReaderClient(user.split("@")[1]);
//			clients.add(client);
//			client.execute();
//		}
//
//		for (int i = 0; i < writers.size(); i++) {
//			String user = writers.get(i);
//			;
//			GenericClient client = new WriterClient(user.split("@")[1], 0);
//			clients.add(client);
//			client.execute();
//		}
	}
}
