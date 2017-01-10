#include "Arduino.h"

String inputString = "";         // a string to hold incoming data (this is general code you can reuse)
boolean stringComplete = false;  // whether the string is complete (this is general code you can reuse)

int a = 11;
int b = 7;
int c = 3;
int d = 5;
int e = 6;
int f = 10;
int g = 2;

void blink();

#define digitalPinListeningNum 14 // Change 14 if you have a different number of pins.
#define analogPinListeningNum 6 // Change 6 if you have a different number of pins.
boolean digitalPinListening[digitalPinListeningNum]; // Array used to know which pins on the Arduino must be listening.
boolean analogPinListening[analogPinListeningNum]; // Array used to know which pins on the Arduino must be listening.
int digitalPinListenedValue[digitalPinListeningNum]; // Array used to know which value is read last time.
int analogPinListenedValue[analogPinListeningNum]; // Array used to know which value is read last time.

void setup() {
    // initialize serial: (this is general code you can reuse)
    Serial.begin(115200);

    //set to false all listen variable
    int index = 0;
    for (index = 0; index < digitalPinListeningNum; index++) {
        digitalPinListening[index] = false;
        digitalPinListenedValue[index] = -1;
    }
    for (index = 0; index < analogPinListeningNum; index++) {
        analogPinListening[index] = false;
        analogPinListenedValue[index] = -1;
    }

    // Turn off everything (not on RXTX)
    for (index = 2; index < digitalPinListeningNum; index++) {
        pinMode(index, OUTPUT);
        digitalWrite(index, LOW);
    }

    pinMode(a, OUTPUT);
    pinMode(b, OUTPUT);
    pinMode(c, OUTPUT);
    pinMode(d, OUTPUT);
    pinMode(e, OUTPUT);
    pinMode(f, OUTPUT);
    pinMode(g, OUTPUT);
}

void turnOff() {
    digitalWrite(a, LOW);
    digitalWrite(b, LOW);
    digitalWrite(c, LOW);
    digitalWrite(d, LOW);
    digitalWrite(e, LOW);
    digitalWrite(f, LOW);
    digitalWrite(g, LOW);
}

void blinkDash() {
    digitalWrite(g, HIGH);
    delay(300);
    digitalWrite(g, LOW);
    delay(300);
    digitalWrite(g, HIGH);
    delay(300);
    digitalWrite(g, LOW);
    delay(300);
    digitalWrite(g, HIGH);
    delay(300);
    digitalWrite(g, LOW);
    delay(300);
}

void displaySuccess() {
    digitalWrite(a, HIGH);
    digitalWrite(f, HIGH);
    digitalWrite(g, HIGH);
    digitalWrite(c, HIGH);
    digitalWrite(d, HIGH);

}

void displayFailed() {
    digitalWrite(a, HIGH);
    digitalWrite(f, HIGH);
    digitalWrite(g, HIGH);
    digitalWrite(e, HIGH);
}

