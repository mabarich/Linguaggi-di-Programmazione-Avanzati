import scala.actors.Actor
import scala.collection.{immutable, mutable}

object Gateway 
{
	def main(args: Array[String]): Unit = 
	{
		val network:Network= new Network();
		network.start();
		//network!"go";
		//network!'cc; //Ok: Tutti insieme
		//network!'pp; //Ok: Due alla volta
		//network!'tt; //Ok: Due alla volta
		//network!'cp; //Ok: Due alla volta
		//network!'pt; //Ok: Due alla volta
		//network!'ct; //Ok: Quattro insieme e poi uno alla volta
		//network!'prt; //Ok: Uno alla volta
		//network!'prp; //Ok: Uno alla volta
		//network!'prc; //Ok: Uno alla volta
		network!'prpr; //Ok: Uno alla volta. I metodi di entrambi gli attori finiscono assieme quindi stampa due righe alla volta.
	}
}

