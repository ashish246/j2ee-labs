package com.ejb.session.stateful.demo;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

public class SessionBeanStatefulTest {

	public static void main(String[] args) throws NamingException {

		final Context context = EJBContainer.createEJBContainer().getContext();

		SessionBeanStatefulExample counterA = (SessionBeanStatefulExample) context
				.lookup("java:global/EJBSessionBeanProject/SessionBeanStatefulExample");

		// assertEquals(0, counterA.count());
		// assertEquals(0, counterA.reset());
		// assertEquals(1, counterA.increment());
		// assertEquals(2, counterA.increment());
		// assertEquals(0, counterA.reset());

		System.out.println("Counter ->" + counterA.count());

		counterA.increment();
		counterA.increment();
		counterA.increment();
		counterA.increment();

		System.out.println("Counter ->" + counterA.count());
		// assertEquals(4, counterA.count());

		// Get a new counter
		SessionBeanStatefulExample counterB = (SessionBeanStatefulExample) context
				.lookup("java:global/EJBSessionBeanProject/SessionBeanStatefulExample");

		// The new bean instance starts out at 0

		System.out.println("Counter ->" + counterB.count());
		// assertEquals(0, counterB.count());
	}

}