void loop() {
    // when a newline arrives:
    if (stringComplete) {

        if (inputString.startsWith("alp://")) { // OK is a message I know (this is general code you can reuse)

            boolean msgRecognized = true;

            if (inputString.substring(6, 10) == "kprs") { // KeyPressed
                // here you can write your own code. For instance the commented code change pin intensity if you press 'a' or 's'
                // take the command and change intensity on pin 11 this is needed just as example for this sketch
                //char commandChar = inputString.charAt(14);
                //if(commandChar == 'a' and intensity > 0) { // If press 'a' less intensity
                //  intensity--;
                //  analogWrite(11,intensity);
                //} else if(commandChar == 's' and intensity < 125) { // If press 's' more intensity
                //  intensity++;
                //  analogWrite(11,intensity);
                //}
            } else if (inputString.substring(6, 10) ==
                       "ppin") { // Power Pin Intensity (this is general code you can reuse)
                int separatorPosition = inputString.indexOf('/', 11);
                String pin = inputString.substring(11, separatorPosition);
                String intens = inputString.substring(separatorPosition + 1);
                pinMode(pin.toInt(), OUTPUT);
                analogWrite(pin.toInt(), intens.toInt());
            } else if (inputString.substring(6, 10) ==
                       "ppsw") { // Power Pin Switch (this is general code you can reuse)
                int separatorPosition = inputString.indexOf('/', 11);
                String pin = inputString.substring(11, separatorPosition);
                String power = inputString.substring(separatorPosition + 1);
                pinMode(pin.toInt(), OUTPUT);
                if (power.toInt() == 1) {
                    digitalWrite(pin.toInt(), HIGH);
                } else if (power.toInt() == 0) {
                    digitalWrite(pin.toInt(), LOW);
                }
            } else if (inputString.substring(6, 10) ==
                       "srld") { // Start Listen Digital Pin (this is general code you can reuse)
                String pin = inputString.substring(11);
                digitalPinListening[pin.toInt()] = true;
                digitalPinListenedValue[pin.toInt()] = -1; // Ensure a message back when start listen happens.
                pinMode(pin.toInt(), INPUT);
            } else if (inputString.substring(6, 10) ==
                       "spld") { // Stop Listen Digital Pin (this is general code you can reuse)
                String pin = inputString.substring(11);
                digitalPinListening[pin.toInt()] = false;
                digitalPinListenedValue[pin.toInt()] = -1; // Ensure a message back when start listen happens.
            } else if (inputString.substring(6, 10) ==
                       "srla") { // Start Listen Analog Pin (this is general code you can reuse)
                String pin = inputString.substring(11);
                analogPinListening[pin.toInt()] = true;
                analogPinListenedValue[pin.toInt()] = -1; // Ensure a message back when start listen happens.
            } else if (inputString.substring(6, 10) ==
                       "spla") { // Stop Listen Analog Pin (this is general code you can reuse)
                String pin = inputString.substring(11);
                analogPinListening[pin.toInt()] = false;
                analogPinListenedValue[pin.toInt()] = -1; // Ensure a message back when start listen happens.
            } else if (inputString.substring(6, 10) == "cust") {
                turnOff();
                if (inputString.substring(11, 15) == "tbif") {
                    blinkDash();
                    displayFailed();
                }
                if (inputString.substring(11, 15) == "tbis") {
                    blinkDash();
                    displaySuccess();
                }
                if (inputString.substring(11, 14) == "tbs") {
                    digitalWrite(g, HIGH);
                }
                if (inputString.substring(11, 14) == "tbh") {
                    digitalWrite(g, LOW);
                }
            } else {
                msgRecognized = false; // this sketch doesn't know other messages in this case command is ko (not ok)
            }

            // Prepare reply message if caller supply a message id (this is general code you can reuse)
            int idPosition = inputString.indexOf("?id=");
            if (idPosition != -1) {
                String id = inputString.substring(idPosition + 4);
                // print the reply
                Serial.print("alp://rply/");
                if (msgRecognized) { // this sketch doesn't know other messages in this case command is ko (not ok)
                    Serial.print("ok?id=");
                } else {
                    Serial.print("ko?id=");
                }
                Serial.print(id);
                Serial.write(255); // End of Message
                Serial.flush();
            }
        }

        // clear the string:
        inputString = "";
        stringComplete = false;
    }

    // Send listen messages
    int index = 0;
    for (index = 0; index < digitalPinListeningNum; index++) {
        if (digitalPinListening[index] == true) {
            int value = digitalRead(index);
            if (value != digitalPinListenedValue[index]) {
                digitalPinListenedValue[index] = value;
                Serial.print("alp://dred/");
                Serial.print(index);
                Serial.print("/");
                Serial.print(value);
                Serial.write(255); // End of Message
                Serial.flush();
            }
        }
    }

    for (index = 0; index < analogPinListeningNum; index++) {
        if (analogPinListening[index] == true) {
            int value = analogRead(index);
            if (value != analogPinListenedValue[index]) {
                analogPinListenedValue[index] = value;
                Serial.print("alp://ared/");
                Serial.print(index);
                Serial.print("/");
                Serial.print(value);
                Serial.write(255); // End of Message
                Serial.flush();
            }
        }
    }
}

/*
 SerialEvent occurs whenever a new data comes in the
 hardware serial RX.  This routine is run between each
 time loop() runs, so using delay inside loop can delay
 response.  Multiple bytes of data may be available.
 This is general code you can reuse.
 */
void serialEvent() {

    while (Serial.available() && !stringComplete) {
        // get the new byte:
        char inChar = (char) Serial.read();
        // add it to the inputString:
        inputString += inChar;
        // if the incoming character is a newline, set a flag
        // so the main loop can do something about it:
        if (inChar == '\n') {
            stringComplete = true;
        }
    }
}