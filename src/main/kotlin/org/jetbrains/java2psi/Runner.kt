package org.jetbrains.java2psi

import com.intellij.ide.highlighter.JavaFileType
import com.intellij.psi.PsiFileFactory
import org.jetbrains.java2psi.infrastructure.Preparatory
import org.jetbrains.java2psi.io.FilesReader
import org.jetbrains.java2psi.io.FilesWriter
import java.io.File

class Runner {
    companion object {
        fun run(sourcesDir: String, psiDir: String) {
            val language = JavaFileType.INSTANCE.language
            val project = Preparatory.prepare(language)
            val psiFactory = PsiFileFactory.getInstance(project)

            FilesReader.run(sourcesDir, "java") { content: String, file: File ->
                val psiFile = psiFactory.createFileFromText(language, content)
                val jsonPsi = Psi2TypesTree.convert(psiFile)
                FilesWriter.write(sourcesDir, psiDir, file, jsonPsi)
            }
        }
    }
}