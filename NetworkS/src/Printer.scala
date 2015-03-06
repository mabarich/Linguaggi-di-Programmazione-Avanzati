import scala.actors._
import scala.actors.Actor._

//Phone puo' inviare un messaggio alla volta o puo' riceverne sempre uno alla volta
class Printer (val i: Int) extends Dispositive
{	
	id=i;
	println("Stampante "+id+" creato.");
	
	//Nessun nuovo attore perche' posso o ricevere o inviare un messaggio
	override def act() 
	{
		loop
		{
			react
			{
				case o:Operation => receive(o.from2, o.to2);
				case k:Int => send(k);
				case "send" =>  actor { prepare; }
				case 'Interrupt => exit();
			}
		}
	}
	
	//Stesa cosa fatta in Tablet
	override def receive (i: Int, j:Int) =
	{
		Thread.sleep(2500);
		println("[Stampante]Messaggio ricevuto da "+i+" a "+j+"!");
	}
	
	//Stesa cosa fatta in Tablet
	override def send(i: Int) =
	{
		val o:Operation=ope(i);
		val to: Dispositive=o.to;
		op+=1;
		Thread.sleep(2000);
		to!o;	
	}
	
	//Invia un messaggio alla volta con un po' di attesa
	override def prepare() =
	{
		for (i<-0 to ope.size-1)
		{
			println("[Pr] Preparazione messaggio");
			val s:Dispositive=this;
			this!i;  
		}
	}
}