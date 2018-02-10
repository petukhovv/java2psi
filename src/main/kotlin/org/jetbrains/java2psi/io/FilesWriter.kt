package org.jetbrains.java2psi.io

import com.google.gson.Gson
import com.google.gson.JsonArray
import java.io.File

class FilesWriter {
    companion object {
        fun write(inputDir: String, outputDir: String, file: File, content: JsonArray) {
            val relativePath = file.relativeTo(File(inputDir))
            val outputPath = File("$outputDir/$relativePath.json")

            File("$outputDir/${relativePath.parent ?: ""}").mkdirs()
            outputPath.writeText(Gson().toJson(content))
        }
    }
}