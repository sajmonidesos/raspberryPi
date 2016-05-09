  String inputString = "";
  boolean stringComplete = false;
  boolean transmit = false;
  float rad1 = 0.0;
  float rad2 = 0.5;
  float rad3 = 1;
  float rad4 = 1.5;
  double sin1; 
  double sin2;
  double sin3; 
  double sin4;
  

void setup()
{
  // start serial port at 9600 bps:
  Serial.begin(9600);
  inputString.reserve(200);
  Serial.println("ArduinoReady\n");

}

void loop()
{
    if (stringComplete){
      if (inputString ==("start\n")){
        transmit = true;
      }
      else if (inputString ==("stop\n")){
         transmit = false; 
      }
      else{
        Serial.println(inputString);
        
      }
       inputString=""; 
       stringComplete = false;
    }
  
    if (transmit){
      delay(200);
      
      // send sinusoid f(x) e [300,700]
      sin1 = (200 *( sin(rad1)+1))+300;
      sin2 = (200 *( sin(rad2)+1))+300;
      sin3 = (200 *( sin(rad3)+1))+300;
      sin4 = (200 *( sin(rad4)+1))+300;
      rad1 = rad1 + 0.1;
      rad2 = rad2 + 0.1;
      rad3 = rad3 + 0.1;
      rad4 = rad4 + 0.1;
     
      
      char TempString[10];
      dtostrf(sin1,2,0,TempString);
      String  sens1= String(TempString);
      dtostrf(sin2,2,0,TempString);
      String  sens2= String(TempString);
      dtostrf(sin3,2,0,TempString);
      String  sens3= String(TempString);
      dtostrf(sin4,2,0,TempString);
      String  sens4= String(TempString);
      
      Serial.println("$$"+sens1+","+sens2+","+sens3+","+sens4+"##\n");
      
     // Serial.println(sin1Result+"|"+sin2Result+"|"+sin3Result+"|"+sin4Result+"\n");
    }
}

void serialEvent() {
  while (Serial.available()) {
    // get the new byte:
    char inChar = (char)Serial.read(); 
    // add it to the inputString:
    inputString += inChar;
    // if the incoming character is a newline, set a flag
    // so the main loop can do something about it:
    if (inChar == '\n') {
      stringComplete = true;
    } 
  }
}


