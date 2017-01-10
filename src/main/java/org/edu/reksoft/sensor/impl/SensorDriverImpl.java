package org.edu.reksoft.sensor.impl;

import org.ardulink.core.Link;
import org.ardulink.core.serial.rxtx.SerialLinkConfig;
import org.ardulink.core.serial.rxtx.SerialLinkFactory;
import org.edu.reksoft.json.build.BuildStatus;
import org.edu.reksoft.sensor.SensorDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.lang.reflect.Field;

@Component
public class SensorDriverImpl implements SensorDriver {

    private static final Logger LOG = LoggerFactory.getLogger(SensorDriverImpl.class);


    @Value("${rxtx.library.path}")
    private String rxtxPath;

    private Link link;

    @PostConstruct
    public void init() {
        try {
            LOG.info("Trying to connect to arduino at {} ...", RXTX_DEVICE);
            setRxTxLibraryPath();
            System.setProperty(RXTX_PROPERTY_NAME, RXTX_DEVICE);
            SerialLinkFactory factory = new SerialLinkFactory();
            SerialLinkConfig config = factory.newLinkConfig();
            config.setPort(RXTX_DEVICE);
            config.setPingprobe(false);
            link = factory.newLink(config);
            LOG.info("Successfully connect to arduino at {}.", RXTX_DEVICE);
            link.sendCustomMessage(ARDUINO_START);
        } catch (Exception e) {
            LOG.error("Program aborted, cause can't connect to arduino: {}.", e);
            halt("Program aborted, cause can't connect to arduino");
        }
    }

    @PreDestroy
    public void destroy() {
        switchOffPins();
    }

    @Override
    public void changeState(BuildStatus status) throws IOException {
        switch (status) {
            case SUCCESS:
                link.sendCustomMessage(TEAM_CITY_BUILD_SUCCESS);
                break;

            case FAILURE:
                link.sendCustomMessage(TEAM_CITY_BUILD_FAIL);
                break;
        }
    }

    private void switchOffPins() {
        try {
            link.sendCustomMessage(ARDUINO_HALT);
        } catch (IOException e) {
            LOG.error("Program aborted cause error occured while changing states on board: {}.", e);
            halt("Program aborted cause error occured while changing states on board");
        }
    }

    private void halt(String msg) {
        throw new RuntimeException(msg);
    }

    private void setRxTxLibraryPath() throws NoSuchFieldException, IllegalAccessException {
        //dirty hack
        System.setProperty(JAVA_LIBRARY_PATH, rxtxPath);
        Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
        fieldSysPath.setAccessible(true);
        fieldSysPath.set(null, null);
    }
}
