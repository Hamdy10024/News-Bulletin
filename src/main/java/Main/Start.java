package Main;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.rmi.Naming;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.util.Properties;

import Main.FileObject.SharedInt;
import Main.Server.NewsServer;
import Main.Server.ServerBuilder;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class Start {
	public static void main(String[] args) throws IOException {
		Properties props = new Properties();
		props.load( Start.class.getClassLoader().getResourceAsStream("system.properties"));
		PropParser parser = new PropParser(props);
		ServerBuilder builder = new ServerBuilder();
		builder.setCurReaders(new SharedInt(0));
		builder.setPortNumber(parser.getServerPort());
		builder.setNumReaders(parser.getNumReaders());
		builder.setNumWriters(parser.getNumWriters());
		builder.setMaxReqs(parser.getRequests());


//
		NewsServer server = builder.getServer();
//		Thread serverThread = new Thread(server);
//		serverThread.start();
		Naming.rebind("//localhost/MyServer",server);
		//System.out.println(stdout);
	}
}
