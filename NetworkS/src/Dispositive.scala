import scala.actors._
import scala.actors.Actor._
import scala.collection.{immutable, mutable}

trait Dispositive extends Actor
{  
	//Operazioni che deve fare
	protected var ope=new mutable.ArrayBuffer[Operation]();
	//Dispositivi che conosce
	protected var disp=new mutable.ArrayBuffer[Dispositive]();
	//Operazioni fatte
	protected var op:Int=0;
	//Nome
	protected var gid: Int=0;

	def id=gid;
	def id_= (i:Int) = {gid=i;};
	
	def register(dispositive: Dispositive, j: Int, i: Int) 
	{
		disp+=dispositive;
		println("Dispositivo "+i+" aggiunto a "+j);
	}
	
	def addOp(o: Operation)
	{
		ope+=o;
	}
	
	//Posso inviare e ricevere assieme, quindi creo un actor per ogni messaggio
	def act() 
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
	
	def receive (i: Int, j:Int); 
	
	//Invia un messaggio alla volta con un po' di attesa
	def send(i: Int) =
	{
		val o:Operation=ope(i);
		val to: Dispositive=o.to;
		op+=1;
		Thread.sleep(2000);
		to!o;
	}
	
	//Prepara i messaggi all'invio
	def prepare() =
	{
		for (i<-0 to ope.size-1)
		{
			println("[D] Preparazione messaggio");
			val s:Dispositive=this;
			s!i; 
		}
	}
}