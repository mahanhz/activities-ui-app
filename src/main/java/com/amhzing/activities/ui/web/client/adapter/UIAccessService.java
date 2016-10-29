package com.amhzing.activities.ui.web.client.adapter;

import org.springframework.stereotype.Service;

import static com.amhzing.activities.ui.user.UserRole.COORDINATOR;
import static com.amhzing.activities.ui.user.UserUtil.hasRole;


import static com.amhzing.activities.ui.feature.AppFeatures.ACTIVITY_FACILITATED;
import static com.amhzing.activities.ui.feature.AppFeatures.ACTIVITY_HOSTED;

@Service
public class UIAccessService {

    public boolean includeFacilitatedActivity() {
        return ACTIVITY_FACILITATED.isActive() && hasRole(COORDINATOR.getRoleFullName());
    }

    public boolean includeHostedActivity() {
        return ACTIVITY_HOSTED.isActive() && hasRole(COORDINATOR.getRoleFullName());
    }
}
