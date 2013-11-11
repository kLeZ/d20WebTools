package it.d4nguard.d20webtools;

import it.d4nguard.d20webtools.common.MersenneTwister;
import it.d4nguard.d20webtools.common.StringUtils;
import it.d4nguard.d20webtools.common.TimeSpan;
import it.d4nguard.d20webtools.controller.Chat;
import it.d4nguard.d20webtools.controller.ChatBox;
import it.d4nguard.d20webtools.controller.Rooms;
import it.d4nguard.d20webtools.controller.SignIn;
import it.d4nguard.d20webtools.controller.SignUp;
import it.d4nguard.d20webtools.model.Room;
import it.d4nguard.d20webtools.model.User;

import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.Action;

public class SingleUserNavigation
{
	private final Logger log = Logger.getLogger(getClass());

	private final BaseStrutsTestCase<Action> test;
	private final User user;
	private final Room room;
	private final boolean newRoom;
	private final long ttl;

	public SingleUserNavigation(BaseStrutsTestCase<Action> test)
	{
		this(test, new User("kLeZ", "julius8774@gmail.com", "klez-hack87"), new Room("Nebula", "julius8774@gmail.com"), false, 20);
	}

	public SingleUserNavigation(BaseStrutsTestCase<Action> test, User user, Room room, boolean newRoom, long ttl)
	{
		this.test = test;
		this.user = user;
		this.room = room;
		this.newRoom = newRoom;
		this.ttl = ttl;
	}

	/**
	 * This method acts as a single user which navigates the webapp.<br>
	 * It is useful to have a single method handling an entire user session,
	 * because in this way I can think of calling it myself many times inside a
	 * thread pool.<br>
	 * Doing that can bring me to a (quasi) full true multiuser load test suite.
	 * 
	 * @throws Exception
	 */
	public void runSingleNavigation() throws Exception
	{
		log.info("First time: Registering user");
		signUp();

		log.info("When registered, enter the web site");
		signIn();

		if (isNewRoom())
		{
			log.info("And then Create a room");
			createRoom();
		}
		else
		{
			log.info("And then Join a room");
			joinRoom();
		}

		log.info("Doing this, redirects the user to the Chat Action");
		chat();

		log.info("Every half-second update the chatbox where messages live and show up");
		Date start = new Date();
		Date lastMessage = null;
		MersenneTwister random = new MersenneTwister();
		do
		{
			Thread.sleep(500);
			chatBox();
			int delay = random.next(20, 90);
			if (new TimeSpan(lastMessage).diff().getSeconds() >= delay)
			{
				log.info(String.format("This message has been sent after waiting %d seconds...", delay));
				chat(String.format("This message has been sent after waiting %d seconds...", delay));
			}
		}
		while (new TimeSpan(start).diff().getMinutes() < getTtl());
		exitRoom();
		logOut();
	}

	private SignIn logOut() throws Exception
	{
		return getTest().callAction(SignIn.class, "/pages/logout");
	}

	private Rooms exitRoom() throws Exception
	{
		return getTest().callAction(Rooms.class, "/pages/exit-room");
	}

	private ChatBox chatBox() throws Exception
	{
		return getTest().callAction(ChatBox.class, "/pages/chatbox");
	}

	private Chat chat() throws Exception
	{
		return chat("");
	}

	private Chat chat(String message) throws Exception
	{
		HashMap<String, String> form = new HashMap<String, String>();
		if (StringUtils.isNullOrWhitespace(message))
		{
			form.put("message", message);
		}

		return getTest().callAction(Chat.class, "/pages/chat", form);
	}

	private Rooms joinRoom() throws Exception
	{
		HashMap<String, String> form = new HashMap<String, String>();
		form.put("room.id", getRoom().getId().toString());

		return getTest().callAction(Rooms.class, "/pages/join-room", form);
	}

	private Rooms createRoom() throws Exception
	{
		HashMap<String, String> form = new HashMap<String, String>();
		form.put("room.name", getRoom().getName());

		return getTest().callAction(Rooms.class, "/pages/new-room", form);
	}

	private SignIn signIn() throws Exception
	{
		HashMap<String, String> form = new HashMap<String, String>();
		form.put("user.email", getUser().getEmail());
		form.put("user.password", getUser().getPassword());

		return getTest().callAction(SignIn.class, "/pages/sign-in", form);
	}

	private SignUp signUp() throws Exception
	{
		HashMap<String, String> form = new HashMap<String, String>();
		form.put("user.name", getUser().getName());
		form.put("user.email", getUser().getEmail());
		form.put("user.password", getUser().getPassword());
		form.put("confirmPassword", getUser().getPassword());

		return getTest().callAction(SignUp.class, "/pages/sign-up", form);
	}

	public BaseStrutsTestCase<Action> getTest()
	{
		return test;
	}

	public User getUser()
	{
		return user;
	}

	public Room getRoom()
	{
		return room;
	}

	public boolean isNewRoom()
	{
		return newRoom;
	}

	public long getTtl()
	{
		return ttl;
	}
}
