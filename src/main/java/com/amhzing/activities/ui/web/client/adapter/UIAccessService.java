package com.amhzing.activities.ui.web.client.adapter;

import org.springframework.stereotype.Service;

import static com.amhzing.activities.ui.UserRole.COORDINATOR;
import static com.amhzing.activities.ui.application.SecurityUtil.hasRole;


import static com.amhzing.activities.ui.feature.AppFeatures.ACTIVITY_FACILITATED;
import static com.amhzing.activities.ui.feature.AppFeatures.ACTIVITY_HOSTED;

@Service
public class UIAccessService {

    public boolean includeFacilitatedActivity() {
        if (ACTIVITY_FACILITATED.isActive() && hasRole(COORDINATOR.getRoleFullName())) {
            return true;
        }

        return false;
    }

    public boolean includeHostedActivity() {
        if (ACTIVITY_HOSTED.isActive() && hasRole(COORDINATOR.getRoleFullName())) {
            return true;
        }

        return false;
    }
}
