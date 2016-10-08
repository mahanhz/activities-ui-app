package com.amhzing.activities.ui.web.client.vaadin;

import com.amhzing.activities.ui.web.client.vaadin.page.SearchParticipantPage;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import static org.apache.commons.lang3.Validate.notNull;

@Theme("valo")
@SpringUI(path = "/vaadin")
public class VaadinUI extends UI {

    private SearchParticipantPage searchParticipantPage;

    @Autowired
    public VaadinUI(final SearchParticipantPage searchParticipantPage) {
        this.searchParticipantPage = searchParticipantPage;
    }

    @Override
    protected void init(final VaadinRequest request) {
        notNull(request);

        final VerticalLayout pageLayout = new VerticalLayout();
        final TabSheet tabsheet = new TabSheet();
        pageLayout.addComponent(tabsheet);
        pageLayout.setSpacing(true);
        pageLayout.setMargin(true);

        searchForParticipants(tabsheet);

        createActivity(tabsheet);

        setContent(pageLayout);
    }

    private void searchForParticipants(final TabSheet tabsheet) {
        final VerticalLayout tabContents = new VerticalLayout();

        searchParticipantPage.populate(tabContents);
        tabsheet.addTab(tabContents, "Search for Participants");
    }

    private void createActivity(final TabSheet tabsheet) {
        final VerticalLayout tabContents = new VerticalLayout();
        tabsheet.addTab(tabContents, "Create Activity");
    }
}
