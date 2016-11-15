package com.amhzing.activities.ui.web.client.vaadin.page;

import com.amhzing.activities.ui.web.client.adapter.ParticipantAdapter;
import com.amhzing.activities.ui.web.client.exception.UIFriendlyException;
import com.amhzing.activities.ui.web.client.model.Country;
import com.amhzing.activities.ui.web.client.model.ParticipantModel;
import com.amhzing.activities.ui.web.client.model.SearchSpecification;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.amhzing.activities.ui.web.client.exception.UIFriendlyException.HTML_ERROR_MESSAGE;
import static com.vaadin.data.Validator.InvalidValueException;
import static com.vaadin.event.ShortcutAction.KeyCode.ENTER;
import static com.vaadin.ui.Grid.SelectionMode.MULTI;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.Validate.notNull;

@SpringComponent
@UIScope
public class SearchParticipantPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchParticipantPage.class);

    private ParticipantAdapter participantAdapter;

    protected ComboBox countrySelect = new ComboBox();
    private TextField cityText = new TextField();
    private TextField addressLine1Text = new TextField();
    private TextField lastNameText = new TextField();
    private TextField idText = new TextField();

    protected Button searchBtn = new Button("Search");
    protected Grid grid = new Grid();
    private ParticipantForm participantForm = new ParticipantForm();
    private Panel resultErrorPanel = new Panel();

    @Autowired
    public SearchParticipantPage(final ParticipantAdapter participantAdapter) {
        this.participantAdapter = notNull(participantAdapter);
    }

    public void populate(final VerticalLayout pageLayout) {
        notNull(pageLayout);

        initWidgets();

        pageLayout.addComponents(searchLayout(), resultLayout());
        pageLayout.setSpacing(true);
        pageLayout.setMargin(true);
    }

    private void initWidgets() {
        cityText.setInputPrompt("City");
        addressLine1Text.setInputPrompt("Address line 1");
        lastNameText.setInputPrompt("Last name");
        idText.setInputPrompt("Id");

        initCountrySelect();

        searchBtn.setClickShortcut(ENTER);
        grid.setSelectionMode(MULTI);
        hideResults();
    }

    private void initCountrySelect() {
        countrySelect.setValidationVisible(false);
        countrySelect.setRequired(true);
        countrySelect.setRequiredError("Please select a country!");

        final BeanItemContainer<Country> countries = new BeanItemContainer(Country.class, countries());
        countrySelect.setContainerDataSource(countries);
        countrySelect.setItemCaptionPropertyId("name");
        countrySelect.setFilteringMode(FilteringMode.CONTAINS);
        countrySelect.focus();
    }

    private List<Country> countries() {
        return Arrays.stream(Locale.getISOCountries())
                     .map(countryCode -> new Locale("", countryCode))
                     .map(locale -> Country.create(locale.getCountry(), locale.getDisplayCountry()))
                     .collect(toList());
    }

    private HorizontalLayout searchLayout() {
        final CssLayout filtering = new CssLayout();
        filtering.addComponents(countrySelect, cityText, addressLine1Text, lastNameText, idText);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        searchButtonListener();

        final HorizontalLayout searchLayout = new HorizontalLayout(filtering, searchBtn);
        searchLayout.setSpacing(true);
        return searchLayout;
    }

    private VerticalLayout resultLayout() {
        //gridSelectionListener();

        final VerticalLayout resultLayout = new VerticalLayout();

        HorizontalLayout foundResultLayout = new HorizontalLayout(grid, participantForm);
        foundResultLayout.setSpacing(true);
        foundResultLayout.setSizeFull();
        grid.setSizeFull();
        foundResultLayout.setExpandRatio(grid, 1.7f);
        foundResultLayout.setExpandRatio(participantForm, 1.3f);

        resultLayout.addComponents(foundResultLayout, resultErrorPanel);

        return resultLayout;
    }

    private void searchButtonListener() {
        searchBtn.addClickListener(e -> {
            preValidationVisibility();
            hideResults();
            try {
                validateFields();
                getParticipants(searchSpec());
            } catch (final InvalidValueException ex) {
                Notify.warn(ex.getMessage());
                validationErrorsVisibility();
            } catch (final Exception ex) {
                LOGGER.error("Could not search for participants due to an error", ex);
                Notify.error(HTML_ERROR_MESSAGE);
                validationErrorsVisibility();
            }
        });
    }

    private void preValidationVisibility() {
        countrySelect.setValidationVisible(false);
    }

    private void validationErrorsVisibility() {
        countrySelect.setValidationVisible(true);
        countrySelect.focus();
    }

    private void validateFields() {
        countrySelect.validate();
    }

//    private void gridSelectionListener() {
//        grid.addSelectionListener(event -> {
//            if (event.getSelected().isEmpty()) {
//                participantForm.setVisible(false);
//            } else {
//                final ParticipantModel participant = (ParticipantModel) event.getSelected().iterator().next();
//                participantForm.participantDetails(participant);
//            }
//        });
//    }

    private SearchSpecification searchSpec() {
        return SearchSpecification.create(countryCode(),
                                          cityText.getValue(),
                                          addressLine1Text.getValue(),
                                          lastNameText.getValue(),
                                          idText.getValue());
    }

    private String countryCode() {
        final Object country = countrySelect.getValue();

        if (country instanceof Country) {
            return ((Country) country).getCode();
        } else {
            throw new IllegalArgumentException("No country exists for selected value: " + country);
        }
    }

    private void getParticipants(final SearchSpecification searchSpec) {

        try {
            final List<ParticipantModel> participants = participantAdapter.participants(searchSpec);
            populateGrid(participants);
        } catch (final UIFriendlyException ex) {
            Notify.error(ex.getMessage());
        }
    }

    private void populateGrid(final List<ParticipantModel> participantModels) {
        grid.setVisible(true);

        final BeanItemContainer<ParticipantModel> participants = new BeanItemContainer<>(ParticipantModel.class);
        participants.addNestedContainerBean("name");
        participants.addNestedContainerBean("address");

        participants.addAll(participantModels);

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
            public String getValue(Item item, Object itemId, Object propertyId) {
                return "info"; // The caption
            }

            @Override
            public Class<String> getType() {
                return String.class;
            }
        };
    }

    private void hideResults() {
        grid.setVisible(false);
        participantForm.setVisible(false);
        resultErrorPanel.setVisible(false);
    }
}
