package my.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Deletes all the comments and specific parts of code from a TARGET FILE.
 */
@Mojo(name="delete-part-code")
public class DeletePartOfCode extends AbstractMojo {

    enum Visibility {
        PRIVATE, PUBLIC
    }

    enum Part {
        METHODS, VARIABLES
    }

    @Parameter(property = "targetFile", required = true)
    File targetFile;

    @Parameter(property = "visibility")
    Visibility visibility;

    @Parameter(property = "part")
    Part part;

    @Parameter(property = "deleteComments", defaultValue = "true")
    boolean deleteComments;


    @Override
    public void execute() throws MojoExecutionException {
        if (targetFile == null || !targetFile.exists() || !targetFile.isFile()) {
            getLog().warn(">>>> Target file not found or is not a file: " + targetFile);
            return;
        }

        getLog().info(">>>> Starting code cleanup in file: " + targetFile.getAbsolutePath());

        processFile(targetFile.toPath());
    }

    private void processFile(Path path) {
        try {
            String code = Files.readString(path);
            String originalCode = code;

            if (deleteComments) {
                code = code.replaceAll("//.*|/\\*(?:.|[\\n\\r])*?\\*/", "");
            }

            if (visibility != null && part != null) {
                String vis = visibility.name().toLowerCase();

                if (part == Part.VARIABLES) {
                    String varRegex = vis + "\\s+[^;{]+;";
                    code = code.replaceAll(varRegex, "");
                }
                else if (part == Part.METHODS) {
                    String methodRegex = vis + "\\s+[\\w\\s<>\\[\\]]+\\s+\\w+\\s*\\([^)]*\\)\\s*\\{([^{}]*|\\{[^{}]*\\})*\\}";
                    code = code.replaceAll(methodRegex, "");
                }
            }

            if (!code.equals(originalCode)) {
                Files.writeString(path, code);
                getLog().info(">>>> Cleared and updated file: " + path.getFileName());
            } else {
                getLog().info(">>>> No changes were needed for: " + path.getFileName());
            }

        } catch (IOException e) {
            getLog().error(">>>> Error processing file: " + path, e);
        }
    }
}