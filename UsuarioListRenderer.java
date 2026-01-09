import javax.swing.*;
import java.awt.*;

public class UsuarioListRenderer extends JLabel implements ListCellRenderer<Usuario> {

    public UsuarioListRenderer() {
        setOpaque(true); // obrigatório para pintar fundo
    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends Usuario> list,
            Usuario usuario,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {

        // Texto que vai aparecer na lista
        setText(usuario.getId() + " - " + usuario.getNome());

        // Cores quando selecionado
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}
