package org.jetbrains.java2psi.io

import java.io.File

class FilesReader {
    companion object {
        private fun readFile(file: File): String {
            return file.readText()
        }

        private fun walkDirectory(dirPath: String, filesExt: String, callback: (String, File) -> Unit) {
            File(dirPath).walkTopDown().forEach {
                if (it.isFile && it.extension == filesExt) {
                    callback(readFile(it), it)
                }
            }
        }

        fun run(dirPath: String, filesExt: String, callback: (String, File) -> Unit) {
            walkDirectory(dirPath, filesExt, fun(content, file) = callback(content, file))
        }
    }
}