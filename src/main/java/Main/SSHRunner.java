package Main;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.*;
import java.util.Properties;

public class SSHRunner implements Runnable {
    private Session session;
    public void connect(String user,String host,String password ) throws JSchException {
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        JSch jsch = new JSch();
        // Create a JSch session to connect to the server
        session = jsch.getSession(user, host, 22);
        session.setPassword(password);
        session.setConfig(config);
        // Establish the connection
        session.connect();
    }
    public void exec(String Command) throws JSchException, IOException {
        Channel channel = session.openChannel("shell");
        channel.connect();

        OutputStream output = channel.getOutputStream();
        PrintStream printStream = new PrintStream(output, true);
        InputStream commandOutput = channel.getInputStream();

        printStream.println(Command);

        InputStreamReader inputReader = new InputStreamReader(commandOutput);
        BufferedReader bufferedReader = new BufferedReader(inputReader);
        String line ;

        while((line = bufferedReader.readLine()) != null){
            System.out.println(line);
        }
        bufferedReader.close();
        inputReader.close();
        channel.disconnect();

    }

    public void setCommand(String command) {
        this.command = command;
    }

    private
    String command;

    @Override
    public void run() {
        try{
            exec(command);
            disconnect();
        }catch (Exception e) {

        }
    }
    public void disconnect() {
        session.disconnect();
    }
}
