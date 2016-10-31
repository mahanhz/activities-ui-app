package com.amhzing.activities.ui.web.client.vaadin;

import com.amhzing.activities.ui.web.client.adapter.UIAccessService;
import com.amhzing.activities.ui.web.client.vaadin.page.SearchParticipantPage;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletContext;

import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class VaadinUITest {

    @Mock
    private ServletContext servletContext;
    @Mock
    private UIAccessService uiAccessService;
    @Mock
    private SearchParticipantPage searchParticipantPage;
    @Mock
    private VaadinRequest vaadinRequest;

    private VaadinUI vaadinUI;

    @Before
    public void setUp() {
        vaadinUI = new VaadinUI(servletContext, uiAccessService, searchParticipantPage);
        UI.setCurrent(vaadinUI);
    }

    @Test
    public void should_init_ui() throws Exception {
        try {
            given(uiAccessService.includeFacilitatedActivity()).willReturn(true);
            given(uiAccessService.includeHostedActivity()).willReturn(true);
            vaadinUI.init(vaadinRequest);
        } catch (Exception ex) {
            fail("Should have created the UI");
        }
    }

}