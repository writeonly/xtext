package org.eclipse.xtext.java.resource;

import com.google.common.base.Objects;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.eclipse.jdt.internal.compiler.batch.CompilationUnit;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileReader;
import org.eclipse.jdt.internal.compiler.env.INameEnvironment;
import org.eclipse.jdt.internal.compiler.env.NameEnvironmentAnswer;
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor;
import org.eclipse.xtext.common.types.TypesPackage;
import org.eclipse.xtext.common.types.descriptions.EObjectDescriptionBasedStubGenerator;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsData;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@FinalFieldsConstructor
@SuppressWarnings("all")
public class IndexAwareNameEnvironment implements INameEnvironment {
  private final ClassLoader classLoader;
  
  private final ResourceDescriptionsData descriptionsData;
  
  private final EObjectDescriptionBasedStubGenerator stubGenerator;
  
  private Map<QualifiedName, NameEnvironmentAnswer> cache = CollectionLiterals.<QualifiedName, NameEnvironmentAnswer>newHashMap();
  
  @Override
  public void cleanup() {
    this.cache.clear();
  }
  
  @Override
  public NameEnvironmentAnswer findType(final char[][] compoundTypeName) {
    final Function1<char[], String> _function = new Function1<char[], String>() {
      @Override
      public String apply(final char[] it) {
        return String.valueOf(it);
      }
    };
    List<String> _map = ListExtensions.<char[], String>map(((List<char[]>)Conversions.doWrapArray(compoundTypeName)), _function);
    final QualifiedName className = QualifiedName.create(_map);
    return this.findType(className);
  }
  
  public NameEnvironmentAnswer findType(final QualifiedName className) {
    try {
      Iterable<IEObjectDescription> _exportedObjects = this.descriptionsData.getExportedObjects(TypesPackage.Literals.JVM_DECLARED_TYPE, className, false);
      final IEObjectDescription candidate = IterableExtensions.<IEObjectDescription>head(_exportedObjects);
      NameEnvironmentAnswer result = null;
      boolean _notEquals = (!Objects.equal(candidate, null));
      if (_notEquals) {
        final String source = this.stubGenerator.getJavaStubSource(candidate);
        char[] _charArray = source.toCharArray();
        String _string = className.toString("/");
        String _plus = (_string + ".java");
        CompilationUnit _compilationUnit = new CompilationUnit(_charArray, _plus, "UTF-8");
        NameEnvironmentAnswer _nameEnvironmentAnswer = new NameEnvironmentAnswer(_compilationUnit, null);
        result = _nameEnvironmentAnswer;
      } else {
        String _string_1 = className.toString("/");
        final String fileName = (_string_1 + ".class");
        boolean _containsKey = this.cache.containsKey(fileName);
        if (_containsKey) {
          return this.cache.get(fileName);
        }
        final URL url = this.classLoader.getResource(fileName);
        boolean _equals = Objects.equal(url, null);
        if (_equals) {
          this.cache.put(className, null);
          return null;
        }
        InputStream _openStream = url.openStream();
        final ClassFileReader reader = ClassFileReader.read(_openStream, fileName);
        NameEnvironmentAnswer _nameEnvironmentAnswer_1 = new NameEnvironmentAnswer(reader, null);
        result = _nameEnvironmentAnswer_1;
      }
      this.cache.put(className, result);
      return result;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  public NameEnvironmentAnswer findType(final char[] typeName, final char[][] packageName) {
    final Function1<char[], String> _function = new Function1<char[], String>() {
      @Override
      public String apply(final char[] it) {
        return String.valueOf(it);
      }
    };
    List<String> _map = ListExtensions.<char[], String>map(((List<char[]>)Conversions.doWrapArray(packageName)), _function);
    final ArrayList<String> list = new ArrayList<String>(_map);
    String _valueOf = String.valueOf(typeName);
    list.add(_valueOf);
    final QualifiedName className = QualifiedName.create(list);
    return this.findType(className);
  }
  
  @Override
  public boolean isPackage(final char[][] parentPackageName, final char[] packageName) {
    Character _head = IterableExtensions.<Character>head(((Iterable<Character>)Conversions.doWrapArray(packageName)));
    return Character.isLowerCase((_head).charValue());
  }
  
  public IndexAwareNameEnvironment(final ClassLoader classLoader, final ResourceDescriptionsData descriptionsData, final EObjectDescriptionBasedStubGenerator stubGenerator) {
    super();
    this.classLoader = classLoader;
    this.descriptionsData = descriptionsData;
    this.stubGenerator = stubGenerator;
  }
}