/*
 * generated by Xtext
 */
package org.eclipse.xtext.parser.parameters.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.parameters.parametersTestLanguage.ParametersTestLanguagePackage;
import org.eclipse.xtext.parser.parameters.parametersTestLanguage.ParserRuleParameters;
import org.eclipse.xtext.parser.parameters.parametersTestLanguage.Scenario;
import org.eclipse.xtext.parser.parameters.serializer.ParametersTestLanguageSemanticSequencer;
import org.eclipse.xtext.parser.parameters.services.TwoParametersTestLanguageGrammarAccess;
import org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor;
import org.eclipse.xtext.serializer.diagnostic.ISemanticSequencerDiagnosticProvider;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.GenericSequencer;
import org.eclipse.xtext.serializer.sequencer.ISemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService;

@SuppressWarnings("all")
public class TwoParametersTestLanguageSemanticSequencer extends ParametersTestLanguageSemanticSequencer {

	@Inject
	private TwoParametersTestLanguageGrammarAccess grammarAccess;
	
	@Override
	public void createSequence(EObject context, EObject semanticObject) {
		if(semanticObject.eClass().getEPackage() == ParametersTestLanguagePackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case ParametersTestLanguagePackage.PARSER_RULE_PARAMETERS:
				sequence_ParserRuleParameters(context, (ParserRuleParameters) semanticObject); 
				return; 
			case ParametersTestLanguagePackage.SCENARIO:
				if(context == grammarAccess.getScenario1Rule()) {
					sequence_Scenario1(context, (Scenario) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getScenario2Rule()) {
					sequence_Scenario2(context, (Scenario) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getScenario3Rule()) {
					sequence_Scenario3(context, (Scenario) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getScenario4Rule()) {
					sequence_Scenario4(context, (Scenario) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getScenario5Rule()) {
					sequence_Scenario5(context, (Scenario) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getScenario6Rule()) {
					sequence_Scenario6(context, (Scenario) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getScenario7Rule()) {
					sequence_Scenario7(context, (Scenario) semanticObject); 
					return; 
				}
				else if(context == grammarAccess.getScenario8Rule()) {
					sequence_Scenario8(context, (Scenario) semanticObject); 
					return; 
				}
				else break;
			}
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Constraint:
	 *     (
	 *         scenario=Scenario5 | 
	 *         scenario=Scenario5 | 
	 *         scenario=Scenario6 | 
	 *         scenario=Scenario6 | 
	 *         scenario=Scenario6 | 
	 *         scenario=Scenario6 | 
	 *         scenario=Scenario7 | 
	 *         scenario=Scenario7 | 
	 *         scenario=Scenario8 | 
	 *         scenario=Scenario6 | 
	 *         scenario=Scenario8 | 
	 *         scenario=Scenario6 | 
	 *         scenario=Scenario8 | 
	 *         scenario=Scenario6 | 
	 *         scenario=Scenario8 | 
	 *         scenario=Scenario6
	 *     )
	 */
	protected void sequence_ParserRuleParameters(EObject context, ParserRuleParameters semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (first=ID | second=ID)
	 */
	protected void sequence_Scenario5(EObject context, Scenario semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     first=IdOrKeyword2
	 */
	protected void sequence_Scenario6(EObject context, Scenario semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     (first=IdOrKeyword2 | second='keyword')
	 */
	protected void sequence_Scenario7(EObject context, Scenario semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * Constraint:
	 *     second=IdOrKeyword2
	 */
	protected void sequence_Scenario8(EObject context, Scenario semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
}