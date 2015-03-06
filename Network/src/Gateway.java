import java.util.ArrayList;
import java.util.Random;

public class Gateway 
{
	//Tutti i dispositivi della rete
	private ArrayList<Dispositive> disp=new ArrayList<Dispositive>();
	//Numero operazioni
	private int op=0;
	
	//Due alla volta
	private void testPhoneTablet() 
	{
		Dispositive a=new Phone(0);
		Dispositive b=new Tablet(1);
		startTest(a, b);
	}
	
	//Due alla volta
	private void testComputerPhone() 
	{
		Dispositive a=new Computer(0);
		Dispositive b=new Phone(1);
		startTest(a, b);
	}
	
	//Quattro insieme e poi uno alla volta
	private void testComputerTablet() 
	{
		Dispositive a=new Computer(0);
		Dispositive b=new Tablet(1);
		startTest(a, b);
	}
	
	//Tutto insieme
	private void testComputerComputer() 
	{
		Dispositive a=new Computer(0);
		Dispositive b=new Computer(1);
		startTest(a, b);
	}
	
	//Due alla volta
	private void testTabletTablet() 
	{
		Dispositive a=new Tablet(0);
		Dispositive b=new Tablet(1);
		startTest(a, b);
	}
		
	//Due alla volta
	private void testPhonePhone() 
	{
		Dispositive a=new Phone(0);
		Dispositive b=new Phone(1);
		startTest(a, b);
	}
	
	private void startTest(Dispositive a, Dispositive b) 
	{
		a.setOperations(6);
		b.setOperations(6);
		a.register(b, 0, 1);
		b.register(a, 1, 0);
		a.addOp(new Operation(a, b, "Ciao", 0, 1, false));
		a.addOp(new Operation(a, b, "Ciao", 0, 1, false));
		a.addOp(new Operation(a, b, "Ciao", 0, 1, false));
		b.addOp(new Operation(b, a, "Ciao", 1, 0, false));
		b.addOp(new Operation(b, a, "Ciao", 1, 0, false));
		b.addOp(new Operation(b, a, "Ciao", 1, 0, false));
		new Thread(a).start();
		new Thread(b).start();
	}
	
	private void start()
	{
		final int nDisp=3;
		//Crea computer, tablet o phone
		for (int i=0; i<nDisp; i++)
		{
			Random rand = new Random();
		    int type = rand.nextInt((2 - 0) + 1) + 0;
		    Dispositive d;
		    switch(type)
		    {
		    	case 0: 
	    			d=new Computer(i);
	    			disp.add(d);
	    			break; 
		    	case 1: 
	    			d=new Phone(i); 
	    			disp.add(d);
	    			break; 
		    	case 2: 
	    			d=new Tablet(i);
	    			disp.add(d);
	    			break; 
		    }		    
		}
		
		//Fa conoscere ad ogni dispositivo tutti gli altri
		register();
		//Crea per ogni dispositivo, un numero casuale di operazioni con destinatario sempre casuale
		operations();
		//Avvia i dispositivi
		for(int i=0; i<disp.size(); i++)
		{
			new Thread(disp.get(i)).start();
		}
	}
	
	//Crea per ogni dispositivo, un numero casuale di operazioni con destinatario sempre casuale
	private void operations() 
	{
		final int minOp=0;
		final int maxOp=2;
		//Per ogni dispositivo
		for (int i=0; i<disp.size(); i++)
		{
			//Creo un numero casuale di operazioni da fare
			Random rand = new Random();
			int operations = rand.nextInt((maxOp - minOp) + 1) + minOp;
			op+=operations;
			for (int j=0;j<operations;j++)
			{
				//Scelgo il destinatario casualmente
				int to = rand.nextInt(((disp.size()-1) - 0) + 1) + 0;
				if(i!=to)
				{
					String message=new String("Messaggio inviato da "+i+" a "+to+".");
					int bro = rand.nextInt((1 - 0) + 1) + 0;
					boolean br=false;
					//Se e' un broadcast, devo inviarla a tutti
					if(bro==1)
					{
						br=true;
						for(int k=0; k<disp.size(); k++)
						{
							if (k!=i)
							{
								disp.get(i).addOp(new Operation(disp.get(i), disp.get(k), message, i, to, br));
								op+=1;
							}
						}
						System.out.println("Operazione di broadcast aggiunta da "+i);
					}
					else
					{
						disp.get(i).addOp(new Operation(disp.get(i), disp.get(to), message, i, to, br));
						op+=1;
						System.out.println("Operazione aggiunta da "+i+" a "+to);
					}
				}
			}	
			System.out.println("-----------");
		}
		
		for (int i=0; i<disp.size(); i++)
		{
			disp.get(i).setOperations(op);
		}
	}
	
	//Fa conoscere ad ogni dispositivo tutti gli altri
	private void register() 
	{
		for (int i=0; i<disp.size(); i++)
		{
			for (int j=0; j<disp.size(); j++)
			{
				if(i!=j)
					disp.get(j).register(disp.get(i), j, i);
			}
			System.out.println("...........");
		}
	}

	public static void main(String[] args) 
	{
		//new Gateway().start();
		//new Gateway().testPhoneTablet(); //Ok: Due alla volta
		//new Gateway().testComputerPhone(); //Ok: Due alla volta
		new Gateway().testComputerTablet(); //Ok: Quattro insieme e uno alla volta dopo
		//new Gateway().testComputerComputer(); //Ok: Tutti insieme
		//new Gateway().testPhonePhone(); //Ok: Due alla volta
		//new Gateway().testTabletTablet(); //Ok: Due alla volta
	}
}
