import java.awt.Transparency;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.awt.Image;
import javax.imageio.ImageIO;

public class SpriteCache extends ResourceCache implements ImageObserver
{
   

    protected Object loadResource(URL url){
        try {
            return ImageIO.read(url);
        }
        catch(Exception e){
            System.out.println("Przy otwieraniu "+url+"\n");
            System.out.println("Wystąpił błąd: "+ e.getClass().getName()+e.getMessage());
            System.exit(0);
            return null;
        }
        
        
        
        
    }
  
    public BufferedImage  createCompatible(int width, int height, int transparency)
    {
        
        GraphicsConfiguration gc =GraphicsEnvironment. getLocalGraphicsEnvironment(). getDefaultScreenDevice(). getDefaultConfiguration();
        
        BufferedImage compatible =  gc. createCompatibleImage(width,height,transparency);
        
        return compatible;
    
    }
    
    public BufferedImage  getSprite(String name) {
    
        BufferedImage loaded = (BufferedImage) getResource("img/"+name);
        
        BufferedImage compatible =createCompatible(loaded. getWidth(),loaded. getHeight(),Transparency.BITMASK);
        
        Graphics g = compatible. getGraphics();
        
        g. drawImage(loaded,0,0,this);
        
        return compatible;
    
    }
    
    public boolean imageUpdate(Image img, int infoflags,int x, int y, int w, int h) {
    
        return (infoflags & (ALLBITS|ABORT)) == 0;
    
    }
        
        
}
