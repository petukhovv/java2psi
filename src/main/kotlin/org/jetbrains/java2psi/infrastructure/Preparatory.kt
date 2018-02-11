package org.jetbrains.java2psi.infrastructure

import com.intellij.lang.Language
import com.intellij.lang.LanguageParserDefinitions
import com.intellij.lang.java.custom.JavaParserDefinition
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectFileIndex
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.roots.impl.DirectoryIndex
import com.intellij.openapi.roots.impl.DirectoryIndexImpl
import com.intellij.openapi.roots.impl.ProjectFileIndexImpl
import com.intellij.openapi.roots.impl.ProjectRootManagerImpl
import com.intellij.openapi.util.Disposer
import com.intellij.psi.impl.JavaPsiImplementationHelper
import com.intellij.psi.impl.JavaPsiImplementationHelperImpl
import org.intellij.grammar.LightPsi

abstract class Preparatory {
    companion object {
        fun prepare(language: Language): Project {
            val project = LightPsi.Init.initAppAndProject(Disposer.newDisposable())

            project.registerService(JavaPsiImplementationHelper::class.java, JavaPsiImplementationHelperImpl::class.java)
            project.registerService(ProjectRootManager::class.java, ProjectRootManagerImpl::class.java)
            project.registerService(ProjectFileIndex::class.java, ProjectFileIndexImpl::class.java)
            project.registerService(DirectoryIndex::class.java, DirectoryIndexImpl::class.java)
            LightPsi.Init.addKeyedExtension(LanguageParserDefinitions.INSTANCE, language, JavaParserDefinition(), project)

            return project
        }
    }
}