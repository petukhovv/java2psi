package org.jetbrains.psi

import com.intellij.ide.highlighter.JavaFileType
import com.intellij.psi.PsiFileFactory
import com.xenomachina.argparser.ArgParser
import org.jetbrains.psi.infrastructure.Preparatory
import org.jetbrains.psi.io.FilesReader
import org.jetbrains.psi.io.FilesWriter
import java.io.File


fun main(args : Array<String>) {
    val parser = ArgParser(args)
    val sourcesDir by parser.storing("-i", "--input", help="path to dir with java source code files for psi extraction")
    val psiDir by parser.storing("-o", "--output", help="path to dir in which will be written psi trees")

    val language = JavaFileType.INSTANCE.language
    val project = Preparatory.prepare(language)
    val psiFactory = PsiFileFactory.getInstance(project)

    FilesReader.run(sourcesDir, JavaFileType.DEFAULT_EXTENSION) { content: String, file: File ->
        val psiFile = psiFactory.createFileFromText(language, content)
        val jsonPsi = Psi2TypesTree.convert(psiFile)
        FilesWriter.write(sourcesDir, psiDir, file, jsonPsi)
    }
}
