import java.util.ArrayList;

public abstract class Dispositive implements Runnable
{
	//Dispositivi che conosce
	protected ArrayList<Dispositive> disp=new ArrayList<Dispositive>();
	//Operazioni che deve fare
	protected ArrayList<Operation> ope=new ArrayList<Operation>();
	//Nome
	protected int name;
	//Numero di operazioni
	protected int operations=0;
	
	public Dispositive(int n)
	{
		name=n;
	}
	
	public void setOperations(int op)
	{
		operations=op;
	}

	public void register(Dispositive dispositive, int j, int i) 
	{
		disp.add(dispositive);
		System.out.println("Dispositivo "+i+" aggiunto a "+j);
	}
	
	public void addOp(Operation o)
	{
		ope.add(o);
	}
	
	//Crea un nuovo thread perche' puo' inviare piu' messaggi assieme
	public void run()
	{
		for (int i=0; i<ope.size(); i++)
		{
			final int num=i;
			System.out.println("[D]Preparazione operazione da "+ope.get(i).getFrom2()+" a "+ope.get(i).getTo2()+"!");
			new Thread() 
			{
				public void run() 
				{
					send(num);
				}
			}.start();
		}
		
		//Quando ha finito, aspetta un po' prima di fermarsi
		try 
		{
			Thread.sleep(5000*operations);
		} 
		catch (InterruptedException e) 
		{
			//e.printStackTrace();
		}
	}
	
	private void send(int i)
	{
		Dispositive to=ope.get(i).getTo();
		String msg=ope.get(i).getMessage();
		try 
		{
			to.receive(msg, ope.get(i).getFrom2(), ope.get(i).getTo2());
		} 
		catch (InterruptedException e) 
		{
			//e.printStackTrace();
		}
	}
	
	//Puo' ricevere piu' messaggi assieme
	abstract public void receive(String msg, int i, int j) throws InterruptedException;
}
