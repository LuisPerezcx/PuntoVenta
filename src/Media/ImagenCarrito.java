package Media;

import java.awt.*;
import java.net.URL;

public class ImagenCarrito extends Canvas {
    private final int x,y;
    public ImagenCarrito(int x, int y){
        this.x=x;
        this.y=y;
    }
    @Override
    public void paint(Graphics g){
        ClassLoader classLoader = getClass().getClassLoader();
        URL imageurl = classLoader.getResource("Media/Imagenes/carritoCompra.png");

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(imageurl);
        g.drawImage(image,x,y,this);
    }
}
