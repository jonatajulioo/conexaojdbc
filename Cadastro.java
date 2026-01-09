

import javax.swing.*;
import java.awt.*;

public class Cadastro extends JPanel{
    private Deletar deletarPanel;


    public Cadastro(JPanel container) {
        setLayout(null);

        //usuario
        JLabel usuario = new JLabel();
        usuario.setBounds(100, 60, 100, 50);
        usuario.setFont(new Font("Arial", Font.BOLD, 20));
        usuario.setText("Usuário:");

        //campo do usuário
        JTextField usuarioCampo = new JTextField();
        usuarioCampo.setBounds(40,100, 200, 30);
        usuarioCampo.setText("");
        usuarioCampo.setBorder(null);

        //email
        JLabel email = new JLabel();
        email.setBounds(100, 140, 100, 50);
        email.setFont(new Font("Arial", Font.BOLD, 20));
        email.setText("E-mail:");

        //campo do email
        JTextField emailCampo = new JTextField(20);
        emailCampo.setBounds(40,180, 200, 30);
        emailCampo.setText("");
        emailCampo.setBorder(null);

        //btn enviar
        JButton enviar = new JButton();
        enviar.setBounds(40, 235, 200, 30);
        enviar.setBackground(Color.green);
        enviar.setText("Cadastrar");
        enviar.setBorder(null);
        enviar.addActionListener( e -> {
            if (!usuarioCampo.getText().isEmpty() && !emailCampo.getText().isEmpty()){

                System.out.printf("\nUsuario: %s \nEmail: %s " , usuarioCampo.getText() , emailCampo.getText());

                UsuarioDAO dao = new UsuarioDAO();

                Usuario novo = new Usuario(usuarioCampo.getText(), emailCampo.getText());
                dao.criar(novo);

                JOptionPane.showMessageDialog(
                        null,
                        "Usuário cadastrado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );

                usuarioCampo.setText("");
                emailCampo.setText("");
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "ERRO AO LOGAR",
                        "ERRO",
                        JOptionPane.ERROR_MESSAGE

                );
            }
        });

        JButton btnVoltar = new JButton("Ir para deletar");
        btnVoltar.setBounds(30, 240, 220, 30);
        btnVoltar.setBounds(85, 275, 100, 30);
        btnVoltar.setBackground(Color.red);
        btnVoltar.setBorder(null);

        btnVoltar.addActionListener(e -> {

            CardLayout cl = (CardLayout) container.getLayout();
            cl.show(container, "deletar");

        });


        add(usuario);
        add(usuarioCampo);
        add(btnVoltar);
        add(email);
        add(emailCampo);
        add(enviar);

        setVisible(true);
    }
}
