package org.edu.reksoft.sensor.impl;


import org.ardulink.core.Link;
import org.ardulink.core.Pin;
import org.ardulink.core.serial.rxtx.SerialLinkConfig;
import org.ardulink.core.serial.rxtx.SerialLinkFactory;
import org.edu.reksoft.sensor.SensorDriver;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@RunWith(BlockJUnit4ClassRunner.class)
public class SensorDriverImplTest {

    @BeforeClass
    public static void setUp() throws Exception {
        System.setProperty(
                SensorDriver.RXTX_PROPERTY_NAME,
                SensorDriver.RXTX_DEVICE
        );
    }

    @org.junit.Test
    public void testChangeState() throws Exception {
        SerialLinkFactory factory = new SerialLinkFactory();
        SerialLinkConfig config = factory.newLinkConfig();
        config.setPort(SensorDriver.RXTX_DEVICE);
        config.setPingprobe(false);
        Link link = factory.newLink(config);

        Pin.DigitalPin pin = Pin.digitalPin(12);
        boolean power = true;
        while (true) {
            System.out.println("Send power:" + power);
            link.switchDigitalPin(pin, power);
            power = !power;
            TimeUnit.SECONDS.sleep(2);
        }
    }
}