import greenfoot.*;

/**
 * An ant that collects food.
 * 
 * @author Michael Kölling
 * @version 0.1
 */
public class Ant extends Creature
{
    private boolean carryingFood = false;
    
    private GreenfootImage image1;
    
    private GreenfootImage image2;
    
    private final int MAX_PH_AVAILABLE = 16;
    
    private final int TIME_FOLLOWING_TRAIL = 30;
    
    private int phAvailable = MAX_PH_AVAILABLE;
    
    private int followTrialTimeRemaining = 0; 
    
   /**
     * Create an ant with a given home hill. The initial speed is zero (not moving).
     */
   public Ant(AntHill home)
    {
        setHomeHill(home);
        image1 = getImage(); 
        image2 = new GreenfootImage("ant-with-food.gif");
    }

   private void handlePheromoneDrop()
   {
        if(MAX_PH_AVAILABLE == 16)
        {
            Pheromones ph = new Pheromones();
            getWorld().addObject(ph, getX(), getY());
            phAvailable = 0;
        }
        else 
        {
            phAvailable++; 
        }
    }
    
   private boolean smellsPheromone()
   {
       if(isTouching(Pheromones.class))
       {
           return true;
       }
       else
       {
           return false;
       }
    }
    
   private void walkTowardsPheromoneCenter()
    {
        Pheromones pheromones = (Pheromones) getOneIntersectingObject(Pheromones.class);
        if (pheromones != null) 
        {
        headTowards(pheromones);   
           if(getX() == pheromones.getX() && getY() == pheromones.getY())
        {
            followTrialTimeRemaining = TIME_FOLLOWING_TRAIL;
        }
    }
   }
   /**
     * Do what an ant's gotta do.
      */
      public void act()
    {
      status();// This currently does not do anything
    }
    
   private void searchForFood()
    {
        if(followTrialTimeRemaining == 0)
        {
        walkTowardsPheromoneCenter();// if ants smells a pheromone, walk toward center of the pheromone droplet
        randomWalk();// otherwise walk around randomly
        }
       else
        {
        followTrialTimeRemaining--; // decrement time remaining
        walkAwayFromHome();// walk away from home
        }
        checkForFood();
    }
    
   private boolean atHome()
    {
        if (getHomeHill() != null) {
            return (Math.abs(getX() - getHomeHill().getX()) < 4) && 
                   (Math.abs(getY() - getHomeHill().getY()) < 4);
        }
        else {
            return false;
        }
    }
    
     private void status()
    {
         if (carryingFood == true) 
        {
            handlePheromoneDrop();
            walkTowardsHome();
            if(atHome())
            {
                setImage(image1);
                carryingFood = false;
                getHomeHill().countFood();
            }
        }
        else
        {
            searchForFood();
       }
   }
    
   private void checkForFood()
    {
        Food food = (Food) getOneIntersectingObject(Food.class);
        if (food != null) 
        {
            carryingFood = true;
            food.removeCrumb();
            setImage(image2);
        }
        
    }
}