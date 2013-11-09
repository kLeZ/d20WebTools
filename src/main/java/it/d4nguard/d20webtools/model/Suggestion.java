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

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (message == null ? 0 : message.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof Suggestion)) return false;
		Suggestion other = (Suggestion) obj;
		if (message == null)
		{
			if (other.message != null) return false;
		}
		else if (!message.equals(other.message)) return false;
		return true;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Suggestion [id=");
		builder.append(id);
		builder.append(", message=");
		builder.append(message);
		builder.append(", status=");
		builder.append(status);
		builder.append(", type=");
		builder.append(type);
		builder.append(", votes=");
		builder.append(votes);
		builder.append(", proposer=");
		builder.append(proposer);
		builder.append(", done=");
		builder.append(done);
		builder.append("]");
		return builder.toString();
	}
}
