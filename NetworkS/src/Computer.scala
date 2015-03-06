//Computer puo' inviare piu' di un messaggio e riceverne sempre piu' di uno
class Computer (val i: Int) extends Dispositive
{
	id=i;
	println("Computer "+id+" creato.");
	
	override def receive (i: Int, j:Int) =
	{
		Thread.sleep(2000);
		println("[Computer]Messaggio ricevuto da "+i+" a "+j+"!");
	}
}