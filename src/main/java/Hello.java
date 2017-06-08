import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote{
    String sayHello() throws RemoteException;

    String proxyBind(String name, Remote obj) throws RemoteException;
}
