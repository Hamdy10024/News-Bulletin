package Client;

import java.rmi.registry.Registry;

public abstract class RemoteClient extends AbstractClient {

    public RemoteClient(Integer id) {
        super(id);

    }
}
