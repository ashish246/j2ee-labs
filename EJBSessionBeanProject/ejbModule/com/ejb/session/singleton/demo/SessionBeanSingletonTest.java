package com.ejb.session.singleton.demo;

import java.net.URI;
import java.util.Collection;
import java.util.Date;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

public class SessionBeanSingletonTest {

	private final static EJBContainer ejbContainer = EJBContainer
			.createEJBContainer();

	public static void main(String[] args) throws Exception {
		componentRegistryTest();

		SessionBeanSingletonExampleTest();

		ejbContainer.close();
	}

	public static void SessionBeanSingletonExampleTest() throws Exception {

		final Context context = ejbContainer.getContext();

		final SessionBeanSingletonExample one = (SessionBeanSingletonExample) context
				.lookup("java:global/EJBSessionBeanProject/SessionBeanSingletonExample");
		final SessionBeanSingletonExample two = (SessionBeanSingletonExample) context
				.lookup("java:global/EJBSessionBeanProject/SessionBeanSingletonExample");

		one.setProperty("url", "http://superbiz.org");
		String url = two.getProperty("url");
		// Assert.assertSame("http://superbiz.org", url);
		System.out.println("url ->" + url);

		two.removeProperty("url");
		url = one.getProperty("url");
		// Assert.assertNull(url);

		two.setProperty("version", "1.0.5");
		String version = one.getProperty("version");
		// Assert.assertSame("1.0.5", version);

		one.removeProperty("version");
		version = two.getProperty("version");
		// Assert.assertNull(version);
	}

	public static void componentRegistryTest() throws Exception {

		final Context context = ejbContainer.getContext();

		// Both references below will point to the exact same instance
		final ComponentRegistry one = (ComponentRegistry) context
				.lookup("java:global/EJBSessionBeanProject/ComponentRegistry");
		final ComponentRegistry two = (ComponentRegistry) context
				.lookup("java:global/EJBSessionBeanProject/ComponentRegistry");

		final URI expectedUri = new URI("foo://bar/baz");
		one.setComponent(URI.class, expectedUri);
		final URI actualUri = two.getComponent(URI.class);

		System.out.println("expectedUri ->" + expectedUri);
		System.out.println("actualUri ->" + actualUri);

		two.removeComponent(URI.class);
		URI uri = one.getComponent(URI.class);
		System.out.println("uri after removal ->" + uri);

		one.removeComponent(URI.class);
		uri = two.getComponent(URI.class);
		// Assert.assertNull(uri);

		final Date expectedDate = new Date();
		two.setComponent(Date.class, expectedDate);
		final Date actualDate = one.getComponent(Date.class);
		// Assert.assertSame(expectedDate, actualDate);

		Collection<?> collection = one.getComponents();
		System.out.println(collection);
		// Assert.assertEquals("Reference 'one' - ComponentRegistry contains one record",
		// collection.size(), 1);
		System.out.println("size ->" + collection.size());

		collection = two.getComponents();
		System.out.println("size ->" + collection.size());
		// Assert.assertEquals("Reference 'two' - ComponentRegistry contains one record",
		// collection.size(), 1);
	}

}
