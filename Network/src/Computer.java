//Computer puo' inviare piu' di un messaggio e riceverne piu' di uno
public class Computer extends Dispositive
{
	public Computer(int i)
	{
		super(i);
		System.out.println("Creato Computer "+i);
	}
	
	//Puo' ricevere piu' messaggi assieme
	public void receive(String msg, int i, int j) throws InterruptedException 
	{
		Thread.sleep(2000);
		System.out.println("[C]Messaggio ricevuto da "+i+" a "+j+"!");
	}
}
