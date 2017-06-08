import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientConrtroll extends Remote {

    String sayHi() throws RemoteException;
}
