import javax.swing.*;
import java.awt.*;

public class Deletar extends JPanel {
    private UsuarioDAO dao = new UsuarioDAO();
    private DefaultListModel<Usuario> model;
    private JList<Usuario> listaUsuarios;

    private void atualizarLista() {
        model.clear();
        for (Usuario u : dao.listar()) {
            model.addElement(u);
        }
    }

    public Deletar(JPanel container) {

        setLayout(null);

        model = new DefaultListModel<>();
        listaUsuarios = new JList<>(model);
        listaUsuarios.setCellRenderer(new UsuarioListRenderer());
        listaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(listaUsuarios);
        scroll.setBounds(40, 140, 200, 100);

        atualizarLista();


        JButton atualizar = new JButton("Atualizar");
        atualizar.setBounds(100, 60, 100, 30);
        atualizar.setBorder(null);
        atualizar.setBackground(Color.lightGray);
        atualizar.addActionListener( e -> {
            atualizarLista();
        });

        // Texto usuários
        JLabel usuarios = new JLabel("Usuarios:");
        usuarios.setBounds(100, 100, 100, 50);
        usuarios.setFont(new Font("Arial", Font.BOLD, 20));

        // Botão deletar
        JButton enviar = new JButton("Deletar");
        enviar.setBounds(40, 260, 200, 30);
        enviar.setBackground(Color.green);
        enviar.setBorder(null);
        enviar.addActionListener(e -> {
            Usuario selecionado = listaUsuarios.getSelectedValue();

            if (selecionado != null) {
                dao.excluir(selecionado.getId());
                atualizarLista();

                JOptionPane.showMessageDialog(
                        null,
                        "Usuário removido com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Selecione um usuário!",
                        "ERRO",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Botão voltar
        JButton btnVoltar = new JButton("Ir para cadastrar");
        btnVoltar.setBounds(85, 305, 100, 30);
        btnVoltar.setBackground(Color.red);
        btnVoltar.setBorder(null);

        btnVoltar.addActionListener(e -> {
            CardLayout cl = (CardLayout) container.getLayout();
            cl.show(container, "cadastro");
            atualizarLista(); // Atualiza ao voltar
        });

        // Adiciona componentes
        add(usuarios);
        add(scroll);
        add(enviar);
        add(btnVoltar);
        add(atualizar);

        setVisible(true);
    }
}
