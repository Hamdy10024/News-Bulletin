package Main.FileObject;

/**
 * Shared synchronizable Object
 */
public class SharedObject<T> {
    /**
     * Synced Object
     */
    protected T ObVal;

    public SharedObject(){

    }

    public SharedObject(T init) {
        this.ObVal = init;
    }

    public T read(){
        T value;
        synchronized (this) {
            value = ObVal;
        }
        return  value;

    }

    protected void update(T val){
        ObVal = val;
    }
    public void write(T value) {
        synchronized (this) {
            this.update(value);
        }
    }

}
