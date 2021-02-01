public class Aresta{
	
	private int arestaVertice;
	private int peso;
	
	public Aresta(int arestaVertice, int peso){
		
		this.arestaVertice = arestaVertice;
		this.peso = peso;
	}
	
	public String toString(){
		
		return "Aresta " + arestaVertice + " peso " + peso;
	}
}
