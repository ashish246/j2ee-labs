package com.ejb.session.cmt.demo;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import com.ejb.session.model.Employee;

/**
 * A Container Managed Persistence Context is injected into an enterprise
 * component by using the @PersistenceContext annotation.
 * 
 * The enterprise container is responsible for ensuring that the Persistence
 * Context is aware of any ongoing transaction. It will also propagate the
 * Persistence Context between distinct enterprise components. Example: An EJB
 * calls another EJB that shares the same transaction of the first one. If they
 * both declare a container managed Persistence Context, the container will make
 * sure that the Persistence Context is propagated between the distinct EJBs.
 * 
 * A Container Managed Persistence Context may only be used with JTA:
 */
@Stateless
@LocalBean
public class SessionBeanCMTExample implements SessionBeanCMTExampleRemote,
		SessionBeanCMTExampleLocal {

	// only for Container Managed Persistence
	@PersistenceContext
	private static EntityManager entityManagerContainer;

	// only for Application Managed Persistence
	@PersistenceUnit
	private static EntityManagerFactory entityManagerFactory;


	public static void main(String[] args) {

		containerManagedPersistenceWithCMT();

		applicationManagedPersistenceWithCMT();
	}

	private static void containerManagedPersistenceWithCMT() {
		// read the existing entries and write to console
		Query q = entityManagerContainer
				.createQuery("select e from Employee e");
		List<Employee> employeeList = q.getResultList();

		for (Employee employee : employeeList) {
			System.out.println(employee);
		}

		System.out.println("Size: " + employeeList.size());
		// create new todo
		// entityManagerContainer.getTransaction().begin();

		Employee emp = new Employee();
		emp.setId(1L);
		emp.setName("Ashish");
		emp.setEmail("atripathi@gmail.com");
		emp.setDepartment("Finance");

		entityManagerContainer.persist(emp);

		// entityManager.getTransaction().commit();
		// entityManager.close();
	}

	private static void applicationManagedPersistenceWithCMT() {

		EntityManager entityManagerApp = entityManagerFactory
				.createEntityManager();

		// read the existing entries and write to console
		Query q = entityManagerApp.createQuery("select e from Employee e");
		List<Employee> employeeList = q.getResultList();

		for (Employee employee : employeeList) {
			System.out.println(employee);
		}

		System.out.println("Size: " + employeeList.size());
		// create new todo
		// entityManagerApp.getTransaction().begin();

		Employee emp = new Employee();
		emp.setId(1L);
		emp.setName("Ashish");
		emp.setEmail("atripathi@gmail.com");
		emp.setDepartment("Finance");

		entityManagerApp.persist(emp);

		// entityManager.getTransaction().commit();
		// entityManager.close();
	}

}
