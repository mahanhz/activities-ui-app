package com.amhzing.activities.ui.acceptancetest.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "src/test/resources/cucumber" },
                 tags = { "~@ignored" },
                 format = { "json:build/reports/cucumber/cucumber.json",
                            "html:build/reports/cucumber/cucumber.html",
                            "pretty" })
public class AcceptanceTestRunner {
}
