package org.edu.reksoft.sensor;

import org.edu.reksoft.json.build.BuildStatus;

import java.io.IOException;

public interface SensorDriver {

    String JAVA_LIBRARY_PATH = "java.library.path";
    String RXTX_PROPERTY_NAME = "gnu.io.rxtx.SerialPorts";
    String RXTX_DEVICE = "/dev/ttyACM0";
    String TEAM_CITY_BUILD_SUCCESS = "tbis";
    String TEAM_CITY_BUILD_FAIL = "tbif";
    String ARDUINO_START = "tbs";
    String ARDUINO_HALT = "tbh";

    void changeState(BuildStatus status) throws IOException;
}