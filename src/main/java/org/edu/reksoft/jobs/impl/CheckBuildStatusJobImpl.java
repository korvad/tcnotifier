package org.edu.reksoft.jobs.impl;

import org.apache.commons.collections.CollectionUtils;
import org.edu.reksoft.jobs.CheckBuildStatusJob;
import org.edu.reksoft.json.build.Build;
import org.edu.reksoft.json.build.BuildStatus;
import org.edu.reksoft.json.buildtype.BuildType;
import org.edu.reksoft.rest.TeamCityClient;
import org.edu.reksoft.sensor.SensorDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CheckBuildStatusJobImpl implements CheckBuildStatusJob {

    private static final Logger LOG = LoggerFactory.getLogger(CheckBuildStatusJobImpl.class);

    @Autowired
    private TeamCityClient teamCityClient;

    @Autowired
    private SensorDriver sensorDriver;

    @Override
    @Scheduled(cron = "${check.build.status.cron.job}")
    public void checkStatuses() throws IOException {
        List<String> failedBuildNames = new ArrayList<>();
        List<BuildType> buildTypes = teamCityClient.getBuildTypes();
        for (BuildType buildType : buildTypes) {
            Build build = teamCityClient.getBuildStatus(buildType.getId());
            if (build != null && BuildStatus.FAILURE.equals(build.getStatus())) {
                BuildType buildTypeFailed = buildTypes.stream().filter(
                        bt -> bt.getId().equals(build.getBuildTypeId())
                ).findFirst().orElse(null);
                if (buildTypeFailed != null) {
                    failedBuildNames.add(buildTypeFailed.getName());
                }
            }
        }
        if (CollectionUtils.isNotEmpty(failedBuildNames)) {
            failedBuildNames.forEach(s -> LOG.error("Build [{}] failure ...", s));
            sensorDriver.changeState(BuildStatus.FAILURE);
        } else {
            sensorDriver.changeState(BuildStatus.SUCCESS);
        }
    }
}
