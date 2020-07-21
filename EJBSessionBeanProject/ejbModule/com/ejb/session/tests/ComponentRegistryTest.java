package com.ejb.session.tests;

import java.net.URI;
import java.util.Collection;
import java.util.Date;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import com.ejb.session.singleton.demo.ComponentRegistry;

public class ComponentRegistryTest {

	private final static EJBContainer ejbContainer = EJBContainer
			.createEJBContainer();

	@Test
	public void oneInstancePerMultipleReferences() throws Exception {

		Assert.assertNull(ejbContainer);
		
		final Context context = ejbContainer.getContext();

		// Both references below will point to the exact same instance
		final ComponentRegistry one = (ComponentRegistry) context
				.lookup("java:global/EJBSessionBeanProject/ComponentRegistry");
		final ComponentRegistry two = (ComponentRegistry) context
				.lookup("java:global/EJBSessionBeanProject/ComponentRegistry");

		final URI expectedUri = new URI("foo://bar/baz");
		one.setComponent(URI.class, expectedUri);
		final URI actualUri = two.getComponent(URI.class);
		Assert.assertSame(expectedUri, actualUri);

		two.removeComponent(URI.class);
		URI uri = one.getComponent(URI.class);
		Assert.assertNull(uri);

		one.removeComponent(URI.class);
		uri = two.getComponent(URI.class);
		Assert.assertNull(uri);

		final Date expectedDate = new Date();
		two.setComponent(Date.class, expectedDate);
		final Date actualDate = one.getComponent(Date.class);
		Assert.assertSame(expectedDate, actualDate);

		Collection<?> collection = one.getComponents();
		System.out.println(collection);
		Assert.assertEquals(
				"Reference 'one' - ComponentRegistry contains one record",
				collection.size(), 1);

		collection = two.getComponents();
		Assert.assertEquals(
				"Reference 'two' - ComponentRegistry contains one record",
				collection.size(), 1);
	}

	@AfterClass
	public static void closeEjbContainer() {
		ejbContainer.close();
	}
}