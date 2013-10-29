package it.d4nguard.d20webtools.model;

public class Suggestion
{
	private Long id;
	private String message;
	private SuggestionStatus status;
	private SuggestionType type;
	private Long votes;
	private User proposer;
	private boolean done;

	/**
	 * Needed for struts2 to instantiate then populate, which is the default
	 * behavior of the framework.
	 */
	public Suggestion()
	{
	}

	public Suggestion(Long id, String message, SuggestionStatus status, SuggestionType type, Long votes, User proposer, boolean done)
	{
		this.id = id;
		this.message = message;
		this.status = status;
		this.type = type;
		this.votes = votes;
		this.proposer = proposer;
		this.done = done;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public SuggestionStatus getStatus()
	{
		return status;
	}

	public void setStatus(SuggestionStatus status)
	{
		this.status = status;
	}

	public SuggestionType getType()
	{
		return type;
	}

	public void setType(SuggestionType type)
	{
		this.type = type;
	}

	public Long getVotes()
	{
		return votes;
	}

	public void setVotes(Long votes)
	{
		this.votes = votes;
	}

	public User getProposer()
	{
		return proposer;
	}

	public void setProposer(User proposer)
	{
		this.proposer = proposer;
	}

	public boolean isDone()
	{
		return done;
	}

	public void setDone(boolean done)
	{
		this.done = done;
	}
}
