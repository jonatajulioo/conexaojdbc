import javax.swing.*;
import java.awt.*;

public class Tela {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Sistema");
        frame.setSize(300, 400);
        frame.setLocationRelativeTo(null); // centraliza na tela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel container = new JPanel(new CardLayout());

        container.add(new Cadastro(container), "cadastro");
        container.add(new Deletar(container), "deletar");
        container.setBackground(Color.lightGray);

        frame.add(container);

        frame.setVisible(true);

        CardLayout cl = (CardLayout) container.getLayout();
        cl.show(container, "cadastro");
    }
}
