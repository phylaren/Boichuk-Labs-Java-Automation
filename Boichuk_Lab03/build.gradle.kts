import third.practice.CommentRemoverPlugin;

plugins {
    id("java")
}

group = "third.practice"
version = "1.0-SNAPSHOT"

apply<CommentRemoverPlugin>()

tasks.register("countCommentsJava", third.practice.CountCommentsTask::class.java)

tasks.register("countComments") {
    group = "comment management from build.gradle.kts"
    description = "Рахує загальну кількість коментарів"

    doLast {
        val sourceDir = file("src/main")
        if (!sourceDir.exists()) {
            println(">>>> Директорію src/main не знайдено")
            return@doLast
        }

        val commentRegex = Regex("(/\\*[\\s\\S]*?\\*/)|(//.*)")

        var totalComments = 0
        var filesWithComments = 0
        var totalFilesScanned = 0

        sourceDir.walkTopDown()
            .filter { it.isFile && it.extension in listOf("java", "kt") }
            .forEach { file ->
                totalFilesScanned++
                val content = file.readText()

                val commentsInFile = commentRegex.findAll(content).count()

                if (commentsInFile > 0) {
                    totalComments += commentsInFile
                    filesWithComments++
                }
            }

        println("АНАЛІЗ КОМЕНТАРІВ У ПРОЄКТІ (KOTLIN)")
        println("Проскановано файлів: $totalFilesScanned")
        println("Файлів із коментарями: $filesWithComments")
        println("Загальна кількість коментарів: $totalComments")
    }
}