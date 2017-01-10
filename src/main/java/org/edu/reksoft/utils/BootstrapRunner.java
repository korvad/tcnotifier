package org.edu.reksoft.utils;

import org.edu.reksoft.rest.TeamCityClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapRunner implements CommandLineRunner {

    private static final String RUNNER_TAG = "[BOOTSTRAP RUNNER]";

    private static final Logger LOG = LoggerFactory.getLogger(BootstrapRunner.class);

    @Autowired
    private TeamCityClient teamCityClient;

    @Override
    public void run(String... args) throws Exception {
        LOG.info("{} STARTED.", RUNNER_TAG);
        LOG.info(String.format("Team City API Version: %s", teamCityClient.getVersion()));
    }
}
