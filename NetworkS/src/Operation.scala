class Operation (private val gfrom: Dispositive, private val gto: Dispositive, private val gmessage: String, private val gfrom2: Int, private val gto2: Int, private val gbroadcast: Boolean)
{
	if(gbroadcast)
		println("Operazione di broadcast creata da "+gfrom2);
	else
		println("Operazione creata da "+gfrom2+" a "+gto2+".");
    def from = gfrom;
    def to = gto;
	def message = gmessage;
	def from2 = gfrom2;
	def to2 = gto2;
	def broadcast = gbroadcast;
}