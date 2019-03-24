package Main.FileObject;

/**
 * Shared synchronizable Object
 */
public class SharedObject<T> {
    /**
     * Synced Object
     */
    protected T ObVal;
    protected int sseq;
    public SharedObject(){
        sseq= 0;
    }

    public SharedObject(T init) {
        this.ObVal = init;
    }

    public T read(){
        T value;
        synchronized (this) {
            value = ObVal;
            sseq ++;
        }
        return  value;

    }

    public Integer getsseq() {
        return sseq;
    }
    protected void update(T val){
        ObVal = val;
    }
    public void write(T value) {
        synchronized (this) {
            this.update(value);
            sseq ++;
        }
    }

}
