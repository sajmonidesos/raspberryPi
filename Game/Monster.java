
public class Monster extends Actor
{
    protected int vX;

    
    public Monster(Stage stage){
        super(stage);
        setSpriteName("fighter");
    }
    
    public void act(){
        posX += vX;
        if(posX <0 || posX>Stage.width-50) vX = -vX;
        
    }
    public int getVx(){
        return vX;
    }
    public void setVx(int newVx){
        this.vX = newVx;
    }
}
