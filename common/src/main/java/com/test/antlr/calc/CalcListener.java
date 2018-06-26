// Generated from /Users/dasouche1/IdeaProjects/test/common/src/main/java/com/test/antlr/calc/Calc.g4 by ANTLR 4.5.3
package com.test.antlr.calc;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CalcParser}.
 */
public interface CalcListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CalcParser#exprs}.
	 * @param ctx the parse tree
	 */
	void enterExprs(CalcParser.ExprsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalcParser#exprs}.
	 * @param ctx the parse tree
	 */
	void exitExprs(CalcParser.ExprsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalcParser#setExpr}.
	 * @param ctx the parse tree
	 */
	void enterSetExpr(CalcParser.SetExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalcParser#setExpr}.
	 * @param ctx the parse tree
	 */
	void exitSetExpr(CalcParser.SetExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalcParser#agmts}.
	 * @param ctx the parse tree
	 */
	void enterAgmts(CalcParser.AgmtsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalcParser#agmts}.
	 * @param ctx the parse tree
	 */
	void exitAgmts(CalcParser.AgmtsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalcParser#agmt}.
	 * @param ctx the parse tree
	 */
	void enterAgmt(CalcParser.AgmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalcParser#agmt}.
	 * @param ctx the parse tree
	 */
	void exitAgmt(CalcParser.AgmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalcParser#calcExpr}.
	 * @param ctx the parse tree
	 */
	void enterCalcExpr(CalcParser.CalcExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalcParser#calcExpr}.
	 * @param ctx the parse tree
	 */
	void exitCalcExpr(CalcParser.CalcExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(CalcParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalcParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(CalcParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalcParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(CalcParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalcParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(CalcParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalcParser#funCall}.
	 * @param ctx the parse tree
	 */
	void enterFunCall(CalcParser.FunCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalcParser#funCall}.
	 * @param ctx the parse tree
	 */
	void exitFunCall(CalcParser.FunCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link CalcParser#params}.
	 * @param ctx the parse tree
	 */
	void enterParams(CalcParser.ParamsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CalcParser#params}.
	 * @param ctx the parse tree
	 */
	void exitParams(CalcParser.ParamsContext ctx);
}