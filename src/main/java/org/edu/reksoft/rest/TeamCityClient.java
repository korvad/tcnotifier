package org.edu.reksoft.rest;

import org.edu.reksoft.json.build.Build;
import org.edu.reksoft.json.buildtype.BuildType;

import java.util.List;

public interface TeamCityClient {

    String VERSION_URL = "%s/app/rest/version";

    String BUILD_TYPES_URL = "%s/app/rest/buildTypes";

    String LAST_BUILD_STATUS_URL = "%s/app/rest/buildTypes/%s/builds?count=1";

    String getVersion();

    List<BuildType> getBuildTypes();

    Build getBuildStatus(String id);
}
