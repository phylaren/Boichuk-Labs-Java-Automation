package sample.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Says "Hi" to the user.
 *
 */

// execution: mvn sample.plugin:plugin1-maven-plugin:sayhi
@Mojo(name = "sayhi")
public class GreetingMojo extends AbstractMojo
{
    @Override
    public void execute() throws MojoExecutionException
    {
        getLog().info("Hello, world.");
    }
}