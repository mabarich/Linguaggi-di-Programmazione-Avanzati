//Tablet puo' inviare piu' di un messaggio ma puo' riceverne uno alla volta
public class Tablet extends Dispositive
{	
	//Lock per ricezione
	Object lockT=new Object();
	
	public Tablet(int i)
	{
		super(i);
		System.out.println("Creato Tablet "+i);
	}
	
	//Uso synchronized perche' puo' ricevere un solo messaggio alla volta
	public void receive(String m, int i, int j) throws InterruptedException
	{
		synchronized(lockT)
		{
			try
			{
				Thread.sleep(2000);
				System.out.println("[T]Messaggio ricevuto da "+i+" a "+j+"!");		
			}
			catch (InterruptedException e){}
		}
	}
}
