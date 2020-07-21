package com.ejb.session.bmt.demo;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.ejb.session.model.Employee;

/**
 * Session Bean implementation class SessionBeanBMTExample
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
public class SessionBeanBMTExample implements SessionBeanBMTExampleRemote,
		SessionBeanBMTExampleLocal {

	// only for Application Managed Persistence
	@PersistenceUnit
	private static EntityManagerFactory entityManagerFactory;

	// Only if it is Container Managed Persistence
	@PersistenceContext
	private static EntityManager entityManagerContainer;

	@Resource
	private static UserTransaction userTransaction;

	
	public static void main(String[] args) throws SecurityException,
			IllegalStateException, NotSupportedException, SystemException,
			RollbackException, HeuristicMixedException,
			HeuristicRollbackException {

		containerManagedPersistenceWithBMT();

		applicationManagedPersistenceWithBMT();

	}

	private static void containerManagedPersistenceWithBMT()
			throws NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException,
			HeuristicRollbackException {

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
		userTransaction.begin();

		Employee emp = new Employee();
		emp.setId(1L);
		emp.setName("Ashish");
		emp.setEmail("atripathi@gmail.com");
		emp.setDepartment("Finance");

		entityManagerContainer.persist(emp);

		// entityManager.getTransaction().commit();
		userTransaction.commit();
		// entityManager.close();
	}

	private static void applicationManagedPersistenceWithBMT()
			throws NotSupportedException, SystemException, SecurityException,
			IllegalStateException, RollbackException, HeuristicMixedException,
			HeuristicRollbackException {

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
		userTransaction.begin();

		Employee emp = new Employee();
		emp.setId(1L);
		emp.setName("Ashish");
		emp.setEmail("atripathi@gmail.com");
		emp.setDepartment("Finance");

		entityManagerApp.persist(emp);

		// entityManager.getTransaction().commit();
		userTransaction.commit();
		// entityManager.close();
	}

}
