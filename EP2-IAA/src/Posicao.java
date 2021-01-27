public class Posicao{
	
	private int x, y;
	private Posicao anterior;
	
	public Posicao(int x, int y){
		
		this.x = x;
		this.y = y;
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
	
}
