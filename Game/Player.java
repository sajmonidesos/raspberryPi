import java.awt.event.KeyEvent;

public class Player extends Actor{
   
    protected static final int PLAYER_SPEED = 4;
    protected int vX;
    protected int vY;
    private boolean up,down,left,right;
    private I2cAdapter sensor;
    
    public Player(Stage stage)
    {   
        super(stage);
        setSpriteName("X-wing");
        sensor = new I2cAdapter("POTENT");
    }

    public void act(){
        super.act();
        posX += vX;
        posY += vY;
    
        if (posX < 0 || posX > Stage.width-50 )
            vX = -vX;
        if (posY < 0 || posY > Stage.height-50 )
            vY = -vY;
            
    }
    
    public int getVx(){
        return vX;
    }
    public void setVx(int newVx){
        this.vX = newVx;
    }
    public int getVy(){
        return vY;
    }
    public void setVy(int newVy){
        this.vY = newVy;
    }
    public void updateSpeed(){
        vX = 0;
        vY = 0;
        if (down) vY = PLAYER_SPEED;
        if (left) vX = -PLAYER_SPEED;
        if (up) vY = -PLAYER_SPEED;
        if (right) vX = PLAYER_SPEED;
           
    }
    public void keyReleased ( KeyEvent e){
        
        switch(e.getKeyCode()){
            case KeyEvent.VK_DOWN : down = false ; break;
            case KeyEvent.VK_UP : up = false ; break;            
            case KeyEvent.VK_LEFT : left = false ; break;
            case KeyEvent.VK_RIGHT : right = false ; break;
            
        }
        updateSpeed();    
            
    }
    public void keyPressed ( KeyEvent e){
        
        switch(e.getKeyCode()){
            case KeyEvent.VK_DOWN : down = true ; break;
            case KeyEvent.VK_UP : up = true ; break;            
            case KeyEvent.VK_LEFT : left = true ; break;
            case KeyEvent.VK_RIGHT : right = true ; break;
            
        }
        updateSpeed();    
            
    }
    public int sensorDirectionUpdate (){
        
        int result=128;
        
         try{
          result = sensor.getData();
          
        }
       catch (InterruptedException e){
           System.out.println("błąd czunika");
           
        }
        if (result >150)     left = true;
        if(result < 150)    left  = false;
        if(result > 100)    right = false;
        if (result <100)   right = true;
        updateSpeed();     
        return result;
    }
}
