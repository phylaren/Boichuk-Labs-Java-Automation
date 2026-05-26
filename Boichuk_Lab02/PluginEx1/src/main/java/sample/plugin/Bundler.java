package sample.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Collects the text from the all the .java files in the project to one single file
 *
 */

@Mojo(name = "bundle-code")
public class Bundler extends AbstractMojo
{
    private enum LOGS {
        ALL, NECESSARY, NONE
    }

    @Parameter(property = "rootDir")
    Path rootDir;

    @Parameter(property = "outputFile")
    File outputFile;

    @Parameter(property = "logsState", defaultValue = "ALL")
    LOGS logsState;

    @Override
    public void execute() throws MojoExecutionException
    {
        if(logsState != LOGS.NONE) getLog().info(">>>> Starting collecting text");

        StringBuilder collectedContent = new StringBuilder();

        try (Stream<Path> stream = Files.walk(rootDir)) {
            stream.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".java"))
                    .forEach(path -> {

                        if(logsState == LOGS.ALL) getLog().info(">>>> Reading file: " + path.toAbsolutePath());

                        try {
                            collectedContent.append("// ").append(path.getFileName()).append("\n");
                            collectedContent.append(Files.readString(path));
                            collectedContent.append("\n\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException e) {
            //e.printStackTrace();
            throw new MojoExecutionException(">>>> Error while reading files");
        }

        if(logsState != LOGS.NONE)getLog().info(">>>> Writing to the file"+outputFile.getName());

        try {
            if (outputFile.getParentFile() != null) {
                outputFile.getParentFile().mkdirs();
            }
            Files.writeString(outputFile.toPath(), collectedContent.toString());
        } catch (IOException e) {
            //throw new RuntimeException(e);
            throw new MojoExecutionException(">>>> Error while writing to output file", e);

        }
    }
}