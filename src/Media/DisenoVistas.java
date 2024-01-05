package Media;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class DisenoVistas {
    public JButton imagenBoton(String nombre,String path,int ancho, int alto){
        ClassLoader classLoader = getClass().getClassLoader();
        URL imgUrl = classLoader.getResource(path);
        ImageIcon icon = new ImageIcon(imgUrl);
        Image imgResize = icon.getImage().getScaledInstance(ancho,alto,Image.SCALE_SMOOTH);
        ImageIcon iconResize = new ImageIcon(imgResize);

        return new JButton(nombre,iconResize);
    }
}
