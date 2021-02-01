import java.util.*;

public class No{
	
	private Posicao posAtual;
	private No posAnterior = null;
	private int numVertice;
	private List <Aresta> aresta;
	
	public No(Posicao posAtual){
		this.posAtual = posAtual;	
	}
	
	public No(Posicao posAtual, No posAnterior){
		this.posAtual = posAtual;
		this.posAnterior = posAnterior;
	}
	
	public No(int numVertice){
		this.numVertice = numVertice;
		aresta = new ArrayList<Aresta>();
	}
	
	public void adicionaAresta(int numVertice, int peso){
		Aresta a = new Aresta(numVertice, peso);
		aresta.add(a);
	}
	public Posicao getPosAtual(){
		return this.posAtual;
	}
	public No getPosAnterior(){
		return this.posAnterior;
	}

//	Acessando 'lin' e 'col' do objeto do tipo Posicao
	public int getPosAtualX(){
		
		return this.posAtual.getX();
	}
	public int getPosAtualY(){
		
		return this.posAtual.getY();
	}
	
	public int getPosAnteriorX(){
		
		return this.getPosAtualX();
	}
	public int getPosAnteriorY(){
		
		return this.getPosAtualY();
	}
	
	public String toString(){
		
		String s = "numVertice " + numVertice + " ";
		for(int i=0; i < aresta.size(); i++){
			s += aresta.get(i) + " ";
		}
		
		return s;
	}
	
}
