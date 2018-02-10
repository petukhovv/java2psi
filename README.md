# java2psi

PSI tree extraction from java source code

## Program use

You run the program on the folder with java source code files you are interested. The program analyzes only files with the `java` extension.

The program parse the code using [JavaParserDefenition](https://github.com/JetBrains/intellij-community/blob/master/java/java-psi-impl/src/com/intellij/lang/java/JavaParserDefinition.java) from [intellij-community](https://github.com/JetBrains/intellij-community).

The program also uses [Grammar-Kit](https://github.com/JetBrains/Grammar-Kit) to prepare configuration and infrastructure for intellij-community (creating mock project, java language registration, etc).

### Program arguments

* `-i` or `--input`: path to dir with java source code files to psi extraction;
* `-o` or `--output`: path to dir in which will be written psi trees.

### How to run

Before run program you must add a library to the project `lib/grammar-kit-2017.1.1.jar` and also make sure that you use IntelliJ IDEA Community Edition as project SDK.

To run program you must run `main` function in `main.kt`, not forgetting to set the program arguments.
