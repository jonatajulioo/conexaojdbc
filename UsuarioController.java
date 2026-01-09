import com.sun.net.httpserver.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import com.google.gson.*;

public class UsuarioController implements HttpHandler {
    private final UsuarioDAO dao = new UsuarioDAO();
    private final Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String metodo = exchange.getRequestMethod();
        String caminho = exchange.getRequestURI().getPath();
        String resposta = "";
        int status = 200;

        try {
            if ("GET".equalsIgnoreCase(metodo)) {
                resposta = gson.toJson(dao.listar());
            } else if ("POST".equalsIgnoreCase(metodo)) {
                Usuario usuario = gson.fromJson(new InputStreamReader(exchange.getRequestBody()), Usuario.class);
                dao.criar(usuario);
                resposta = "Usuário criado.";
                status = 201;

            } else if ("PUT".equalsIgnoreCase(metodo)) {
                String[] partes = caminho.split("/");
                if (partes.length >= 3) {
                    int id = Integer.parseInt(partes[2]);
                    Usuario usuario = gson.fromJson(new InputStreamReader(exchange.getRequestBody()), Usuario.class);
                    usuario.setId(id);
                    dao.atualizar(usuario);
                    resposta = "Usuário Atualizado.";
                } else {
                    status = 400;
                    resposta = "ID não informado.";
                }
            } else if ("DELETE".equalsIgnoreCase(metodo)) {
                String[] partes = caminho.split("/");
                if (partes.length >= 3) {
                    int id = Integer.parseInt(partes[2]);
                    dao.excluir(id);
                    resposta = "Usuário deletado.";
                } else {
                    status = 400;
                    resposta = "ID não informado.";
                }
            } else {
                status = 405;
                resposta = "Método não permitido.";
            }
        } catch (Exception e) {
            status = 500;
            resposta = "Erro interno: " + e.getMessage();
            e.printStackTrace();
        }

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, resposta.getBytes(StandardCharsets.UTF_8).length);
        OutputStream os = exchange.getResponseBody();
        os.write(resposta.getBytes(StandardCharsets.UTF_8));
        os.close();
    }

}
