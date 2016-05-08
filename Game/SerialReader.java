import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataListener;
import com.pi4j.io.serial.SerialFactory;
import com.pi4j.io.serial.SerialPortException;

import java.lang.StringBuilder;
import java.lang.String;

public class SerialReader {
    // instance variables - replace the example below with your own
    final Serial serial;
    StringBuilder line;
    boolean reading = false , sent = true;
    /**
     * Constructor for objects of class SerialReader
     */
    public SerialReader()throws InterruptedException{ 
        
        line = new StringBuilder();
        serial = SerialFactory.createInstance();
        try{
            serial.open("/dev/ttyACM0", 9600);
        }catch(SerialPortException e){
            try{
            serial.open("/dev/ttyAMA0", 9600);
            }
            catch (SerialPortException ex ){
                System.out.println("Error in establishing serial connection");
            }
        }
            Thread.sleep(300);
        serial.write("\nstart\n"); 
    }
    
    public void start(){
        serial.write("\nstart\n");      
    }
   
    public void stop(){
        serial.write("\nstop\n");
    }
    
    public String buildLine(String event2){
       //System.out.println("!!!"+event+"!!!");
       char [] event = event2.toCharArray();
       
       for (int i=0; i < event2.length(); i++){
            if (event[i] == '$'){
                line.setLength(0);
                reading = true;
                sent = false;
            }
            if (event[i] == '#'){
                reading = false;
            }
        
            if(reading){
                line.append(event[i]);
            
            }
            else if (!reading && !sent ){
                sent = true;
                return line.toString().substring(1,line.length());
            }
           
        }
       
       
        
        return ""; 
        
    }
}