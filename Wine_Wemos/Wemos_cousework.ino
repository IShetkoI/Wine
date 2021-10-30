#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>
#include <NewPing.h>

#define TRIGGER_PIN  5
#define ECHO_PIN     4
#define MAX_DISTANCE 400

const char* ssid = "Wine";
const char* password = "11111111";

float temp = 0.0;
int dist = 0;

ESP8266WebServer server(80);

NewPing sonar(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE);

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
     dist = sonar.ping_cm();
     Serial.println("Showing temperature");
    }
    else if(req.indexOf("/temperature") != -1){
      
      }
      
      
    else {
    Serial.println("invalid request");
    client.stop();
    return;
  }
}
