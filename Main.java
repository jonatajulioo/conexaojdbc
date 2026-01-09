import java.util.List;

public class Main {

    public static void main(String[] args) {
        UsuarioDAO dao = new UsuarioDAO();

        Usuario novo = new Usuario("Ana Maria", "ana@email.com");
        dao.criar(novo);

        List<Usuario> lista = dao.listar();
        lista.forEach(System.out::println);

        if (!lista.isEmpty()) {
            Usuario u = lista.get(0);
            u.setNome("Ana Maria Silva");
            dao.atualizar(u);
        }

        if (!lista.isEmpty()) {
            dao.excluir(lista.get(0).getId());
        }
    }

}
