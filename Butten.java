package mines;

import javafx.scene.control.Button;

public class Butten extends Button{
    private int x,y;
    public Butten(String str, int x,int y){
        super(str);
        this.x=x;
        this.y=y;
    }
    public int getx(){
        return this.x;
    }
    public int gety(){
        return this.y;
    }

}