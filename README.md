# java-psi-extractor

PSI tree extraction from java source code

## Program use

You run the program on the folder with java source code files you are interested. The program analyzes only files with the `java` extension.

The program parse the code using [JavaParserDefenition](https://github.com/JetBrains/intellij-community/blob/master/java/java-psi-impl/src/com/intellij/lang/java/JavaParserDefinition.java) from [intellij-community](https://github.com/JetBrains/intellij-community).

The program also uses [Grammar-Kit](https://github.com/JetBrains/Grammar-Kit) to prepare configuration and infrastructure for intellij-community (creating mock project, java language registration, etc).
