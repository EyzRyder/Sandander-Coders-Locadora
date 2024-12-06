package com.ada.santander.coders.locadora;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.ada.santander.coders.locadora.steps",
        plugin = {"pretty", "html:target/cucumber-reports"}
)
public class RunCucumberTest {
}

