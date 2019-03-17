package Main;

import Main.FileObject.SharedInt;
import Main.Server.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class Start {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket sock = new ServerSocket(9999);
        Socket nSocket = sock.accept();
        System.out.println("connected");
        Properties props = new Properties();
        props.setProperty("sSeq","1");
        props.setProperty("id","1");
        SharedInt obj = new SharedInt();
        SharedInt reads = new SharedInt(0);

        ClientHandler handler = new ClientHandler(props,nSocket,obj,reads);
        handler.start();
        handler.join();

        System.out.println(obj.read());
    }
}
