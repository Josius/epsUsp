public class Posicao{
	
//	NAO ESQUECER DE DEIXAR PRIVATE
	private int x, y;
	private Posicao parent;
	
	public Posicao(int x, int y){
		
		this.x = x;
		this.y = y;
		this.parent = null;
	}
	public Posicao(int x, int y, Posicao parent){
		
		this.x = x;
		this.y = y;
		this.parent = parent;
	}
	public Posicao(){
		
		
	}
	
	public void setX(int x){
		this.x = x;
	}
	public int getX(){
		
		return this.x;
	}
	public void setY(int y){
		this.y = y;
	}
	public int getY(){
		
		return this.y;
	}
	public Posicao getParent(){
		
		return this.parent;
	}
	
}
