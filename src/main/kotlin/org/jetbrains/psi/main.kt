package org.jetbrains.psi

import com.intellij.ide.highlighter.JavaFileType
import com.intellij.lang.*
import com.intellij.lang.java.JavaParserDefinition
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectFileIndex
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.roots.impl.DirectoryIndex
import com.intellij.openapi.roots.impl.DirectoryIndexImpl
import com.intellij.openapi.roots.impl.ProjectFileIndexImpl
import com.intellij.openapi.roots.impl.ProjectRootManagerImpl
import com.intellij.openapi.util.Disposer
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.impl.JavaPsiImplementationHelper
import com.intellij.psi.impl.JavaPsiImplementationHelperImpl
import com.xenomachina.argparser.ArgParser
import org.intellij.grammar.LightPsi
import org.jetbrains.psi.io.FilesReader

fun psiWalk(psi: PsiElement, level: Int = 0) {
    for (i in 0..level) {
        print(' ')
    }
    println(psi.node.elementType)
    if (psi.children.isNotEmpty()) {
        for (child in psi.children) {
            psiWalk(child, level + 1)
        }
    }
}

fun prepareProjectInfrastructure(language: Language): Project {
    val project = LightPsi.Init.initAppAndProject(Disposer.newDisposable())

    project.registerService(JavaPsiImplementationHelper::class.java, JavaPsiImplementationHelperImpl::class.java)
    project.registerService(ProjectRootManager::class.java, ProjectRootManagerImpl::class.java)
    project.registerService(ProjectFileIndex::class.java, ProjectFileIndexImpl::class.java)
    project.registerService(DirectoryIndex::class.java, DirectoryIndexImpl::class.java)
    LightPsi.Init.addKeyedExtension(LanguageParserDefinitions.INSTANCE, language, JavaParserDefinition(), project)

    return project
}

fun main(args : Array<String>) {
    val parser = ArgParser(args)
    val path by parser.storing("-p", "--path", help="path to java files for psi extraction")
    val language = JavaFileType.INSTANCE.language
    val project = prepareProjectInfrastructure(language)
    val psiFactory = PsiFileFactory.getInstance(project)

    FilesReader(path, JavaFileType.DEFAULT_EXTENSION).run {
        val psiFile = psiFactory.createFileFromText(language, it)
        psiWalk(psiFile)
    }
}
