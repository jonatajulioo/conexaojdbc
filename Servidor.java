import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
public class Servidor {
    public static void main(String[] args) throws IOException{
        HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);
        server.createContext("/usuarios", new UsuarioController());
        server.setExecutor(null);
        server.start();
        System.out.println("Servidor rodando em http://localhost:8001/usuarios");
    }
}
