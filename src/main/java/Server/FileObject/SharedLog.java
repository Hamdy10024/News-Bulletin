package Server.FileObject;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class SharedLog extends SharedObject<String> {
    private List<String> logs;
    private String headers;
    FileWriter fStream;
    public SharedLog(String headers,String fName)  {
        logs = new ArrayList<String>();
        this.headers=headers;
        ObVal = this.headers;
        File file =  new File(fName);
        try {
            fStream = new FileWriter(file);
            fStream.write(headers);
        }catch (IOException e ) {

        }
    }
    @Override
    protected void update(String val) {
        logs.add(val);
        try {
            fStream.write(val);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String exportLog() {

        StringBuilder res = new StringBuilder("");
        res.append(headers);
        res.append("\n");
        for(String rec:logs) {
            res.append(rec+"\n");
        }
        try {
            fStream.close();

        } catch (IOException e) {
            e.printStackTrace();

        }
        return res.toString();
    }
}
