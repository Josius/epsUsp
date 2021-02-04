import java.util.*;

public class Test{
	public static void main(String[] args){
		
		
		No no1 = new No(new Posicao(1, 22), null, null, 3);
		No no2 = new No(new Posicao(11, 33), no1, null, 4);
		No no3 = new No(new Posicao(55, 44), no1, null, no2.valorItem);
		No no4 = new No(new Posicao(66, 77), null, null, 8+no3.valorItem);
		System.out.println(no1.valorItem);
		System.out.println(no2.valorItem);
		System.out.println(no3.valorItem);
		System.out.println(no4.valorItem);
		
	}
}
