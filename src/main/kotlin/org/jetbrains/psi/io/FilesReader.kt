package org.jetbrains.psi.io

import java.io.File

class FilesReader(private val dirPath: String, private val filesExt: String) {
    private fun readFile(file: File): String {
        return file.readText()
    }

    private fun walkDirectory(callback: (String) -> Unit) {
        File(dirPath).walkTopDown().forEach {
            if (it.isFile && it.extension == filesExt) {
                callback(readFile(it))
            }
        }
    }

    fun run(callback: (String) -> Unit) {
        walkDirectory { callback(it) }
    }
}