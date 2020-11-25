package br.com.svlabs;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * @author lucasns
 * @since #1.0
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources",
        plugin = { "pretty", "html:target/report"},
        tags = {"~@SmokeTest"})
public class CucumberTest {

}
