package it.d4nguard.d20webtools.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import it.d4nguard.d20webtools.common.CharType;
import it.d4nguard.d20webtools.common.Password;
import it.d4nguard.d20webtools.model.User;

import java.util.ArrayList;
import java.util.EnumSet;
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
		assertEquals(1, users.size());
		try
		{
			getPersistor().save(new User("kLeZ", "julius8774@gmail.com", "klez-hack87"));
			fail("Not handling duplicated user");
		}
		catch (PersistorException e)
		{
		}
		assertEquals(1, getPersistor().findAll(User.class).size());
	}

	@Test
	public final void a_02_testUpdate()
	{
		List<User> users = getPersistor().findByEqField(User.class, "email", "julius8774@gmail.com");
		assertEquals(1, users.size());

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
		assertEquals(1, getPersistor().findAll(User.class).size());
	}

	@Test
	public final void a_03_testSaveOrUpdate()
	{
		List<User> users = getPersistor().findByEqField(User.class, "email", "julius8774@gmail.com");
		assertEquals(1, users.size());

		User u = users.get(0);
		u.setPassword("klez-hack8774");
		getPersistor().saveOrUpdate(u);

		getPersistor().saveOrUpdate(new User("Admin", "admin@d20webtools.org", "Pippo13"));
		getPersistor().saveOrUpdate(new User("Root", "root@d20webtools.org", "Password01"));
		/*
		 * FIXME:I can't use saveOrUpdate this way because of the logic of this method
		 * 
		 * Semantics of saveOrUpdate() operation is the following (see 11.7. Automatic state detection):
		 * 
		 * if the object is already persistent in this session, do nothing
		 * if another object associated with the session has the same identifier, throw an exception
		 * if the object has no identifier property, save() it
		 * if the object's identifier has the value assigned to a newly instantiated object, save() it
		 * if the object is versioned by a or , and the version property value is the same value assigned to a newly instantiated object, save() it
		 * otherwise update() the object
		 */
		//getPersistor().saveOrUpdate(new User("Admin", "admin@d20webtools.org", "Pippo131"));

		users = getPersistor().findByEqField(User.class, "email", "root@d20webtools.org");
		assertEquals(1, users.size());

		users = getPersistor().findByEqField(User.class, "email", "admin@d20webtools.org");
		assertEquals(1, users.size());

		assertEquals(3, getPersistor().findAll(User.class).size());
	}

	@Test
	public final void a_04_testDelete()
	{
		List<User> users = getPersistor().findByEqField(User.class, "email", "julius8774@gmail.com");
		assertEquals(1, users.size());

		User u = users.get(0);
		assertEquals(3, getPersistor().findAll(User.class).size());

		getPersistor().delete(u);
		assertEquals(2, getPersistor().findAll(User.class).size());
	}

	@Test
	public final void a_05_testSaveAll()
	{
		List<User> users = getPersistor().findByEqField(User.class, "email", "julius8774@gmail.com");
		assertEquals(0, users.size());

		users.add(new User("Pippo", "pippo@disney.com", "Pippo001"));
		users.add(new User("Pluto", "pluto@disney.com", "Pluto001"));
		users.add(new User("Topolino", "mickey@disney.com", "Mickey001"));
		users.add(new User("Clarabella", "beauclaire@disney.com", "BeauClaire001"));
		getPersistor().saveAll(users);

		users = new ArrayList<User>();
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

		assertEquals(6, getPersistor().findAll(User.class).size());
	}

	@Test
	public final void a_06_testUpdateAll()
	{
		List<User> users = getPersistor().findAll(User.class);
		for (User u : users)
		{
			u.setPassword(Password.generate(12, EnumSet.of(CharType.Upper, CharType.Lower, CharType.Number, CharType.Symbol)));
		}
		getPersistor().updateAll(users);
		assertEquals(6, getPersistor().findAll(User.class).size());

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
		assertEquals(6, getPersistor().findAll(User.class).size());
	}

	@Test
	public final void a_07_testFindById()
	{
		User u = getPersistor().findById(User.class, 2L);
		assertEquals("Admin", u.getName());
		assertEquals("admin@d20webtools.org", u.getEmail());
	}

	@Test
	public final void a_08_testFindByEqField()
	{
		List<User> users = getPersistor().findByEqField(User.class, "email", "julius8774@gmail.com");
		assertEquals(0, users.size());

		users = getPersistor().findByEqField(User.class, "email", "admin@d20webtools.org");
		assertEquals(1, users.size());
	}

	@Test
	public final void a_09_testFindAll()
	{
		assertEquals(6, getPersistor().findAll(User.class).size());
	}
}
