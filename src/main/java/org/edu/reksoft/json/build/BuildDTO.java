package org.edu.reksoft.json.build;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class BuildDTO implements Serializable {

    private static final long serialVersionUID = -8692459085580691718L;

    private Integer count;

    private String nextHref;

    @JsonProperty(value = "build")
    private List<Build> statuses;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNextHref() {
        return nextHref;
    }

    public void setNextHref(String nextHref) {
        this.nextHref = nextHref;
    }

    public List<Build> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Build> statuses) {
        this.statuses = statuses;
    }
}
