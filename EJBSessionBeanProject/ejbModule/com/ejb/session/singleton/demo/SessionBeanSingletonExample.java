package com.ejb.session.singleton.demo;

import static javax.ejb.ConcurrencyManagementType.BEAN;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Session Bean implementation class SessionBeanSingletonExample.
 * 
 * Here we see a bean that uses the Bean-Managed Concurrency option as well as
 * the @Startup annotation which causes the bean to be instantiated by the
 * container when the application starts. Singleton beans with
 * @ConcurrencyManagement(BEAN) are responsible for their own thread-safety. The
 * bean shown is a simple properties "registry" and provides a place where
 * options could be set and retrieved by all beans in the application.
 */
@Singleton
@Startup
@ConcurrencyManagement(BEAN)
public class SessionBeanSingletonExample implements
		SessionBeanSingletonExampleRemote, SessionBeanSingletonExampleLocal {

	/**
	 * Default constructor.
	 */
	public SessionBeanSingletonExample() {
		// TODO Auto-generated constructor stub
	}

	// Note the java.util.Properties object is a thread-safe
	// collections that uses synchronization. If it didn't
	// you would have to use some form of synchronization
	// to ensure the PropertyRegistryBean is thread-safe.
	private final Properties properties = new Properties();

	// The @Startup annotation ensures that this method is
	// called when the application starts up.
	@PostConstruct
	public void applicationStartup() {
		properties.putAll(System.getProperties());
	}

	@PreDestroy
	public void applicationShutdown() {
		properties.clear();
	}

	public String getProperty(final String key) {
		return properties.getProperty(key);
	}

	public String setProperty(final String key, final String value) {
		return (String) properties.setProperty(key, value);
	}

	public String removeProperty(final String key) {
		return (String) properties.remove(key);
	}

}
