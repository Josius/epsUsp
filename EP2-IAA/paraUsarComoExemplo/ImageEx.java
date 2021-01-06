//Josimar Amaro de Sousa
//11270715

// Esqueleto da classe na qual devem ser implementadas as novas funcionalidades de desenho

public class ImageEx extends Image {

	public static int cont;

	public ImageEx(int w, int h, int r, int g, int b){

		super(w, h, r, g, b);
	}

	public ImageEx(int w, int h){

		super(w, h);
	}

	public void kochCurve(int px, int py, int qx, int qy, int l){
		
		int c = 0;
//	O if-else é para casos em que queremos uma curva de Koch padrão (com os triangulos voltados para cima) ou inversa
		if(px <= super.getWidth() && qx > px){
			c = (qx-px)/3;
		}else if(qx <= super.getWidth() && px> qx){
			c = (px-qx)/3;
		}
		
		if(c < l){
			drawLine(px, py, qx, qy);
		}else{

			double deltaX = qx-px;
			double deltaY = qy-py;
			
			double x2, y2, x3, y3, x4, y4;
			
			int ax = (int)Math.round(x2 = px + deltaX/3);
			int ay = (int)Math.round(y2 = py + deltaY/3);
			
			int bx = (int)Math.round(x3 = ((px+qx)/2 - (Math.sqrt(3.0)/6)*(py-qy)));
			int by = (int)Math.round(y3 = ((py+qy)/2 - (Math.sqrt(3.0)/6)*(qx-px)));
			
			int cx = (int)Math.round(x4 = qx - deltaX/3);
			int cy = (int)Math.round(y4 = qy - deltaY/3);
			
			kochCurve(px, py, ax, ay, l);
			kochCurve(ax, ay, bx, by, l);
			kochCurve(bx, by, cx, cy, l);
			kochCurve(cx, cy, qx, qy, l);
		}
	}
	
//Desculpe professor, peguei as duas próximas funções abaixo do seu código, ehehehe, como a chamada regionFill estava saindo da matriz e não poderiamos alterar a classe Image, então trouxe essas duas funções com pequenas alterações. Sei que um if resolveria, mas achei ficava mais elegante com essas duas funções.	
	
	private int limitWidth(int value){

		return value < 0 ? 0 : (value > super.getWidth()-1 ? super.getWidth()-1 : value);	
	}
	
	private int limitHeigth(int value){

		return value < 0 ? 0 : (value > getHeight()-1 ? getHeight()-1 : value);
	}
	
	public void regionFill(int x, int y, int reference_rgb){
					
		if(super.getPixel(x, y) == reference_rgb){
			super.setPixel(x, y);
			
			regionFill(limitWidth(x-1), limitHeigth(y), reference_rgb);
			regionFill(limitWidth(x), limitHeigth(y-1), reference_rgb);
			regionFill(limitWidth(x+1), limitHeigth(y), reference_rgb);
			regionFill(limitWidth(x), limitHeigth(y+1), reference_rgb);
			
		}
		
	}
	
}
