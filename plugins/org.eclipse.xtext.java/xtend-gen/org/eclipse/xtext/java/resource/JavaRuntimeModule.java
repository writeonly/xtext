package org.eclipse.xtext.java.resource;

import com.google.inject.AbstractModule;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.Constants;
import org.eclipse.xtext.common.types.access.ClasspathTypeProviderFactory;
import org.eclipse.xtext.common.types.access.IJvmTypeProvider;
import org.eclipse.xtext.common.types.xtext.JvmIdentifiableQualifiedNameProvider;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.java.resource.JavaResource;
import org.eclipse.xtext.java.resource.JavaResourceDescriptionManager;
import org.eclipse.xtext.java.resource.JavaResourceServiceProvider;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.parser.IEncodingProvider;
import org.eclipse.xtext.parser.antlr.IReferableElementsUnloader;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.impl.SimpleResourceDescriptionsBasedContainerManager;
import org.eclipse.xtext.validation.IResourceValidator;

@SuppressWarnings("all")
public class JavaRuntimeModule extends AbstractModule {
  @Override
  protected void configure() {
    AnnotatedBindingBuilder<Resource.Factory> _bind = this.<Resource.Factory>bind(Resource.Factory.class);
    _bind.to(JavaResource.Factory.class);
    AnnotatedBindingBuilder<IResourceValidator> _bind_1 = this.<IResourceValidator>bind(IResourceValidator.class);
    _bind_1.toInstance(IResourceValidator.NULL);
    AnnotatedBindingBuilder<IGenerator> _bind_2 = this.<IGenerator>bind(IGenerator.class);
    _bind_2.to(IGenerator.NullGenerator.class);
    AnnotatedBindingBuilder<IEncodingProvider> _bind_3 = this.<IEncodingProvider>bind(IEncodingProvider.class);
    _bind_3.to(IEncodingProvider.Runtime.class);
    AnnotatedBindingBuilder<IResourceServiceProvider> _bind_4 = this.<IResourceServiceProvider>bind(IResourceServiceProvider.class);
    _bind_4.to(JavaResourceServiceProvider.class);
    AnnotatedBindingBuilder<IContainer.Manager> _bind_5 = this.<IContainer.Manager>bind(IContainer.Manager.class);
    _bind_5.to(SimpleResourceDescriptionsBasedContainerManager.class);
    AnnotatedBindingBuilder<IResourceDescription.Manager> _bind_6 = this.<IResourceDescription.Manager>bind(IResourceDescription.Manager.class);
    _bind_6.to(JavaResourceDescriptionManager.class);
    AnnotatedBindingBuilder<IQualifiedNameProvider> _bind_7 = this.<IQualifiedNameProvider>bind(IQualifiedNameProvider.class);
    _bind_7.to(JvmIdentifiableQualifiedNameProvider.class);
    AnnotatedBindingBuilder<String> _bind_8 = this.<String>bind(String.class);
    Named _named = Names.named(Constants.FILE_EXTENSIONS);
    LinkedBindingBuilder<String> _annotatedWith = _bind_8.annotatedWith(_named);
    _annotatedWith.toInstance("java");
    AnnotatedBindingBuilder<IJvmTypeProvider.Factory> _bind_9 = this.<IJvmTypeProvider.Factory>bind(IJvmTypeProvider.Factory.class);
    _bind_9.to(ClasspathTypeProviderFactory.class);
    AnnotatedBindingBuilder<ClassLoader> _bind_10 = this.<ClassLoader>bind(ClassLoader.class);
    ClassLoader _classLoader = JavaRuntimeModule.class.getClassLoader();
    _bind_10.toInstance(_classLoader);
    AnnotatedBindingBuilder<IReferableElementsUnloader> _bind_11 = this.<IReferableElementsUnloader>bind(IReferableElementsUnloader.class);
    _bind_11.to(IReferableElementsUnloader.GenericUnloader.class);
  }
}