package third.practice;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public abstract class CountCommentsTask extends DefaultTask {

    public CountCommentsTask() {
        setGroup("comment management from build.gradle.kts");
        setDescription("Рахує загальну кількість коментарів");
    }

    @TaskAction
    public void action() {
        File sourceDir = getProject().file("src/main");

        if (!sourceDir.exists()) {
            System.out.println(">>>> Директорію src/main не знайдено, підрахунок неможливий");
            return;
        }

        Pattern commentPattern = Pattern.compile("(/\\*[\\s\\S]*?\\*/)|(//.*)");

        int[] totalComments = {0};
        int[] filesWithComments = {0};
        int[] totalFilesScanned = {0};

        try (Stream<Path> paths = Files.walk(sourceDir.toPath())) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> {
                        String fileName = path.toString();
                        return fileName.endsWith(".java") || fileName.endsWith(".kt");
                    })
                    .forEach(path -> {
                        totalFilesScanned[0]++;
                        try {
                            String content = new String(Files.readAllBytes(path));
                            Matcher matcher = commentPattern.matcher(content);

                            int commentsInCurrentFile = 0;

                            while (matcher.find()) {
                                commentsInCurrentFile++;
                                totalComments[0]++;
                            }

                            if (commentsInCurrentFile > 0) {
                                filesWithComments[0]++;
                            }
                        } catch (IOException e) {
                            System.err.println(">>>> Помилка читання файлу: " + path.getFileName());
                        }
                    });
        } catch (IOException e) {
            System.err.println(">>>> Помилка сканування директорії: " + e.getMessage());
        }

        System.out.println("АНАЛІЗ КОМЕНТАРІВ У ПРОЄКТІ (JAVA)");
        System.out.println("Проскановано файлів: " + totalFilesScanned[0]);
        System.out.println("Файлів із коментарями: " + filesWithComments[0]);
        System.out.println("Загальна кількість коментарів: " + totalComments[0]);
    }
}