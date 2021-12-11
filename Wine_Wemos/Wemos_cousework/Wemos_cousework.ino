#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <microDS18B20.h>
#include <NewPing.h>
#include <FS.h>


#define TRIGGER_PIN  5
#define ECHO_PIN     4

NewPing sonar(TRIGGER_PIN, ECHO_PIN, 400);

const char* ssid = "Wine";
const char* password = "11111111";

MicroDS18B20<2> sensor1;
float temp;
float dist;
float c;

WiFiServer server(80);


void setup() {
  
//  dht.begin();
  Serial.begin(115200);
  delay(10);
  
  WiFi.softAP(ssid, password);
  IPAddress myIP = WiFi.softAPIP();
  Serial.print("AP IP address: ");
  Serial.print(myIP);
  
  // Start the server
  server.begin();
  Serial.println("Server started");

   if(SPIFFS.begin())
  {
    Serial.println("SPIFFS Initialize....ok");
  }
  else
  {
    Serial.println("SPIFFS Initialization...failed");
  }
}

void loop() {
  WiFiClient client = server.available();
  if (!client) {
    return;
  }
  
  // Wait until the client sends some data
  Serial.println("new client");
  while(!client.available()){
    delay(1);
  }
  
   // Read the first line of the request
  String req = client.readStringUntil('\r');
  Serial.println(req);
  // Match the request
  controller(req, client);
  client.flush(); 
  // Prepare the response
  String s = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";
  s += dist;
  // Send the response to the client
  client.print(s);
  delay(1);
  Serial.println("Client disonnected");

  // The client will actually be disconnected   // when the function returns and 'client' object is detroyed
}

void controller(String req,  WiFiClient client){
  if (req.indexOf("/distance") != -1){ 
    dist = getDistance();
    int pos1 = req.indexOf("-");
    String data1 = "";
    for(int i =pos1+1;i<req.length();i++){
      data1+=req[i];
      }
      data1.replace("-", " ");
      Serial.println("Data: "+data1);
    saveData();
    return;
  }
  else if(req.indexOf("/temperature") != -1){
    sensor1.requestTemp();
    dist = sensor1.getTemp();
    return;
  }
  else if(req.indexOf("/velocity") != -1){
    dist = getVelocity();
    return; 
  }
  else {
    Serial.println("invalid request");
    client.stop();
  return;
  }
}

float getDistance(){
  float duration, cm; // назначаем переменную "cm" и "duration" для показаний датчика
  digitalWrite(TRIGGER_PIN, LOW); // изначально датчик не посылает сигнал
  delayMicroseconds(5); // ставим задержку в 2 ммикросекунд

  digitalWrite(TRIGGER_PIN, HIGH); // посылаем сигнал
  delayMicroseconds(10); // ставим задержку в 10 микросекунд
  digitalWrite(TRIGGER_PIN, LOW); // выключаем сигнал

  duration = pulseIn(ECHO_PIN, HIGH); // включаем прием сигнала

  cm = (duration * (getTemperature()*6/10+330))/2000; // вычисляем расстояние в сантиметрах
  return cm;
}

float getVelocity(){
  return sqrt(1.4*8.31*getTemperature()/(0.029));
}

float getTemperature(){
  sensor1.requestTemp();
  return sensor1.getTemp();
}

void saveData(){
  File f = SPIFFS.open("history.txt", "a");
  
  if (!f) {
    Serial.println("file open failed");
  }
  else
  {
      //Write data to file
      Serial.println("Writing Data to File");
      f.print(String(getVelocity(),3));
      f.close();  //Close file
  }
  f = SPIFFS.open("history.txt", "r");
  if (!f) {
    Serial.println("file open failed");
  }
  else
  {
      Serial.println("Reading Data from File:");
      //Data from file
      for(int i=0;i<f.size();i++) //Read upto complete file size
      {
        Serial.print((char)f.read());
      }
      f.close();  //Close file
      Serial.println("File Closed");
  }
  }
