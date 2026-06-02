package third.practice;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskProvider;

public class CommentRemoverPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {

        TaskProvider<BackupProjectTask> backupTask = project.getTasks().register(
                "backupProject", BackupProjectTask.class
        );

        project.getTasks().register("removeComments", RemoveCommentsTask.class, task -> {
            task.dependsOn(backupTask);
        });
    }
}