package org.jetbrains.psi

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile

abstract class Psi2TypesTree {
    companion object {
        fun createJsonNode(node: ASTNode): JsonObject {
            val jsonNode = JsonObject()

            jsonNode.add("type", JsonPrimitive(node.elementType.toString()))
//            jsonNode.add("chars", JsonPrimitive(node.chars.toString()))

            return jsonNode
        }

        fun walk(psi: PsiElement, nodes: JsonArray) {
            nodes.add(createJsonNode(psi.node))
            if (psi.children.isNotEmpty()) {
                val children = JsonArray()
                nodes.last().asJsonObject.add("children", children)
                psi.children.map { walk(it, children) }
            }
        }

        fun convert(psiFile: PsiFile): JsonArray {
            val nodes = JsonArray()
            walk(psiFile, nodes)
            return nodes
        }
    }
}