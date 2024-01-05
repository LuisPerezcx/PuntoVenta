package Media;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

public class MiImagen extends Canvas {
    private final int x, y;

    public MiImagen(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void paint(Graphics g) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL imageUrl = classLoader.getResource("Media/Imagenes/perfil.png");

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(imageUrl);
        g.drawImage(image, x, y, this);
    }
}
