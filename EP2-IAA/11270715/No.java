//Josimar Amaro de Sousa - 11270715

import java.util.*;

public class No{
	
	private Posicao posAtual;
	private No posAnterior;
	private Item item;
	private int valorItem;
	
	public No(Posicao posAtual){
		this.posAtual = posAtual;
		this.posAnterior = null;
		this.item = null;
		
		if(posAnterior != null){
			this.valorItem = getPosAnterior().valorItem + valorItem;
		}else this.valorItem = valorItem;
	}
	
	public No(Posicao posAtual, No posAnterior){
		this.posAtual = posAtual;
		this.posAnterior = posAnterior;
		this.item = null;
		
		if(posAnterior != null){
			this.valorItem = getPosAnterior().valorItem + valorItem;
		}else this.valorItem = valorItem;
	}
	
	public No(Posicao posAtual, No posAnterior, Item item){
		this.posAtual = posAtual;
		this.posAnterior = posAnterior;
		this.item = item;

		if(posAnterior != null){
			this.valorItem = getPosAnterior().valorItem + valorItem;
		}else this.valorItem = valorItem;
	}
	
	public No(Posicao posAtual, No posAnterior, Item item, int valorItem){
		this.posAtual = posAtual;
		this.posAnterior = posAnterior;
		this.item = item;
		
		if(posAnterior != null){
			this.valorItem = getPosAnterior().valorItem + valorItem;
		}else this.valorItem = valorItem;
		
	}
	
	public Posicao getPosAtual(){
		return this.posAtual;
	}
	public No getPosAnterior(){
		return this.posAnterior;
	}
	public Item getItem(){
		return this.item;
	}
	public int getValorItem(){
		
		return this.valorItem;
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
	
	/*
	public String toString(){
		
		String s = " ";
		s += "posAtualX " + getPosAtualX() + " posAtualX " +getPosAtualY() + " posAnteriorX " + getPosAnterior().getPosAtualX() + " posAnteriorY " + getPosAnterior().getPosAtualY() + " item " + item + " valorItem " + valorItem;
		return s;
	}*/
}
