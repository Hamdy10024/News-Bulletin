package Main.Server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;

import Main.FileObject.SharedObject;

public class NewsServer {

	private SharedObject<Integer> object;
	private String port_number;
	private int num_readers, num_writers;

	public NewsServer(String[] args) throws IOException {
		object = new SharedObject<Integer>();
		port_number = args[0];
		num_readers = Integer.parseInt(args[1]);
		num_writers = Integer.parseInt(args[2]);
	}

	/**
	 * Runs server functionality as stated in documents and writes output in
	 * outputfile.
	 * 
	 * @param outputFile
	 *            File stream to write results in.
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	public void start(FileOutputStream outputFile) throws NumberFormatException, IOException {
		ServerSocket server_socket = new ServerSocket(Integer.parseInt(port_number));
	}

	/**
	 * Runs server functionality as stated in documents and writes output in
	 * outputfile.
	 * 
	 * @param outputFile
	 *            File name to write results in.
	 */
	public void start(String outputFile) throws IOException {
		start(new FileOutputStream(outputFile));
	}

	/**
	 * Runs server functionality as stated in documents and writes output in
	 * outputfile with initial object value.
	 * 
	 * @param outputFile
	 *            File stream to write results in.
	 * @param initial
	 *            initial value
	 * @throws IOException
	 * @throws NumberFormatException
	 */
	public void start(FileOutputStream outputFile, Integer initial) throws NumberFormatException, IOException {
		object = new SharedObject<Integer>(initial);
		start(outputFile);

	}

	public void start(String outputFile, Integer initial) throws IOException {
		start(new FileOutputStream(outputFile), initial);
	}
}
