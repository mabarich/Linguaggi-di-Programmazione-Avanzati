import scala.actors._
import scala.actors.Actor._

//Tablet puo' inviare piu' di un messaggio e ma puo' riceverne uno alla volta
class Tablet (val i: Int) extends Dispositive 
{
	id=i;
	println("Tablet "+id+" creato.");
	
	//Posso inviare piu' messaggi ma riceverne uno alla volta, quindi creo un actor solo per "send"
	override def act() 
	{
		loop
		{
			react
			{
				case o:Operation => actor { receive(o.from2, o.to2) };
				case k:Int => actor { send(k); }
				case "send" => actor { prepare; }
				case 'Interrupt => exit();
			}
		}
	}
	
	//Mi serve il synchronized perche' posso sia inviare sia ricevere assieme, e senza di esso non potrei garantire una ricezione alla volta
	override def receive (i: Int, j:Int) =
	{
		this.synchronized
		{
			Thread.sleep(2000);
			println("[Tablet]Messaggio ricevuto da "+i+" a "+j+"!");
		}
	}
}