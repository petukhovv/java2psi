//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.intellij.lang.java.custom;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LanguageUtil;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lang.java.lexer.JavaDocLexer;
import com.intellij.lang.java.lexer.JavaLexer;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.LanguageLevelProjectExtension;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.JavaDocTokenType;
import com.intellij.psi.JavaTokenType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.impl.java.stubs.JavaStubElementType;
import com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import com.intellij.psi.impl.source.tree.ElementType;
import com.intellij.psi.impl.source.tree.JavaElementType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.psi.tree.java.IJavaDocElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JavaParserDefinition implements ParserDefinition {
    public JavaParserDefinition() {
    }

    @NotNull
    public Lexer createLexer(@Nullable Project project) {
        LanguageLevel level = project != null ? LanguageLevelProjectExtension.getInstance(project).getLanguageLevel() : LanguageLevel.HIGHEST;
        Lexer var10000 = createLexer(level);
        if (var10000 == null) {
            return null;
        }

        return var10000;
    }

    @NotNull
    public static Lexer createLexer(@NotNull LanguageLevel level) {
        if (level == null) {
            return null;
        }

        JavaLexer var10000 = new JavaLexer(level);
        if (var10000 == null) {
            return null;
        }

        return var10000;
    }

    @NotNull
    public static Lexer createDocLexer(@NotNull LanguageLevel level) {
        if (level == null) {
            return null;
        }

        JavaDocLexer var10000 = new JavaDocLexer(level);
        if (var10000 == null) {
            return null;
        }

        return var10000;
    }

    public IFileElementType getFileNodeType() {
        return JavaStubElementTypes.JAVA_FILE;
    }

    @NotNull
    public TokenSet getWhitespaceTokens() {
        TokenSet var10000 = ElementType.JAVA_WHITESPACE_BIT_SET;
        if (ElementType.JAVA_WHITESPACE_BIT_SET == null) {
            return null;
        }

        return var10000;
    }

    @NotNull
    public TokenSet getCommentTokens() {
        TokenSet var10000 = ElementType.JAVA_COMMENT_BIT_SET;
        if (ElementType.JAVA_COMMENT_BIT_SET == null) {
            return null;
        }

        return var10000;
    }

    @NotNull
    public TokenSet getStringLiteralElements() {
        TokenSet var10000 = TokenSet.create(new IElementType[]{JavaElementType.LITERAL_EXPRESSION});
        if (var10000 == null) {
            return null;
        }

        return var10000;
    }

    @NotNull
    public PsiParser createParser(Project project) {
        throw new UnsupportedOperationException("Should not be called directly");
    }

    @NotNull
    public PsiElement createElement(ASTNode node) {
        IElementType type = node.getElementType();
        if (type instanceof JavaStubElementType) {
            PsiElement var10000 = ((JavaStubElementType)type).createPsi(node);
            if (var10000 == null) {
                return null;
            }

            return var10000;
        } else if (type instanceof IJavaDocElementType) {
            return node.getTreePrev().getPsi();
        } else {
            throw new IllegalStateException("Incorrect node for JavaParserDefinition: " + node + " (" + type + ")");
        }
    }

    public PsiFile createFile(FileViewProvider viewProvider) {
        return new PsiJavaFileImpl(viewProvider);
    }

    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        if (right.getElementType() != JavaDocTokenType.DOC_TAG_VALUE_SHARP_TOKEN && left.getElementType() != JavaDocTokenType.DOC_TAG_VALUE_SHARP_TOKEN) {
            PsiFile containingFile = left.getTreeParent().getPsi().getContainingFile();
            LanguageLevel level = containingFile instanceof PsiJavaFile ? ((PsiJavaFile)containingFile).getLanguageLevel() : LanguageLevel.HIGHEST;
            Lexer lexer = createLexer(level);
            SpaceRequirements spaceRequirements = LanguageUtil.canStickTokensTogetherByLexer(left, right, lexer);
            if (left.getElementType() == JavaTokenType.END_OF_LINE_COMMENT) {
                return SpaceRequirements.MUST_LINE_BREAK;
            } else {
                String text;
                if (left.getElementType() == JavaDocTokenType.DOC_COMMENT_DATA) {
                    text = left.getText();
                    if (text.length() > 0 && Character.isWhitespace(text.charAt(text.length() - 1))) {
                        return SpaceRequirements.MAY;
                    }
                }

                if (right.getElementType() == JavaDocTokenType.DOC_COMMENT_DATA) {
                    text = right.getText();
                    if (text.length() > 0 && Character.isWhitespace(text.charAt(0))) {
                        return SpaceRequirements.MAY;
                    }
                } else if (right.getElementType() == JavaDocTokenType.DOC_INLINE_TAG_END) {
                    return SpaceRequirements.MAY;
                }

                return spaceRequirements;
            }
        } else {
            return SpaceRequirements.MUST_NOT;
        }
    }
}
