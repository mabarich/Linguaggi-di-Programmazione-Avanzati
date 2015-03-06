//Phone puo' inviare un messaggio alla volta e puo' riceverne sempre uno alla volta
public class Phone extends Dispositive
{
	//Lock per ricezione
	Object lockR=new Object();
	
	public Phone(int i)
	{
		super(i);
		System.out.println("Creato Telefono "+i);
	}
	
	//Prepara un messaggio alla volta
	public void run()
	{
		for (int i=0; i<ope.size(); i++)
		{
			try 
			{
				System.out.println("[P]Preparazione operazione da "+ope.get(i).getFrom2()+" a "+ope.get(i).getTo2()+"!");
				send(i);
			} 
			catch (InterruptedException e) 
			{
				//e.printStackTrace();
			}
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
		
	//Non serve nessun synchronized
	private void send(int i) throws InterruptedException
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
	
	//Uso synchronized perche' puo' ricevere un solo messaggio alla volta
	public void receive(String m, int i, int j) throws InterruptedException
	{
		synchronized(lockR)
		{
			try
			{	
				Thread.sleep(2000);		
			}	
			catch (InterruptedException e){}
			System.out.println("[P]Messaggio ricevuto da "+i+" a "+j+"!");
		}
	}
}
