package com.amhzing.activities.ui.web.client.angular.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

@JsonInclude
public class Access implements Serializable {

    @JsonProperty("includeFacilitatedActivity")
    private final boolean includeFacilitatedActivity;

    @JsonProperty("includeHostedActivity")
    private final boolean includeHostedActivity;

    private Access(final boolean includeFacilitatedActivity, final boolean includeHostedActivity) {
        this.includeFacilitatedActivity = includeFacilitatedActivity;
        this.includeHostedActivity = includeHostedActivity;
    }

    public static Access create(final boolean includeFacilitatedActivity, final boolean includeHostedActivity) {
        return new Access(includeFacilitatedActivity, includeHostedActivity);
    }

    public boolean isIncludeFacilitatedActivity() {
        return includeFacilitatedActivity;
    }

    public boolean isIncludeHostedActivity() {
        return includeHostedActivity;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Access access = (Access) o;
        return includeFacilitatedActivity == access.includeFacilitatedActivity &&
                includeHostedActivity == access.includeHostedActivity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(includeFacilitatedActivity, includeHostedActivity);
    }

    @Override
    public String toString() {
        return "Access{" +
                "includeFacilitatedActivity=" + includeFacilitatedActivity +
                ", includeHostedActivity=" + includeHostedActivity +
                '}';
    }
}
