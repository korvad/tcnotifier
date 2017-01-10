package org.edu.reksoft.json.buildtype;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class BuildTypeDTO implements Serializable {

    private static final long serialVersionUID = -8759959087697143497L;

    @JsonProperty(value = "buildType")
    private List<BuildType> buildTypes;

    public List<BuildType> getBuildTypes() {
        return buildTypes;
    }

    public void setBuildTypes(List<BuildType> buildTypes) {
        this.buildTypes = buildTypes;
    }
}
