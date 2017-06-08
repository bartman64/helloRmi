import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Hello {

    private final Registry registry;

    public Server(Registry registry){
        this.registry = registry;
    }

    public String sayHello() {
        return "Hello, world!";
    }

    @Override
    public String proxyBind(String name, Remote obj) throws RemoteException {
        String res = "Registered" + name +" successfully!";
        Runnable bindToRegistry = () -> {
            try {
                registry.bind(name, obj);
            } catch (RemoteException | AlreadyBoundException e) {
                e.printStackTrace();
            }
        };

        Thread binder = new Thread(bindToRegistry);

        binder.start();

        try {
            binder.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static void main(String[] args) {
        try{
            Registry registry = LocateRegistry.createRegistry(1099);
            Server obj = new Server(registry);
            Hello stub = (Hello)  UnicastRemoteObject.exportObject(obj, 0);

            registry.bind("Hello", stub);
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
