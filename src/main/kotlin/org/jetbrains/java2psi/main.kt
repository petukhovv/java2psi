package org.jetbrains.java2psi

import com.xenomachina.argparser.ArgParser

fun main(args : Array<String>) {
    val parser = ArgParser(args)
    val sourcesDir by parser.storing("-i", "--input", help="path to dir with java source code files to java2psi extraction")
    val psiDir by parser.storing("-o", "--output", help="path to dir in which will be written java2psi trees")

    Runner.run(sourcesDir, psiDir)
}
