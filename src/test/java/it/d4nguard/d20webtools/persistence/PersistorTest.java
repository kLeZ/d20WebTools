package it.d4nguard.d20webtools.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import it.d4nguard.d20webtools.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersistorTest
{
	private static Persistor persistor;

	public Persistor getPersistor()
	{
		return persistor;
	}

	@BeforeClass
	public static void setUp() throws Exception
	{
		Properties toOverrideProperties = new Properties();
		toOverrideProperties.put("hibernate.hbm2ddl.auto", "create");
		new Persistor(new HibernateSession(toOverrideProperties)).destroy();

		persistor = new Persistor(new HibernateSession());
	}

	@AfterClass
	public static void tearDown() throws Exception
	{
		persistor.destroy();
		persistor = null;
	}

	@Test
	public final void a_01_testSave()
	{
		getPersistor().save(new User("kLeZ", "julius8774@gmail.com", "klez-hack87"));
		List<User> users = getPersistor().findByEqField(User.class, "email", "julius8774@gmail.com");
		assertEquals(users.size(), 1);
		try
		{
			getPersistor().save(new User("kLeZ", "julius8774@gmail.com", "klez-hack87"));
			fail("Not handling duplicated user");
		}
		catch (PersistorException e)
		{
		}
		assertEquals(getPersistor().findAll(User.class).size(), 1);
	}

	@Test
	public final void a_02_testUpdate()
	{
		List<User> users = getPersistor().findByEqField(User.class, "email", "julius8774@gmail.com");
		assertEquals(users.size(), 1);

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
		assertEquals(getPersistor().findAll(User.class).size(), 1);
	}

	@Test
	public final void a_03_testSaveOrUpdate()
	{
		List<User> users = getPersistor().findByEqField(User.class, "email", "julius8774@gmail.com");
		assertEquals(users.size(), 1);

		User u = users.get(0);
		u.setPassword("klez-hack8774");
		getPersistor().saveOrUpdate(u);

		getPersistor().saveOrUpdate(new User("Admin", "admin@d20webtools.org", "Pippo13"));
		getPersistor().saveOrUpdate(new User("Root", "root@d20webtools.org", "Password01"));
		getPersistor().saveOrUpdate(new User("Admin", "admin@d20webtools.org", "Pippo13"));

		users = getPersistor().findByEqField(User.class, "email", "root@d20webtools.org");
		assertEquals(users.size(), 1);

		users = getPersistor().findByEqField(User.class, "email", "admin@d20webtools.org");
		assertTrue(users.size() == 1);

		assertTrue(getPersistor().findAll(User.class).size() == 3);
	}

	@Test
	public final void a_04_testDelete()
	{
		List<User> users = getPersistor().findByEqField(User.class, "email", "julius8774@gmail.com");
		assertTrue(users.size() == 1);

		User u = users.get(0);
		assertTrue(getPersistor().findAll(User.class).size() == 3);

		getPersistor().delete(u);
		assertTrue(getPersistor().findAll(User.class).size() == 2);
	}

	@Test
	public final void a_05_testSaveAll()
	{
		List<User> users = getPersistor().findByEqField(User.class, "email", "julius8774@gmail.com");
		assertTrue(users.size() == 1);

		users.add(new User("Pippo", "pippo@disney.com", "Pippo001"));
		users.add(new User("Pluto", "pluto@disney.com", "Pluto001"));
		users.add(new User("Topolino", "mickey@disney.com", "Mickey001"));
		users.add(new User("Clarabella", "beauclaire@disney.com", "BeauClaire001"));

		try
		{
			getPersistor().saveAll(users);
			fail("Not handling duplicated objects");
		}
		catch (PersistorException e)
		{
		}

		users = new ArrayList<User>();
		users.add(new User("Pippo", "pippo@disney.com", "Pippo001"));
		users.add(new User("Pluto", "pluto@disney.com", "Pluto001"));
		users.add(new User("Topolino", "mickey@disney.com", "Mickey001"));
		users.add(new User("Clarabella", "beauclaire@disney.com", "BeauClaire001"));
		getPersistor().saveAll(users);

		assertTrue(getPersistor().findAll(User.class).size() == 6);
	}

	@Test
	public final void a_06_testUpdateAll()
	{
		List<User> users = getPersistor().findAll(User.class);
		for (User u : users)
		{
			u.setEmail(u.getEmail().replace("@disney.com", "@walt-disney.com"));
		}
		getPersistor().updateAll(users);
		assertTrue(getPersistor().findAll(User.class).size() == 6);

		users.add(new User("Paperino", "dduck@disney.com", "DDuck001"));
		users.add(new User("Zio Paperone", "nichelino@disney.com", "Nickel001"));

		try
		{
			getPersistor().updateAll(users);
			fail("Not handling inexistent objects");
		}
		catch (PersistorException e)
		{
		}
		assertTrue(getPersistor().findAll(User.class).size() == 6);
	}

	@Test
	public final void a_07_testSaveOrUpdateAll()
	{
		List<User> users = getPersistor().findAll(User.class);
		for (User u : users)
		{
			u.setEmail(u.getEmail().replace("@disney.com", "@walt-disney.com"));
		}
		users.add(new User("Paperino", "dduck@walt-disney.com", "DDuck001"));
		users.add(new User("Zio Paperone", "nichelino@walt-disney.com", "Nickel001"));
		getPersistor().updateAll(users);

		assertTrue(getPersistor().findAll(User.class).size() == 8);
	}

	@Test
	public final void a_08_testFindById()
	{
		User kLeZ = getPersistor().findById(User.class, 1L);
		assertEquals(kLeZ.getName(), "kLeZ");
		assertEquals(kLeZ.getEmail(), "julius8774@gmail.com");
		assertEquals(kLeZ.getPassword(), "klez-hack8774");
	}

	@Test
	public final void a_09_testFindByEqField()
	{
		List<User> users = getPersistor().findByEqField(User.class, "email", "julius8774@gmail.com");
		assertTrue(users.size() == 1);
	}

	@Test
	public final void a_10_testFindAll()
	{
		assertTrue(getPersistor().findAll(User.class).size() == 8);
	}
}
