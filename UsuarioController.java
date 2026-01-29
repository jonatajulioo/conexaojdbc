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
        // 1. ADICIONE ESTES HEADERS CORS NO INÍCIO
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");
        exchange.getResponseHeaders().add("Access-Control-Allow-Credentials", "true");
        exchange.getResponseHeaders().add("Access-Control-Max-Age", "3600");

        // 2. LIDAR COM REQUISIÇÃO OPTIONS (PREFLIGHT)
        String metodo = exchange.getRequestMethod();
        if ("OPTIONS".equalsIgnoreCase(metodo)) {
            exchange.sendResponseHeaders(204, -1); // No Content para OPTIONS
            return;
        }

        String caminho = exchange.getRequestURI().getPath();
        String resposta = "";
        int status = 200;

        try {
            if ("GET".equalsIgnoreCase(metodo)) {
                resposta = gson.toJson(dao.listar());
            } else if ("POST".equalsIgnoreCase(metodo)) {
                Usuario usuario = gson.fromJson(new InputStreamReader(exchange.getRequestBody()), Usuario.class);
                dao.criar(usuario);
                resposta = "{\"mensagem\": \"Usuário criado.\"}";
                status = 201;

            } else if ("PUT".equalsIgnoreCase(metodo)) {
                String[] partes = caminho.split("/");
                if (partes.length >= 3) {
                    int id = Integer.parseInt(partes[2]);
                    Usuario usuario = gson.fromJson(new InputStreamReader(exchange.getRequestBody()), Usuario.class);
                    usuario.setId(id);
                    dao.atualizar(usuario);
                    resposta = "{\"mensagem\": \"Usuário atualizado.\"}";
                } else {
                    status = 400;
                    resposta = "{\"erro\": \"ID não informado.\"}";
                }
            } else if ("DELETE".equalsIgnoreCase(metodo)) {
                String[] partes = caminho.split("/");
                if (partes.length >= 3) {
                    int id = Integer.parseInt(partes[2]);
                    dao.excluir(id);
                    resposta = "{\"mensagem\": \"Usuário deletado.\"}";
                } else {
                    status = 400;
                    resposta = "{\"erro\": \"ID não informado.\"}";
                }
            } else {
                status = 405;
                resposta = "{\"erro\": \"Método não permitido.\"}";
            }
        } catch (NumberFormatException e) {
            status = 400;
            resposta = "{\"erro\": \"ID inválido.\"}";
        } catch (Exception e) {
            status = 500;
            resposta = "{\"erro\": \"Erro interno: " + e.getMessage().replace("\"", "'") + "\"}";
            e.printStackTrace();
        }

        // 3. MANTENHA O HEADER Content-Type
        exchange.getResponseHeaders().set("Content-Type", "application/json");

        // 4. ENVIE A RESPOSTA
        byte[] respostaBytes = resposta.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(status, respostaBytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(respostaBytes);
        }
    }
}