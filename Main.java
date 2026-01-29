import java.util.List;

public class Main {

    public static void main(String[] args) {
        UsuarioDAO dao = new UsuarioDAO();

        Usuario novo = new Usuario("Ana Maria", "ana@email.com");
        dao.criar(novo);

        List<Usuario> lista = dao.listar();
        lista.forEach(System.out::println);

    }

}
