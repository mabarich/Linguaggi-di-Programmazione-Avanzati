
public class Operation 
{
	private Dispositive from, to;
	private int from2, to2;
	private String message;
	private boolean broadcast;
	
	public Operation(Dispositive f, Dispositive t, String m, int f2, int t2, boolean b)
	{
		from=f;
		to=t;
		message=m;
		from2=f2;
		to2=t2;
		broadcast=b;
	}

	public Dispositive getTo() 
	{
		return to;
	}
	
	public Dispositive getFrom() 
	{
		return from;
	}
	
	public int getTo2() 
	{
		return to2;
	}
	
	public int getFrom2() 
	{
		return from2;
	}
	
	public String getMessage() 
	{
		return message;
	}
	
	public boolean getBroadcast() 
	{
		return broadcast;
	}
}
