import scala.actors._
import scala.actors.Actor._

//Phone puo' inviare un messaggio alla volta e puo' riceverne sempre uno alla volta
class Phone (val i: Int) extends Dispositive
{
	val lockS:Object=new Object();
	val lockR:Object=new Object();
	
	id=i;
	println("Telefono "+id+" creato.");
	
	override def act() 
	{
		loop
		{
			react
			{
				case o:Operation => actor { receive(o.from2, o.to2); }
				case k:Int => actor { send(k); }
				case "send" => actor { prepare; }
				case 'Interrupt => exit();
			}
		}
	}
	
	//Stesa cosa fatta in Tablet
	override def receive (i: Int, j:Int) =
	{
		lockR.synchronized
		{
			Thread.sleep(2000);
			println("[Telefono]Messaggio ricevuto da "+i+" a "+j+"!");
		}
	}
	
	//Stesa cosa fatta in Tablet
	override def send(i: Int) =
	{
		lockS.synchronized
		{
			val o:Operation=ope(i);
			val to: Dispositive=o.to;
			op+=1;
			Thread.sleep(2000);
			to!o;	
		}
	}
	
	//Invia un messaggio alla volta con un po' di attesa
	override def prepare() =
	{
		for (i<-0 to ope.size-1)
		{
			println("[P] Preparazione messaggio");
			val s:Dispositive=this;
			this!i;  
		}
	}
}