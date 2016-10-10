package com.amhzing.activities.ui.web.client.vaadin;

import com.amhzing.activities.ui.web.client.vaadin.page.SearchParticipantPage;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.apache.commons.lang3.Validate.notNull;

@Theme("activities")
@SpringUI(path = "/vaadin")
public class VaadinUI extends UI {

    private ServletContext servletContext;
    private SearchParticipantPage searchParticipantPage;

    @Autowired
    public VaadinUI(final ServletContext servletContext,
                    final SearchParticipantPage searchParticipantPage) {
        this.servletContext = notNull(servletContext);
        this.searchParticipantPage = notNull(searchParticipantPage);
    }

    // This is needed to find custom themes in a spring boot JAR app where webapp folder does not exist
    @PostConstruct
    public void postConstruct() throws IOException {
        final Theme annotation = getUI().getClass().getAnnotation(Theme.class);
        if (annotation != null) {
            String root = servletContext.getRealPath("/");
            if (root != null && Files.isDirectory(Paths.get(root))) {
                Files.createDirectories(Paths.get(servletContext.getRealPath("/VAADIN/themes/" + annotation.value())));
            }
        }
    }

    @Override
    protected void init(final VaadinRequest request) {
        notNull(request);

        final VerticalLayout pageLayout = new VerticalLayout();
        final TabSheet tabsheet = new TabSheet();

        pageLayout.addComponents(headerLayout(), tabsheet);
        //pageLayout.setSpacing(true);
        pageLayout.setMargin(true);

        searchForParticipants(tabsheet);

        createActivity(tabsheet);

        setContent(pageLayout);
    }

    private Layout headerLayout() {
        final HorizontalLayout headerLayout = new HorizontalLayout();

        final Button logoutBtn = new Button("Logout", event -> {
            // Let Spring Security handle the logout by redirecting to the logout URL
            getPage().setLocation("logout");
        });

        headerLayout.addComponent(logoutBtn);
        headerLayout.setComponentAlignment(logoutBtn, Alignment.TOP_RIGHT);
        headerLayout.setSizeFull();

        return headerLayout;
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
