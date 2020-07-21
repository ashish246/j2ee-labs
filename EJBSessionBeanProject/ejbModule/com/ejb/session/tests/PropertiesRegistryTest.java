package com.ejb.session.tests;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.ejb.session.singleton.demo.SessionBeanSingletonExample;

public class PropertiesRegistryTest {

	private final static EJBContainer ejbContainer = EJBContainer
			.createEJBContainer();

	@Test
	public void oneInstancePerMultipleReferences() throws Exception {

		Assert.assertNull(ejbContainer);
		
		final Context context = ejbContainer.getContext();

		final SessionBeanSingletonExample one = (SessionBeanSingletonExample) context
				.lookup("java:global/EJBSessionBeanProject/SessionBeanSingletonExample");
		final SessionBeanSingletonExample two = (SessionBeanSingletonExample) context
				.lookup("java:global/EJBSessionBeanProject/SessionBeanSingletonExample");

		one.setProperty("url", "http://superbiz.org");
		String url = two.getProperty("url");
		Assert.assertSame("http://superbiz.org", url);

		two.removeProperty("url");
		url = one.getProperty("url");
		Assert.assertNull(url);

		two.setProperty("version", "1.0.5");
		String version = one.getProperty("version");
		Assert.assertSame("1.0.5", version);

		one.removeProperty("version");
		version = two.getProperty("version");
		Assert.assertNull(version);
	}

	@AfterClass
	public static void closeEjbContainer() {
		ejbContainer.close();
	}
}