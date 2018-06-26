// Generated from /Users/dasouche1/IdeaProjects/test/common/src/main/java/com/test/antlr/idata/IData.g4 by ANTLR 4.5.1
package com.test.antlr.idata;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link IDataParser}.
 */
public interface IDataListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link IDataParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(IDataParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link IDataParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(IDataParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link IDataParser#group}.
	 * @param ctx the parse tree
	 */
	void enterGroup(IDataParser.GroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link IDataParser#group}.
	 * @param ctx the parse tree
	 */
	void exitGroup(IDataParser.GroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link IDataParser#sequence}.
	 * @param ctx the parse tree
	 */
	void enterSequence(IDataParser.SequenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link IDataParser#sequence}.
	 * @param ctx the parse tree
	 */
	void exitSequence(IDataParser.SequenceContext ctx);
}