package third.practice;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public abstract class RemoveCommentsTask extends DefaultTask {

    public RemoveCommentsTask() {
        setGroup("comment management");
        setDescription("Видаляє однорядкові та багаторядкові коментарі з коду");
    }

    @TaskAction
    public void action() {
        File sourceDir = getProject().file("src/main");
        if (!sourceDir.exists()) {
            System.out.println(">>>> Немає директорії src/main для обробки");
            return;
        }

        String commentRegex = "(/\\*[\\s\\S]*?\\*/)|(//.*)";

        int[] modifiedFilesCount = {0};

        try (Stream<Path> paths = Files.walk(sourceDir.toPath())) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> {
                        String fileName = path.toString();
                        return fileName.endsWith(".java");
                    })
                    .forEach(path -> {
                        try {
                            String originalText = new String(Files.readAllBytes(path));
                            String newText = originalText.replaceAll(commentRegex, "");

                            if (!originalText.equals(newText)) {
                                Files.write(path, newText.getBytes());
                                modifiedFilesCount[0]++;
                                System.out.println(">>>> Очищено файл: " + path.getFileName());
                            }
                        } catch (IOException e) {
                            System.err.println(">>>> Помилка обробки файлу: " + path.getFileName());
                        }
                    });
        } catch (IOException e) {
            System.err.println(">>>> Помилка читання директорії: " + e.getMessage());
        }

        System.out.println(">>>> Готово! Коментарі видалено з " + modifiedFilesCount[0] + " файлів");
    }
}