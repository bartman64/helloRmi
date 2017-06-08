import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Client implements ClientConrtroll
{
    private Client() {}

    public static void main(String[] args) {
        String host = (args.length < 1) ? null :args[0];
        try{
            Registry registry = LocateRegistry.getRegistry("10.28.9.3", 1099);
            Hello stub = (Hello) registry.lookup("Hello");
            String response = stub.sayHello();
            final Client client = new Client();
            final ClientConrtroll conrtroll = (ClientConrtroll) UnicastRemoteObject.exportObject(client, 0);
            stub.proxyBind("client" , conrtroll);
            System.out.println("response: " +response);
        } catch (Exception e) {
            System.out.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public String sayHi() throws RemoteException {
        return "hi";
    }
}
