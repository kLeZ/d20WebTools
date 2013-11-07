package it.d4nguard.d20webtools.persistence;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import it.d4nguard.d20webtools.model.User;

import java.util.List;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersistorTest
{
	private Persistor persistor;

	public Persistor getPersistor()
	{
		return persistor;
	}

	@Before
	public void setUp() throws Exception
	{
		Properties props = new Properties();
		props.put("hibernate.hbm2ddl.auto", "create-drop");
		persistor = new Persistor(new HibernateSession(props));
	}

	@After
	public void tearDown() throws Exception
	{
		persistor.destroy();
		persistor = null;
	}

	@Test
	public final void a_01_testSave()
	{
		getPersistor().save(new User("kLeZ", "julius8774@gmail.com", "klez-hack87"));
		List<User> users = getPersistor().findByEqField(User.class, "email", "julius8774@gmail.com");
		assertTrue(users.size() == 1);
		try
		{
			getPersistor().save(new User("kLeZ", "julius8774@gmail.com", "klez-hack87"));
			fail("Not handling duplicated user");
		}
		catch (PersistorException e)
		{
		}
		assertTrue(getPersistor().findAll(User.class).size() == 1);
	}

	@Test
	public final void a_02_testUpdate()
	{
		List<User> users = getPersistor().findByEqField(User.class, "email", "julius8774@gmail.com");
		assertTrue(users.size() == 1);

		User u = users.get(0);
		u.setPassword("klez-hack8774");
		getPersistor().update(u);
		try
		{
			getPersistor().update(new User("Root", "root@d20webtools.org", "Password01"));
			fail("Not handling inexistent user");
		}
		catch (PersistorException e)
		{
		}
		assertTrue(getPersistor().findAll(User.class).size() == 1);
	}

	@Test
	public final void a_03_testSaveOrUpdate()
	{
		List<User> users = getPersistor().findByEqField(User.class, "email", "julius8774@gmail.com");
		assertTrue(users.size() == 1);

		User u = users.get(0);
		u.setPassword("klez-hack8774");
		getPersistor().saveOrUpdate(u);

		getPersistor().saveOrUpdate(new User("Admin", "admin@d20webtools.org", "Pippo13"));
		getPersistor().saveOrUpdate(new User("Root", "root@d20webtools.org", "Password01"));
		getPersistor().saveOrUpdate(new User("Admin", "admin@d20webtools.org", "Pippo13"));

		users = getPersistor().findByEqField(User.class, "email", "root@d20webtools.org");
		assertTrue(users.size() == 1);

		users = getPersistor().findByEqField(User.class, "email", "admin@d20webtools.org");
		assertTrue(users.size() == 1);

		assertTrue(getPersistor().findAll(User.class).size() == 3);
	}

	@Test
	public final void a_04_testDelete()
	{
		fail("Not yet implemented");
	}

	@Test
	public final void a_05_testSaveAll()
	{
		fail("Not yet implemented");
	}

	@Test
	public final void a_06_testUpdateAll()
	{
		fail("Not yet implemented");
	}

	@Test
	public final void a_07_testSaveOrUpdateAll()
	{
		fail("Not yet implemented");
	}

	@Test
	public final void a_08_testFindById()
	{
		fail("Not yet implemented");
	}

	@Test
	public final void a_09_testFindByEqField()
	{
		fail("Not yet implemented");
	}

	@Test
	public final void a_10_testFindByCriterionClassOfEHibernateRestrictionArray()
	{
		fail("Not yet implemented");
	}

	@Test
	public final void a_11_testFindByCriterionClassOfEHashMapOfStringStringHibernateRestrictionArray()
	{
		fail("Not yet implemented");
	}

	@Test
	public final void a_12_testFindAll()
	{
		fail("Not yet implemented");
	}

}
