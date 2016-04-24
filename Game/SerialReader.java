import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataListener;
import com.pi4j.io.serial.SerialFactory;
import com.pi4j.io.serial.SerialPortException;

public class SerialReader {
    // instance variables - replace the example below with your own
    final Serial serial;

    /**
     * Constructor for objects of class SerialReader
     */
    public SerialReader()throws InterruptedException{ 
        serial = SerialFactory.createInstance();
        serial.open("/dev/ttyACM0", 9600);
        Thread.sleep(200);
        
    }
    
    public void start(){
        serial.write("start\n");      
    }
   
    public void stop(){
        serial.write("stop\n");
    }
}