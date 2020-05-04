import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;
/**
 * food for ants scattered across the world
 * 
 * @author Ava Jorgensen 
 * @version 5/3/20
 */
public class Food extends Actor
{
    private GreenfootImage image;
    private int crumbs = 155;
    private final int size = 30;
    
    public Food()
    {
        image = new GreenfootImage(size, size);
        updateImage();
        removeCrumb();
    }
    
    /**
     * Act - do whatever the Food wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        
    } 
    
    public void removeCrumb()
    {
        crumbs --;
        image.clear();
        if(crumbs == 0)
        {
            getWorld().removeObject(this);
        }
        else
        {
            updateImage();
        }
    }
    
    private void updateImage()
    {
       Random random = new Random();
       for(int i = 0; i < crumbs; i++)
       {
        int stDev = size / 6;
        int x = (int) (stDev * random.nextGaussian( ) + 3 * stDev);
        int y = (int) (stDev * random.nextGaussian( ) + 3 * stDev);    
        // keep crumbs in image
        if(x < 0) 
         {
           x = 0;
         }
        if(x >= size) 
         { 
           x = size - 1;
         }
        if(y < 0) 
         {
           y = 0;
         }
        if(y >= size) 
         {
           y = size - 1;
         }
        Color color = new Color(0, 0, 255);  // pick the color you want by replacing r, g, b with values.
        image.setColorAt(x, y, color);
       }
       setImage(image);
    }
    
}
