import scala.actors._
import scala.actors.Actor._
import scala.collection.{immutable, mutable}

class Network extends Actor
{
	//Tutti i dispositivi della rete
	private var disp=new mutable.ArrayBuffer[Dispositive]();
	
	def act()=
	{
		loop
		{
			react
			{
			  	case "go" => go();
			  	case 'cc => testComputerComputer();
			  	case 'pp => testPhonePhone();
			  	case 'tt => testTabletTablet();
			  	case 'ct => testComputerTablet();
			  	case 'cp => testComputerPhone();
			  	case 'pt => testPhoneTablet();
			  	case 'prt => testPrinterTablet();
			  	case 'prp => testPrinterPhone();
			  	case 'prc => testPrinterComputer();
			  	case 'prpr => testPrinterPrinter();
			  	case 'Interrupt => finish();
			}
		}
	}
	
	private def finish() =
	{
		for(k<-0 to disp.size-1)
		  disp(k)!'Interrupt;
		exit();
	}
	
	//Fa conoscere ad ogni dispositivo tutti gli altri
	private def register() =
	{
		for (i<-0 to disp.size-1)
		{
			for (j<-0 to disp.size-1)
			{
				if(i!=j)
				  disp(i).register(disp(j), j, i);
			}
			println("...........");
		}
	}
	
	//Crea per ogni dispositivo, un numero casuale di operazioni con destinatario sempre casuale
	private def operations():Int =
	{		
		var oper:Int=0;
		val maxOp:Int=2;
		val minOp:Int=0;
		for (i<-0 to disp.size-1)
		{
			val rnd = new scala.util.Random;
			val range = minOp to maxOp
			val range2 = 0 to disp.size-1
			val operations:Int =range(rnd.nextInt(range length));
			for(j<-0 to operations)
			{
				
				val to:Int =range2(rnd.nextInt(range2 length));
				if(i!=to)
				{
					var br:Boolean=false;
					val rangeBr = 0 to 1
					val brI:Int =rangeBr(rnd.nextInt(rangeBr length));
					if(brI==1)
					{
						br=true;
				    	for(j<-0 to disp.size-1)
				    	{
							if(i!=j)
							{
								
								disp(i).addOp(new Operation(disp(i), disp(j), "Messaggio inviato da "+i+" a "+j+".", i, j, br));
								oper+=1;
							}
				    	}
					}
					else
					{
						disp(i).addOp(new Operation(disp(i), disp(to), "Messaggio inviato da "+i+" a "+to+".", i, to, br));
						oper+=1;
					}
				}
			}
		}
		return oper;
	}
	
	//Avvia i dispositivi
	private def startD() =
	{ 
		for (i<-0 to disp.size-1)
		{
			disp(i).start;
		}
		
		for (j<-0 to disp.size-1)
		{
			disp(j)!"send";
		}
	}
	
	private def go()=
	{
		//Numero operazioni
		var op:Int=0;
		val rnd = new scala.util.Random;
		//Crea 3 dispositivi
		val numDisp=2;
		//Di tipo casuale
		val range = 0 to 3
		//Crea computer, tablet o phone
		for (i<-0 to numDisp)
		{
			var d:Dispositive=null;
			val tDisp:Int =range(rnd.nextInt(range length));
		    tDisp match
		    {
		    	case 0 => 
	    			d=new Computer(i);
	    			disp+=d;
		    	case 1 => 
	    			d=new Phone(i); 
	    			disp+=d;
		    	case 2 => 
	    			d=new Tablet(i);
	    			disp+=d;
    			case 3 => 
	    			d=new Printer(i);
	    			disp+=d;
		    }
		}
		
		//Fa conoscere ad ogni dispositivo tutti gli altri
		register();
		//Crea per ogni dispositivo, un numero casuale di operazioni con destinatario sempre casuale
		op=operations();
		//Avvia i dispositivi
		startD();
		stop(op);
	}
	
	private def stop(op:Int)=
	{
		println("Ho "+op+" operazioni da fare. Ora devo riposare");
		Thread.sleep(op*5000);
		self!'Interrupt;
	}
	
	//Due alla volta
	private def testPhoneTablet() =
	{
		val a:Dispositive=new Phone(0);
		val b:Dispositive=new Tablet(1);
		startTest(a,b);
	}
	
	//Due alla volta
	private def testComputerPhone() =
	{
		val a:Dispositive=new Computer(0);
		val b:Dispositive=new Phone(1);
		startTest(a,b);
	}
	
	//Quattro insieme e poi uno alla volta
	private def testComputerTablet() =
	{
		val a:Dispositive=new Computer(0);
		val b:Dispositive=new Tablet(1);
		startTest(a,b);
	}
	
	//Tutto insieme
	private def testComputerComputer() =
	{
		val a:Dispositive=new Computer(0);
		val b:Dispositive=new Computer(1);
		startTest(a,b);
	}
	
	//Due alla volta
	private def testTabletTablet() =
	{
		val a:Dispositive=new Tablet(0);
		val b:Dispositive=new Tablet(1);
		startTest(a,b);
	}
	
	//Due alla volta
	private def testPhonePhone() =
	{
		val a:Dispositive=new Phone(0);
		val b:Dispositive=new Phone(1);
		startTest(a,b);
	}
	
	//Uno alla volta
	private def testPrinterPhone() =
	{
		val a:Dispositive=new Printer(0);
		val b:Dispositive=new Phone(1);
		startTest(a,b);
	}
	
	//Uno alla volta
	private def testPrinterTablet() =
	{
		val a:Dispositive=new Printer(0);
		val b:Dispositive=new Tablet(1);
		startTest(a,b);
	}
	
	//Uno alla volta
	private def testPrinterComputer() =
	{
		val a:Dispositive=new Printer(0);
		val b:Dispositive=new Computer(1);
		startTest(a,b);
	}
	
	//Uno alla volta
	private def testPrinterPrinter() =
	{
		val a:Dispositive=new Printer(0);
		val b:Dispositive=new Printer(1);
		startTest(a,b);
	}
	
	private def startTest(a:Dispositive, b:Dispositive) =
	{
		a.register(b, 0, 1);
		b.register(a, 1, 0);
		disp+=a;
		disp+=b;
		a.addOp(new Operation(a, b, "Ciao", 0, 1, false));
		a.addOp(new Operation(a, b, "Ciao", 0, 1, false));
		a.addOp(new Operation(a, b, "Ciao", 0, 1, false));
		b.addOp(new Operation(b, a, "Ciao", 1, 0, false));
		b.addOp(new Operation(b, a, "Ciao", 1, 0, false));
		b.addOp(new Operation(b, a, "Ciao", 1, 0, false));
		a.start();
		b.start();
		a!"send";
		b!"send";
		stop(6);
	}
}