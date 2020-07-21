package com.ejb.session.stateless.demo;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;

public class CalculatorTest {

	private static EJBContainer ejbContainer;

	public static void main(String[] args) throws NamingException {

		ejbContainer = EJBContainer.createEJBContainer();

		Object object = ejbContainer.getContext().lookup(
				"java:global/EJBSessionBeanProject/CalculatorBean");

		// assertTrue(object instanceof CalculatorBean);

		CalculatorBean calculator = (CalculatorBean) object;

		/**
		 * Test Add method
		 */
		System.out.println(calculator.add(4, 6));

		/**
		 * Test Subtract method
		 */
		System.out.println(calculator.subtract(4, 6));

		/**
		 * Test Divide method
		 */
		System.out.println(calculator.multiply(4, 6));

		/**
		 * Test Remainder method
		 */
		System.out.println(calculator.remainder(4, 6));
		

		ejbContainer.close();

	}
}
