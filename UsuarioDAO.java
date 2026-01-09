import java.sql.*;
import java.util.*;

public class UsuarioDAO {
    public void criar(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome, email) VALUES (?, ?)";
        try (Connection conn = Conexao.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.executeUpdate();
            System.out.println("Usuario cadastrado com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (Connection conn = Conexao.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario u = new Usuario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("email"));
                    usuarios.add(u);
                }

            } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
    public void atualizar(Usuario usuario) {
        String sql= "UPDATE usuario SET nome = ?, email = ?, WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setInt(3, usuario.getId());
            stmt.executeUpdate();
            System.out.println("Usuario atualizado com sucesso.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void excluir(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Usuario excluido com sucesso");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
