
/**
 * Write a description of class I2cAdapter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.ByteArrayInputStream;

import java.io.DataInputStream;
import java.io.IOException;



public class I2cAdapter
{
    // instance variables - replace the example below with your own
    private static final int i2cBus = 1;
    private static final int deviceAddr = 0x48;
   
    I2CBus bus;
    private I2CDevice device;
    
    private DataInputStream deviceCaliIn;
    private DataInputStream deviceIn;
    // Connections:
    // Photoresistor AIN3 Zworka P6
    private static final byte photoAddr= (byte)0x00;
    // Potentiometer AIN0 Zworka P5
    private static final byte potentAddr= (byte)0x03;
    // Thermistor AIN1 Zworka P4
    private static final byte thermoAddr = (byte)0x01;
    
    private byte inputAddress;
    /**
     * Constructor for objects of class I2cAdapter
     */
    public I2cAdapter(String input)
    {
        if(input.equals("PHOTO"))
        {
            inputAddress = (byte)0x00;
        }
        else if(input.equals("POTENT"))
        {
            inputAddress = (byte)0x03;
        }
        else if(input.equals("TERMO"))
        {
            inputAddress = (byte)0x01;
        }
        else{
            System.out.println("WRONG initialization!");
            return;
        }
        try{
            bus = I2CFactory.getInstance(I2CBus.BUS_1);
            System.out.println("System connected!");
            
            device = bus.getDevice(deviceAddr);
            System.out.println("Device connected!");
            
            Thread.sleep(200);
            
            getData();
        }
        catch(IOException e){
            System.out.println("Exception: "+e.getMessage());
        }
        catch (InterruptedException e){
            System.out.println("Interrupted Exception"+e.getMessage());
        }
        
        try{
            device.write(inputAddress);
        }
        catch(IOException e){
            System.out.println("IOEXception"+e.getMessage());
        }
    }

    
    public int getData() throws InterruptedException
    {   
      

        int result = 999;
        //Thread.sleep(300);
        try{
           
            device.read();
            result = device.read();
        }
        catch(IOException e){
            System.out.println("IOException"+e.getMessage());
        }
        
        return result;
    }
    
     public void getString() 
    {   
             new Thread ( new Runnable(){
          public void run() {
                Thread.sleep(200);
                int result = -1;
                           
                try{
                   
                    device.read();
                    result = device.read();
                    System.out.println("Wynik to: "+result+"\n");
                }
                catch(IOException e){
                    System.out.println("IOException"+e.getMessage());
                }
        
            }
        });
    }
    
}
    