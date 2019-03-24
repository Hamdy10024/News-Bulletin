package Main.FileObject;

public class SharedInt extends SharedObject<Integer> {

    public SharedInt() {
        super();
        ObVal = -1;
    }
    public SharedInt(Integer a) {
        super(a);
    }
    public Integer Increment() {
        Integer ret;
        synchronized (this) {
            ObVal++;
            ret= ObVal;
        }
        return  ret;
    }

    public Integer Decrement(){
        Integer ret;
        synchronized (this) {
            ObVal --;
            ret = ObVal;
        }
        return  ret;
    }
}
