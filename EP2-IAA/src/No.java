public class No{
	
	private Posicao posAtual;
	private No posAnterior = null;
		
	public No(Posicao posAtual){
		this.posAtual = posAtual;	
	}
	
	public No(Posicao posAtual, No posAnterior){
		this.posAtual = posAtual;
		this.posAnterior = posAnterior;
	}
	
	public Posicao getPosAtual(){
		return this.posAtual;
	}
	public No getPosAnterior(){
		return this.posAnterior;
	}
	
	public int getPosAtualX(){
		
		return getPosAtual().getX();
	}
	public int getPosAtualY(){
		
		return getPosAtual().getY();
	}
	/*
	public int getPosAnteriorX(){
		
		return getPosAnterior().getX();
	}
	public int getPosAnteriorY(){
		
		return getPosAnterior().getY();
	}
	*/
	public int sumPosAtualX(int i){
		return getPosAtualX() + i;
	}
	public int sumPosAtualY(int j){
		return getPosAtualX() + j;
	}
	
	
}