// Generated from /Users/dasouche1/IdeaProjects/test/common/src/main/java/com/test/antlr/sandy/SandyParser.g4 by ANTLR 4.5.3
package com.test.antlr.sandy;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SandyParserParser}.
 */
public interface SandyParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SandyParserParser#sandyFile}.
	 * @param ctx the parse tree
	 */
	void enterSandyFile(SandyParserParser.SandyFileContext ctx);
	/**
	 * Exit a parse tree produced by {@link SandyParserParser#sandyFile}.
	 * @param ctx the parse tree
	 */
	void exitSandyFile(SandyParserParser.SandyFileContext ctx);
	/**
	 * Enter a parse tree produced by {@link SandyParserParser#line}.
	 * @param ctx the parse tree
	 */
	void enterLine(SandyParserParser.LineContext ctx);
	/**
	 * Exit a parse tree produced by {@link SandyParserParser#line}.
	 * @param ctx the parse tree
	 */
	void exitLine(SandyParserParser.LineContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varDeclarationStatement}
	 * labeled alternative in {@link SandyParserParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclarationStatement(SandyParserParser.VarDeclarationStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varDeclarationStatement}
	 * labeled alternative in {@link SandyParserParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclarationStatement(SandyParserParser.VarDeclarationStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignmentStatement}
	 * labeled alternative in {@link SandyParserParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentStatement(SandyParserParser.AssignmentStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignmentStatement}
	 * labeled alternative in {@link SandyParserParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentStatement(SandyParserParser.AssignmentStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link SandyParserParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclaration(SandyParserParser.VarDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SandyParserParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclaration(SandyParserParser.VarDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SandyParserParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(SandyParserParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link SandyParserParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(SandyParserParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code decimalLiteral}
	 * labeled alternative in {@link SandyParserParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterDecimalLiteral(SandyParserParser.DecimalLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code decimalLiteral}
	 * labeled alternative in {@link SandyParserParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitDecimalLiteral(SandyParserParser.DecimalLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code minusExpression}
	 * labeled alternative in {@link SandyParserParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMinusExpression(SandyParserParser.MinusExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code minusExpression}
	 * labeled alternative in {@link SandyParserParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMinusExpression(SandyParserParser.MinusExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intLiteral}
	 * labeled alternative in {@link SandyParserParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIntLiteral(SandyParserParser.IntLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intLiteral}
	 * labeled alternative in {@link SandyParserParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIntLiteral(SandyParserParser.IntLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenExpression}
	 * labeled alternative in {@link SandyParserParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParenExpression(SandyParserParser.ParenExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenExpression}
	 * labeled alternative in {@link SandyParserParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParenExpression(SandyParserParser.ParenExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binaryOperation}
	 * labeled alternative in {@link SandyParserParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBinaryOperation(SandyParserParser.BinaryOperationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binaryOperation}
	 * labeled alternative in {@link SandyParserParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBinaryOperation(SandyParserParser.BinaryOperationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varReference}
	 * labeled alternative in {@link SandyParserParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterVarReference(SandyParserParser.VarReferenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varReference}
	 * labeled alternative in {@link SandyParserParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitVarReference(SandyParserParser.VarReferenceContext ctx);
}