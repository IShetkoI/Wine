#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <microDS18B20.h>
#include <NewPing.h>
#include <FS.h>


#define TRIGGER_PIN  5
#define ECHO_PIN     4

const char* ssid = "Wine";
const char* password = "11111111";
MicroDS18B20<2> sensor1;
NewPing sonar(TRIGGER_PIN, ECHO_PIN, 200);
float temp;
String dist;
float c;
float cm;

WiFiServer server(80);


void setup() {
    Serial.begin(115200);
    delay(10);

    WiFi.softAP(ssid, password);
    IPAddress myIP = WiFi.softAPIP();
    SPIFFS.begin();

    server.begin();
    pinMode(TRIGGER_PIN, OUTPUT); // trig выход
    pinMode(ECHO_PIN, INPUT);  // echo вход
}

void loop() {
    WiFiClient client = server.available();

    if (!client) {
        return;
    }
    
    // Wait until the client sends some data
    while(!client.available()){
        delay(1);
    }
    
     // Read the first line of the request
    String req = client.readStringUntil('\r');

    // Match the request
    controller(req, client);
    client.flush(); 
    // Prepare the response
    String s = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";
    s += dist;
    // Send the response to the client
    client.print(s);
    delay(1);
  // The client will actually be disconnected   // when the function returns and 'client' object is detroyed
}


void controller(String req,  WiFiClient client){
    if (req.indexOf("/distance") != -1){ 
        dist = getDistance(req);
        saveData(req, dist);
        return;
    }

    else if(req.indexOf("/history") != -1){
        dist = sendHistory();
        return; 
    }

    else {
        client.stop();
    return;
    }
}


String getDistance(String req){
    float middle;
    float dis[10];
    //=====================================================================//
    for(int i=0;i<10;i++){
      float us = sonar.ping_median(7, 250);
      if(us<=0.0){
        i--;
        continue;
      }
      dis[i]=(us * getVelocity() / 20000.0);
    }
    
    float temp;
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 9; j++) {
        if (dis[j] > dis[j + 1]) {
          temp = dis[j]; // создали дополнительную переменную
          dis[j] = dis[j + 1]; // меняем местами
          dis[j + 1] = temp; // значения элементов
        }
      }
    }
    
    float k, b;  
    float sumX = 0, sumY = 0, sumX2 = 0, sumXY = 0;  
    
    for (int i = 2; i < 8; i++) {    // для всех элементов массива
        sumX += i+1;
        sumY += (dis[i]);
        sumX2 += (i+1) * (i+1);
        sumXY += dis[i] * (i+1);
        Serial.print(" ");
        Serial.print(dis[i]);
    }
    k = (6 * sumXY - sumX * sumY)/(6 * sumX2-sumX * sumX);
    b = (sumY - k * sumX)/6;
    
    float value = k * 4 + b;

    float y1 = getMin(req);
    float y2 = getMax(req);

    if(y1>0.0 && y2>y1){
        k = (y2-y1)/(150-5);
        b = y1 - k*5;
        value = (value - b) / k;
        Serial.println(" ");
        Serial.println(k);
        Serial.println(b);
    }

    char cvalue[16];
    dtostrf(value, 3, 4, cvalue);

    return cvalue;
}

float getVelocity(){
    float temperature = getTemperature();
    
    float pressure = 0.0;
    float density = 0.0;
    float m = 0.0;
    float c = 0.0;
    
    if(temperature >= 25.0 && temperature < 30.0){
        density = 1.1839 + ( (1.1644-1.1839)/(30-25) ) * (temperature-25);
    }
    if(temperature >= 20.0 && temperature < 25.0){
        density = ((temperature-25)-(1.2041-1.1839))/(20-25)+1.1839;
    }
    if(temperature >= 15.0 && temperature < 20.0){
        density = ((temperature-20)-(1.225-1.2041))/(15-20)+1.2041;
    }

    pressure = density * 287 * (temperature + 273.15);

    m = density * 8.31447 * (temperature + 273.15)/pressure;

    c = sqrt( (1.4*8.31447*(temperature+273.15))/(m));
    
    return c;
}

float getTemperature(){
    sensor1.requestTemp();
    return sensor1.getTemp();
}


float getMin(String req){
    int pos = req.indexOf("/calibration-");
    int pos2 = req.indexOf(";");
    String smin = "";
    for(int i=pos+13;i<pos2;i++){
        smin+=req[i];
    }
    return smin.toFloat();
}

float getMax(String req){
    int pos = req.indexOf(";");
    int pos2 = req.indexOf(" HTTP/1.1");
    String smax = "";
    for(int i=pos+1;i<pos2;i++){
        smax+=req[i];
    }
    return smax.toFloat();
}



String sendHistory(){
    File f = SPIFFS.open("history.txt", "r");
    String history="";
    
    for(int i=0;i<f.size();i++){
        history += (char)f.read();
    }
    f.close();  //Close file
    return history;
}


void saveData(String req, String dist){
    int pos = req.indexOf("/date-");
    String date = "";
    for(int i=pos+6;i<pos+6+19;i++){
        date+=req[i];
    }
    date.replace("-", " ");

    pos = req.indexOf("/model-");
    String model = "";
    for(int i=pos+7;i<pos+7+21;i++){
        model+=req[i];
    }
    model.replace("%20", " ");

    File f = SPIFFS.open("history.txt", "a");
    
    if (!f) {}
    else{
        String text = date+"\n("+model+")\n"+dist+" cm\n\n";
        text.trim();
        f.print(text+"\n\n");
        f.close();  //Close file
    }
    f = SPIFFS.open("history.txt", "r");
    if (!f) {}
    else{
        //Data from file
        for(int i=0;i<f.size();i++){ //Read upto complete file size
        }
        f.close();  //Close file
    }
}
