package com.amhzing.activities.ui.web.client.vaadin.page;

import com.amhzing.activities.ui.web.client.adapter.ParticipantAdapter;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.VerticalLayout;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SearchParticipantPageTest {

    @Mock
    private ParticipantAdapter participantAdapter;

    private SearchParticipantPage searchParticipantPage;

    @Before
    public void setUp() {
        searchParticipantPage = new SearchParticipantPage(participantAdapter);
    }

    @Test
    public void should_search_by_country() throws Exception {
        final VerticalLayout verticalLayout = new VerticalLayout();
        searchParticipantPage.populate(verticalLayout);

        final ComboBox countrySelect = searchParticipantPage.countrySelect;
        final Object firstItem = countrySelect.getItemIds().iterator().next();
        countrySelect.setValue(firstItem);
        assertThat(countrySelect.getValue()).isEqualTo(firstItem);

        searchParticipantPage.searchBtn.click();
        assertThat(searchParticipantPage.grid.isVisible()).isTrue();
    }
}