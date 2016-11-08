package com.amhzing.activities.ui.web.client.angular.controller;

import com.amhzing.activities.ui.web.client.adapter.UIAccessService;
import com.amhzing.activities.ui.web.client.angular.model.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.apache.commons.lang3.Validate.notNull;

@RestController
public class AccessController extends AbstractController {

    private UIAccessService uiAccessService;

    @Autowired
    public AccessController(final UIAccessService uiAccessService) {
        this.uiAccessService = notNull(uiAccessService);
    }

    @GetMapping(value = "/access")
    public Access access() {
        return Access.create(uiAccessService.includeFacilitatedActivity(),
                             uiAccessService.includeHostedActivity());
    }
}
