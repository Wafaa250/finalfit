package fit;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)

@CucumberOptions(
		features = "src/feature",
	    plugin = { "pretty", "html:target/cucumber/wikipedia.html" }, 
	    monochrome = true, 
	    snippets = SnippetType.CAMELCASE, 
	    glue = "fit"
	)

public class AccepTest {

}
