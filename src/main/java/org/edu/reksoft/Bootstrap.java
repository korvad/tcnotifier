package org.edu.reksoft;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Bootstrap {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(Bootstrap.class)
                .bannerMode(Banner.Mode.OFF)
                .logStartupInfo(false)
                .registerShutdownHook(true)
                .run(args);
    }
}
