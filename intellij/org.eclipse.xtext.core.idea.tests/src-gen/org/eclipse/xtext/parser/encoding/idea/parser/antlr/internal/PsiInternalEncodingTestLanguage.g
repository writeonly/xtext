/*
 * generated by Xtext
 */
grammar PsiInternalEncodingTestLanguage;

options {
	superClass=AbstractPsiAntlrParser;
}

@lexer::header {
package org.eclipse.xtext.parser.encoding.idea.parser.antlr.internal;

// Hack: Use our own Lexer superclass by means of import. 
// Currently there is no other way to specify the superclass for the lexer.
import org.eclipse.xtext.parser.antlr.Lexer;
}

@parser::header {
package org.eclipse.xtext.parser.encoding.idea.parser.antlr.internal;

import org.eclipse.xtext.idea.parser.AbstractPsiAntlrParser;
import org.eclipse.xtext.parser.encoding.idea.lang.EncodingTestLanguageElementTypeProvider;
import org.eclipse.xtext.idea.parser.TokenTypeProvider;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.encoding.services.EncodingTestLanguageGrammarAccess;

import com.intellij.lang.PsiBuilder;
}

@parser::members {

	protected EncodingTestLanguageGrammarAccess grammarAccess;

	protected EncodingTestLanguageElementTypeProvider elementTypeProvider;

	public PsiInternalEncodingTestLanguageParser(PsiBuilder builder, TokenStream input, EncodingTestLanguageElementTypeProvider elementTypeProvider, EncodingTestLanguageGrammarAccess grammarAccess) {
		this(input);
		setPsiBuilder(builder);
    	this.grammarAccess = grammarAccess;
		this.elementTypeProvider = elementTypeProvider;
	}

	@Override
	protected String getFirstRuleName() {
		return "Model";
	}

}

//Entry rule entryRuleModel
entryRuleModel:
	{ markComposite(elementTypeProvider.getModelElementType()); }
	ruleModel
	{ doneComposite(); }
	EOF;

// Rule Model
ruleModel:
	(
		(
			{
				markComposite(elementTypeProvider.getModel_WordsWordParserRuleCall_0ElementType());
			}
			lv_words_0_0=ruleWord
			{
				doneComposite();
			}
		)
	)*
;

//Entry rule entryRuleWord
entryRuleWord:
	{ markComposite(elementTypeProvider.getWordElementType()); }
	ruleWord
	{ doneComposite(); }
	EOF;

// Rule Word
ruleWord:
	(
		(
			{
				markLeaf();
			}
			lv_value_0_0=RULE_LEXEME
			{
				doneLeaf(lv_value_0_0, elementTypeProvider.getWord_ValueLEXEMETerminalRuleCall_0ElementType());
			}
		)
	)
;

RULE_LEXEME : ('a'..'z'|'A'..'Z'|'0'..'9'|'\u00E4'|'\u00F6'|'\u00FC'|'\u00DF'|'\u00C4'|'\u00D6'|'\u00DC')*;

RULE_WS : (' '|'\t'|'\r'|'\n')+;

RULE_ANY_OTHER : .;