package Main.Server;

import java.io.IOException;
import java.net.ServerSocket;

import Main.FileObject.SharedObject;

public class NewsServer implements Runnable {

	private SharedObject<Integer> object;
	private String port_number;
	private int num_readers, num_writers;

	public NewsServer(String[] args) throws IOException {
		port_number = args[0];
		num_readers = Integer.parseInt(args[1]);
		num_writers = Integer.parseInt(args[2]);
		if (args.length > 2) {
			object = new SharedObject<Integer>(Integer.parseInt(args[3]));
		} else {
			object = new SharedObject<Integer>();
		}
	}

	@Override
	public void run() {
		ServerSocket server_socket = null;
		try {
			server_socket = new ServerSocket(Integer.parseInt(port_number));
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int clients = num_readers + num_writers;
		for (int i = 0; i < clients; i++) {
			ClientHandler h = null;
			try {
				h = new ClientHandler(null, server_socket.accept(), object, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			h.start();
		}
	}
}
