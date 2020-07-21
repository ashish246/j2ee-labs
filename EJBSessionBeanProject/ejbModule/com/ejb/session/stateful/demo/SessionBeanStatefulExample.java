package com.ejb.session.stateful.demo;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

/**
 * Session Bean implementation class SessionBeanStatefulExample
 */
/**
 * This is an EJB 3 style pojo stateful session bean Every stateful session bean
 * implementation must be annotated using the annotation @Stateful This EJB has
 * 2 business interfaces: SessionBeanStatefulExampleRemote, a remote business
 * interface, and SessionBeanStatefulExampleLocal, a local business interface
 * <p/>
 * Per EJB3 rules when the @Remote or @Local annotation isn't present in the
 * bean class (this class), all interfaces are considered local unless
 * explicitly annotated otherwise. If you look in the
 * SessionBeanStatefulExampleRemote interface, you'll notice it uses the @Remote
 * annotation while the SessionBeanStatefulExampleLocal interface is not
 * annotated relying on the EJB3 default rules to make it a local interface.
 *
 * @author Administrator
 *
 */
@Stateful
@LocalBean
public class SessionBeanStatefulExample implements
		SessionBeanStatefulExampleRemote, SessionBeanStatefulExampleLocal {

	/**
	 * Default constructor.
	 */
	public SessionBeanStatefulExample() {
		// TODO Auto-generated constructor stub
	}

	private int count = 0;

	public int count() {
		return count;
	}

	public int increment() {
		return ++count;
	}

	public int reset() {
		return (count = 0);
	}

}
