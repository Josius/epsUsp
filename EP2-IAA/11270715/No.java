//Josimar Amaro de Sousa - 11270715

import java.util.*;

public class No{
	
	private Posicao posAtual;
	private No posAnterior;
	private Item item = null;
	private int valorItem = 0;
	private int vertice;
	
	public No(Posicao posAtual){
		this.posAtual = posAtual;
		this.posAnterior = null;
	}
	public No(Posicao posAtual, int vertice){
		this.posAtual = posAtual;
		this.posAnterior = null;
		this.vertice = vertice;
	}
	public No(Posicao posAtual, No posAnterior){
		this.posAtual = posAtual;
		this.posAnterior = posAnterior;
	}
	
	public No(Posicao posAtual, No posAnterior, Item item){
		this.posAtual = posAtual;
		this.posAnterior = posAnterior;
		this.item = item;
		this.valorItem = item.getValue();
	}
	
	public No(Posicao posAtual, No posAnterior, Item item, int valorItem){
		this.posAtual = posAtual;
		this.posAnterior = posAnterior;
		this.item = item;
		this.valorItem = valorItem;	
	}
	
	public No(Posicao posAtual, No posAnterior, int vertice){
		this.posAtual = posAtual;
		this.posAnterior = posAnterior;
		this.vertice = vertice;

	}
	
	public No(Posicao posAtual, No posAnterior, int vertice, Item item, int valorItem){
		this.posAtual = posAtual;
		this.posAnterior = posAnterior;
		this.item = item;
		this.valorItem = item.getValue();	
		this.vertice = vertice;
		
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
	public int getVertice(){
		
		return this.vertice;
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
	public void setValorItem(int valor){
		this.valorItem = valor;
	}
	public void setPosAnterior(No posAnterior){
		this.posAnterior = posAnterior;
	}
	/*
	public String toString(){
		
		String s = " ";
		s += "posAtualX " + getPosAtualX() + " posAtualX " +getPosAtualY() + " posAnteriorX " + getPosAnterior().getPosAtualX() + " posAnteriorY " + getPosAnterior().getPosAtualY() + " item " + item + " valorItem " + valorItem;
		return s;
	}*/
}
