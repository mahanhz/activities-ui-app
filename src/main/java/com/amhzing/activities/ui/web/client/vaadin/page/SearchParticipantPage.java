package com.amhzing.activities.ui.web.client.vaadin.page;

import com.amhzing.activities.ui.application.Failure;
import com.amhzing.activities.ui.application.ParticipantService;
import com.amhzing.activities.ui.application.Participants;
import com.amhzing.activities.ui.application.QueryCriteria;
import com.amhzing.activities.ui.web.client.model.ParticipantModel;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;
import io.atlassian.fugue.Either;
import org.springframework.beans.factory.annotation.Autowired;

import static com.amhzing.activities.ui.web.client.adapter.ParticipantAdapter.adaptParticipant;
import static com.vaadin.data.Validator.InvalidValueException;
import static com.vaadin.event.ShortcutAction.KeyCode.ENTER;
import static com.vaadin.ui.Grid.SelectionMode.MULTI;
import static org.apache.commons.lang3.Validate.notNull;

@SpringComponent
@UIScope
public class SearchParticipantPage {

    private ParticipantService<Failure, Participants> participantService;

    private TextField countryText = new TextField();
    private TextField cityText = new TextField();
    private TextField addressLine1Text = new TextField();
    private TextField lastNameText = new TextField();
    private TextField idText = new TextField();

    private Button searchBtn = new Button("Search");

    private ParticipantForm participantForm = new ParticipantForm();

    private Grid grid = new Grid();

    @Autowired
    public SearchParticipantPage(final ParticipantService<Failure, Participants> participantService) {
        this.participantService = notNull(participantService);
    }

    public void populate(final VerticalLayout pageLayout) {
        notNull(pageLayout);

        initWidgets();

        pageLayout.addComponents(searchLayout(), resultLayout());
        pageLayout.setSpacing(true);
        pageLayout.setMargin(true);
    }

    private void initWidgets() {
        countryText.setInputPrompt("Country code");
        cityText.setInputPrompt("City");
        addressLine1Text.setInputPrompt("Address line 1");
        lastNameText.setInputPrompt("Last name");
        idText.setInputPrompt("Id");

        countryText.setValidationVisible(false);
        countryText.addValidator(new StringLengthValidator("Invalid country code (was {0})", 2, 3, false));
        countryText.focus();

        searchBtn.setClickShortcut(ENTER);

        grid.setSelectionMode(MULTI);
        showResults(false);
    }

    private HorizontalLayout searchLayout() {
        CssLayout filtering = new CssLayout();
        filtering.addComponents(countryText, cityText, addressLine1Text, lastNameText, idText);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        searchButtonListener();

        HorizontalLayout searchLayout = new HorizontalLayout(filtering, searchBtn);
        searchLayout.setSpacing(true);
        return searchLayout;
    }

    private HorizontalLayout resultLayout() {
        //gridSelectionListener();

        HorizontalLayout resultLayout = new HorizontalLayout(grid, participantForm);
        resultLayout.setSpacing(true);
        resultLayout.setSizeFull();
        grid.setSizeFull();
        resultLayout.setExpandRatio(grid, 1.7f);
        resultLayout.setExpandRatio(participantForm, 1.3f);

        return resultLayout;
    }

    private void searchButtonListener() {
        searchBtn.addClickListener(e -> {
            setValidationVisibility(false);
            showResults(false);
            try {
                validateFields();
                getParticipants(queryCriteria());
            } catch (final InvalidValueException ex) {
                Notification.show(ex.getMessage());
                setValidationVisibility(true);
            }
        });
    }

    private void gridSelectionListener() {
        grid.addSelectionListener(event -> {
            if (event.getSelected().isEmpty()) {
                participantForm.setVisible(false);
            } else {
                final ParticipantModel participant = (ParticipantModel) event.getSelected().iterator().next();
                participantForm.participantDetails(participant);
            }
        });
    }

    private void setValidationVisibility(final boolean isVisible) {
        countryText.setValidationVisible(isVisible);
    }

    private void validateFields() {
        countryText.validate();
    }

    private QueryCriteria queryCriteria() {
        return QueryCriteria.create(countryText.getValue(),
                                    cityText.getValue(),
                                    addressLine1Text.getValue(),
                                    lastNameText.getValue(),
                                    idText.getValue());
    }

    private void getParticipants(final QueryCriteria queryCriteria) {
        final Either<Failure, Participants> response = participantService.participantsByCriteria(queryCriteria);

        if (response.isRight()) {
            populateGrid(response.right().get());
        } else {
            // TODO - Should display something meaningful when an error occurs
        }
    }

    private void populateGrid(final Participants response) {
        grid.setVisible(true);

        final BeanItemContainer<ParticipantModel> participants = new BeanItemContainer<>(ParticipantModel.class);
        participants.addNestedContainerBean("name");
        participants.addNestedContainerBean("address");

        participants.addAll(adaptParticipant(response.getParticipants()));

        final GeneratedPropertyContainer gpc = new GeneratedPropertyContainer(participants);

        gpc.addGeneratedProperty("info", propertyValueGenerator());

        grid.setContainerDataSource(gpc);

        grid.setColumns("info", "name.firstName", "name.middleName", "name.lastName", "name.suffix");

        grid.getColumn("info")
            .setRenderer(new ButtonRenderer(event -> {
                if (event.getItemId() instanceof ParticipantModel) {
                    final ParticipantModel participant = (ParticipantModel) event.getItemId();
                    participantForm.participantDetails(participant);
                } else {
                    participantForm.setVisible(false);
                }
            }));
    }

    private PropertyValueGenerator<String> propertyValueGenerator() {
        return new PropertyValueGenerator<String>() {
            @Override
            public String getValue(Item item, Object itemId,
                                   Object propertyId) {
                return "info"; // The caption
            }

            @Override
            public Class<String> getType() {
                return String.class;
            }
        };
    }

    private void showResults(final boolean isVisible) {
        grid.setVisible(false);
        participantForm.setVisible(false);
    }
}
