package org.eclipse.xtext.java.tests;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Provider;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.builder.standalone.LanguageAccess;
import org.eclipse.xtext.builder.standalone.incremental.BuildRequest;
import org.eclipse.xtext.builder.standalone.incremental.IncrementalBuilder;
import org.eclipse.xtext.common.types.JvmAnnotationReference;
import org.eclipse.xtext.common.types.JvmAnnotationType;
import org.eclipse.xtext.common.types.JvmFeature;
import org.eclipse.xtext.common.types.JvmFormalParameter;
import org.eclipse.xtext.common.types.JvmGenericType;
import org.eclipse.xtext.common.types.JvmOperation;
import org.eclipse.xtext.common.types.JvmType;
import org.eclipse.xtext.common.types.access.IJvmTypeProvider;
import org.eclipse.xtext.common.types.access.impl.AbstractTypeProviderTest;
import org.eclipse.xtext.common.types.access.jdt.MockJavaProjectProvider;
import org.eclipse.xtext.common.types.testSetups.AbstractMethods;
import org.eclipse.xtext.common.types.testSetups.Bug347739ThreeTypeParamsSuperSuper;
import org.eclipse.xtext.common.types.testSetups.ClassWithVarArgs;
import org.eclipse.xtext.generator.OutputConfiguration;
import org.eclipse.xtext.java.tests.JavaInjectorProvider;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.resource.FileExtensionProvider;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(JavaInjectorProvider.class)
@SuppressWarnings("all")
public class ReusedTypeProviderTest extends AbstractTypeProviderTest {
  @Inject
  private IncrementalBuilder builder;
  
  @Inject
  private IResourceServiceProvider resourceServiceProvider;
  
  @Inject
  private FileExtensionProvider extensionProvider;
  
  @Inject
  private IJvmTypeProvider.Factory typeProviderFactory;
  
  @Inject
  private Provider<XtextResourceSet> resourceSetProvider;
  
  private IJvmTypeProvider typeProvider;
  
  @Override
  public void setUp() throws Exception {
    super.setUp();
    final String pathToSources = "/org/eclipse/xtext/common/types/testSetups";
    final List<String> files = MockJavaProjectProvider.readResource((pathToSources + "/files.list"));
    XtextResourceSet _get = this.resourceSetProvider.get();
    final Procedure1<XtextResourceSet> _function = new Procedure1<XtextResourceSet>() {
      @Override
      public void apply(final XtextResourceSet it) {
        ClassLoader _classLoader = ReusedTypeProviderTest.class.getClassLoader();
        it.setClasspathURIContext(_classLoader);
      }
    };
    final XtextResourceSet resourceSet = ObjectExtensions.<XtextResourceSet>operator_doubleArrow(_get, _function);
    this.typeProviderFactory.createTypeProvider(resourceSet);
    BuildRequest _buildRequest = new BuildRequest();
    final Procedure1<BuildRequest> _function_1 = new Procedure1<BuildRequest>() {
      @Override
      public void apply(final BuildRequest it) {
        Iterable<String> _filterNull = IterableExtensions.<String>filterNull(files);
        for (final String file : _filterNull) {
          {
            final String fullPath = ((pathToSources + "/") + file);
            final URL url = MockJavaProjectProvider.class.getResource(fullPath);
            List<URI> _dirtyFiles = it.getDirtyFiles();
            String _externalForm = url.toExternalForm();
            URI _createURI = URI.createURI(_externalForm);
            _dirtyFiles.add(_createURI);
          }
        }
        it.setResourceSet(resourceSet);
      }
    };
    final BuildRequest buildRequest = ObjectExtensions.<BuildRequest>operator_doubleArrow(_buildRequest, _function_1);
    Set<OutputConfiguration> _emptySet = CollectionLiterals.<OutputConfiguration>emptySet();
    final LanguageAccess languageAccess = new LanguageAccess(_emptySet, this.resourceServiceProvider, true);
    Pair<String, LanguageAccess> _mappedTo = Pair.<String, LanguageAccess>of("txt", languageAccess);
    this.builder.build(buildRequest, Collections.<String, LanguageAccess>unmodifiableMap(CollectionLiterals.<String, LanguageAccess>newHashMap(_mappedTo)));
    IJvmTypeProvider _findTypeProvider = this.typeProviderFactory.findTypeProvider(resourceSet);
    this.typeProvider = _findTypeProvider;
  }
  
  @Override
  protected IJvmTypeProvider getTypeProvider() {
    return this.typeProvider;
  }
  
  @Override
  protected String getCollectionParamName() {
    return "arg0";
  }
  
  @Test
  @Override
  public void testFindTypeByName_AbstractMultimap_02() {
    String typeName = "com.google.common.collect.AbstractMultimap";
    IJvmTypeProvider _typeProvider = this.getTypeProvider();
    JvmType _findTypeByName = _typeProvider.findTypeByName(typeName);
    JvmGenericType type = ((JvmGenericType) _findTypeByName);
    Iterable<JvmFeature> _findAllFeaturesByName = type.findAllFeaturesByName("containsValue");
    JvmFeature _onlyElement = Iterables.<JvmFeature>getOnlyElement(_findAllFeaturesByName);
    JvmOperation containsValue = ((JvmOperation) _onlyElement);
    Assert.assertNotNull(containsValue);
    EList<JvmFormalParameter> _parameters = containsValue.getParameters();
    JvmFormalParameter firstParam = _parameters.get(0);
    EList<JvmAnnotationReference> _annotations = firstParam.getAnnotations();
    int _size = _annotations.size();
    Assert.assertEquals(1, _size);
    EList<JvmAnnotationReference> _annotations_1 = firstParam.getAnnotations();
    JvmAnnotationReference annotationReference = _annotations_1.get(0);
    JvmAnnotationType annotationType = annotationReference.getAnnotation();
    boolean _eIsProxy = annotationType.eIsProxy();
    Assert.assertTrue(_eIsProxy);
    URI _uRI = EcoreUtil.getURI(annotationType);
    URI _trimFragment = _uRI.trimFragment();
    String _string = _trimFragment.toString();
    Assert.assertEquals("java:/Objects/javax.annotation.Nullable", _string);
  }
  
  @Test
  @Override
  public void testParameterNames_01() {
    this.doTestParameterName(Bug347739ThreeTypeParamsSuperSuper.class, "getToken(A)", "arg0");
  }
  
  @Test
  @Override
  public void testParameterNames_02() {
    this.doTestParameterName(AbstractMethods.class, "abstractMethodWithParameter(java.lang.String)", "arg0");
  }
  
  @Test
  @Override
  public void testParameterNames_03() {
    this.doTestParameterName(ClassWithVarArgs.class, "method(java.lang.String[])", "arg0");
  }
}