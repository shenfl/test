// Generated from /Users/dasouche1/IdeaProjects/test/common/src/main/java/com/test/antlr/sandy/SandyParser.g4 by ANTLR 4.5.3
package com.test.antlr.sandy;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SandyParserLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		NEWLINE=1, WS=2, VAR=3, INTLIT=4, DECLIT=5, PLUS=6, MINUS=7, ASTERISK=8, 
		DIVISION=9, ASSIGN=10, LPAREN=11, RPAREN=12, ID=13;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"NEWLINE", "WS", "VAR", "INTLIT", "DECLIT", "PLUS", "MINUS", "ASTERISK", 
		"DIVISION", "ASSIGN", "LPAREN", "RPAREN", "ID"
	};

	private static final String[] _LITERAL_NAMES = {
		null, null, null, "'var'", null, null, "'+'", "'-'", "'*'", "'/'", "'='", 
		"'('", "')'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "NEWLINE", "WS", "VAR", "INTLIT", "DECLIT", "PLUS", "MINUS", "ASTERISK", 
		"DIVISION", "ASSIGN", "LPAREN", "RPAREN", "ID"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public SandyParserLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "SandyParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\17`\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\3\2\5\2!\n\2\3\3\6\3$\n\3\r\3\16"+
		"\3%\3\4\3\4\3\4\3\4\3\5\3\5\3\5\7\5/\n\5\f\5\16\5\62\13\5\5\5\64\n\5\3"+
		"\6\3\6\3\6\7\69\n\6\f\6\16\6<\13\6\3\6\3\6\6\6@\n\6\r\6\16\6A\5\6D\n\6"+
		"\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\7\16U"+
		"\n\16\f\16\16\16X\13\16\3\16\3\16\7\16\\\n\16\f\16\16\16_\13\16\2\2\17"+
		"\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\3\2\t"+
		"\4\2\f\ftt\4\2\13\13\"\"\3\2\63;\3\2\62;\3\2aa\3\2c|\6\2\62;C\\aac|h\2"+
		"\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2"+
		"\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2"+
		"\31\3\2\2\2\2\33\3\2\2\2\3 \3\2\2\2\5#\3\2\2\2\7\'\3\2\2\2\t\63\3\2\2"+
		"\2\13C\3\2\2\2\rE\3\2\2\2\17G\3\2\2\2\21I\3\2\2\2\23K\3\2\2\2\25M\3\2"+
		"\2\2\27O\3\2\2\2\31Q\3\2\2\2\33V\3\2\2\2\35\36\7\17\2\2\36!\7\f\2\2\37"+
		"!\t\2\2\2 \35\3\2\2\2 \37\3\2\2\2!\4\3\2\2\2\"$\t\3\2\2#\"\3\2\2\2$%\3"+
		"\2\2\2%#\3\2\2\2%&\3\2\2\2&\6\3\2\2\2\'(\7x\2\2()\7c\2\2)*\7t\2\2*\b\3"+
		"\2\2\2+\64\7\62\2\2,\60\t\4\2\2-/\t\5\2\2.-\3\2\2\2/\62\3\2\2\2\60.\3"+
		"\2\2\2\60\61\3\2\2\2\61\64\3\2\2\2\62\60\3\2\2\2\63+\3\2\2\2\63,\3\2\2"+
		"\2\64\n\3\2\2\2\65D\7\62\2\2\66:\t\4\2\2\679\t\5\2\28\67\3\2\2\29<\3\2"+
		"\2\2:8\3\2\2\2:;\3\2\2\2;=\3\2\2\2<:\3\2\2\2=?\7\60\2\2>@\t\5\2\2?>\3"+
		"\2\2\2@A\3\2\2\2A?\3\2\2\2AB\3\2\2\2BD\3\2\2\2C\65\3\2\2\2C\66\3\2\2\2"+
		"D\f\3\2\2\2EF\7-\2\2F\16\3\2\2\2GH\7/\2\2H\20\3\2\2\2IJ\7,\2\2J\22\3\2"+
		"\2\2KL\7\61\2\2L\24\3\2\2\2MN\7?\2\2N\26\3\2\2\2OP\7*\2\2P\30\3\2\2\2"+
		"QR\7+\2\2R\32\3\2\2\2SU\t\6\2\2TS\3\2\2\2UX\3\2\2\2VT\3\2\2\2VW\3\2\2"+
		"\2WY\3\2\2\2XV\3\2\2\2Y]\t\7\2\2Z\\\t\b\2\2[Z\3\2\2\2\\_\3\2\2\2][\3\2"+
		"\2\2]^\3\2\2\2^\34\3\2\2\2_]\3\2\2\2\f\2 %\60\63:ACV]\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}