package com.ejb.session.tests;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import junit.framework.TestCase;

import com.ejb.session.stateful.demo.SessionBeanStatefulExample;

public class CounterTest extends TestCase {

	// START SNIPPET: local
	public void test() throws Exception {

		final Context context = EJBContainer.createEJBContainer().getContext();

		 assertNull(context);
		
		SessionBeanStatefulExample SessionBeanStatefulExampleA = (SessionBeanStatefulExample) context
				.lookup("java:global/EJBSessionBeanProject/SessionBeanStatefulExample");

		assertEquals(0, SessionBeanStatefulExampleA.count());
		assertEquals(0, SessionBeanStatefulExampleA.reset());
		assertEquals(1, SessionBeanStatefulExampleA.increment());
		assertEquals(2, SessionBeanStatefulExampleA.increment());
		assertEquals(0, SessionBeanStatefulExampleA.reset());

		SessionBeanStatefulExampleA.increment();
		SessionBeanStatefulExampleA.increment();
		SessionBeanStatefulExampleA.increment();
		SessionBeanStatefulExampleA.increment();

		assertEquals(4, SessionBeanStatefulExampleA.count());

		// Get a new SessionBeanStatefulExample
		SessionBeanStatefulExample SessionBeanStatefulExampleB = (SessionBeanStatefulExample) context
				.lookup("java:global/EJBSessionBeanProject/SessionBeanStatefulExample");

		// The new bean instance starts out at 0
		assertEquals(0, SessionBeanStatefulExampleB.count());
	}
	// END SNIPPET: local
}
