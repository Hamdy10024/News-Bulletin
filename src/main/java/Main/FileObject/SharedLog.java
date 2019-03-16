package Main.FileObject;
import java.util.ArrayList;
import java.util.List;
public class SharedLog extends SharedObject<String> {
    private List<String> logs;
    private String headers;
    public SharedLog(String headers) {
        logs = new ArrayList<String>();
        this.headers=headers;
        ObVal = this.headers;
    }
    @Override
    protected void update(String val) {
        logs.add(val);
    }

    public String exportLog() {

        StringBuilder res = new StringBuilder("");
        res.append(headers);
        res.append("\n");
        for(String rec:logs) {
            res.append(rec+"\n");
        }
        return res.toString();
    }
}
