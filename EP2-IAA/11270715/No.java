//Josimar Amaro de Sousa - 11270715

import java.util.*;

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

//	Acessando 'lin' e 'col' do objeto do tipo Posicao
	public int getPosAtualX(){
		
		return this.posAtual.getX();
	}
	public int getPosAtualY(){
		
		return this.posAtual.getY();
	}
	
	public int getPosAnteriorX(){
	
		return this.posAnterior.posAtual.getX();
	}
	public int getPosAnteriorY(){
		
		return this.posAnterior.posAtual.getY();
	}

}
