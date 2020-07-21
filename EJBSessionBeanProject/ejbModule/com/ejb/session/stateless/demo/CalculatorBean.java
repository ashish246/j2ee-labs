package com.ejb.session.stateless.demo;

import javax.ejb.Stateless;

/**
 * Stateless session beans are session beans whose instances have no
 * conversational state. This means that all bean instances are equivalent when
 * they are not involved in servicing a client-invoked method. The term
 * 'stateless' signifies that an instance has no state for a specific client.
 * 
 * 
 * What this means is quite simply that stateless beans are shared. They do in
 * fact have state as you can assign values to the variables, etc. in the bean
 * instance. The only catch is there are a pool of identical instances and you
 * are not guaranteed to get the exact same instance on every call. For each
 * call, you get whatever instance happens to be available. This is identical to
 * checking out a book from the library or renting a movie from the video store.
 * You are essentially checking out or renting a new bean instance on each
 * method call.
 * 
 * @author Administrator
 *
 */
@Stateless
public class CalculatorBean {

	public int add(int a, int b) {
		return a + b;
	}

	public int subtract(int a, int b) {
		return a - b;
	}

	public int multiply(int a, int b) {
		return a * b;
	}

	public int divide(int a, int b) {
		return a / b;
	}

	public int remainder(int a, int b) {
		return a % b;
	}
}