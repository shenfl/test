// Generated from /Users/shenfl/IdeaProjects/test/common/src/main/java/com/test/antlr/rows/Rows.g4 by ANTLR 4.5.3
package com.test.antlr.rows;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RowsParser}.
 */
public interface RowsListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link RowsParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(RowsParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link RowsParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(RowsParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link RowsParser#row}.
	 * @param ctx the parse tree
	 */
	void enterRow(RowsParser.RowContext ctx);
	/**
	 * Exit a parse tree produced by {@link RowsParser#row}.
	 * @param ctx the parse tree
	 */
	void exitRow(RowsParser.RowContext ctx);
}