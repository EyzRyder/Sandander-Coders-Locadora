package com.ada.santander.coders.locadora;

import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.ada.santander.coders.locadora",
        plugin = {"pretty", "html:target/cucumber-reports"}
)
public class CucumberTestRunner {
}
