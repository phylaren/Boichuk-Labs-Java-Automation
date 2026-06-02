package third.practice;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public abstract class BackupProjectTask extends DefaultTask {

    public BackupProjectTask() {
        setGroup("comment management");
        setDescription("Створює резервну копію директорії src/main перед змінами");
    }

    @TaskAction
    public void action() throws IOException {
        File sourceDir = getProject().file("src/main");

        // more logical file but i couldn't show the results, because you said that we should commit folders like this
        // File backupDir = getProject().getLayout().getBuildDirectory().dir("backup-src").get().getAsFile();

        File backupDir = getProject().file("backup-src");

        if (!sourceDir.exists()) {
            System.out.println(">>>> Директорію src/main не знайдено, бекап не створено");
            return;
        }

        Path sourcePath = sourceDir.toPath();
        Path backupPath = backupDir.toPath();

        Files.walk(sourcePath).forEach(source -> {
            try {
                Path destination = backupPath.resolve(sourcePath.relativize(source));
                if (Files.isDirectory(source)) {
                    if (!Files.exists(destination)) {
                        Files.createDirectories(destination);
                    }
                } else {
                    Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                throw new RuntimeException(">>>> Помилка копіювання файлу: " + source, e);
            }
        });

        System.out.println(">>>> Створено резервну копію коду у: " + backupDir.getAbsolutePath());
    }
}