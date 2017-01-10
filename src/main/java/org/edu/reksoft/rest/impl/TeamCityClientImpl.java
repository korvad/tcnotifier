package org.edu.reksoft.rest.impl;

import org.apache.commons.collections.CollectionUtils;
import org.edu.reksoft.json.build.Build;
import org.edu.reksoft.json.build.BuildDTO;
import org.edu.reksoft.json.buildtype.BuildType;
import org.edu.reksoft.json.buildtype.BuildTypeDTO;
import org.edu.reksoft.rest.TeamCityClient;
import org.edu.reksoft.utils.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class TeamCityClientImpl implements TeamCityClient {

    @Value("${teamcity.url}")
    private String teamCityUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String getVersion() {
        String url = UrlUtils.createUrl(VERSION_URL, teamCityUrl);
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        return result.getBody();
    }

    @Override
    public List<BuildType> getBuildTypes() {
        String url = UrlUtils.createUrl(BUILD_TYPES_URL, teamCityUrl);
        ResponseEntity<BuildTypeDTO> response = restTemplate.getForEntity(url, BuildTypeDTO.class);
        return response.getBody().getBuildTypes();
    }

    @Override
    public Build getBuildStatus(String id) {
        Build result = null;
        String url = UrlUtils.createUrl(LAST_BUILD_STATUS_URL, teamCityUrl, id);
        ResponseEntity<BuildDTO> response = restTemplate.getForEntity(url, BuildDTO.class);
        if (response.getBody() != null && CollectionUtils.isNotEmpty(response.getBody().getStatuses())) {
            result = response.getBody().getStatuses().get(0);
        }
        return result;
    }
}