import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


public class Alignment {
	
	//Scores
	public static final int DELTA = -2;
	public static final int MATCH = 1;
	
	private int[][] scoreMatrix;
	
	//moves
	private enum move {
		UP,
		RI,
		DI;
	}
	
	//matrix for movements
	private move[][] movements;
	
	private int maxLength;
	private Map<String,Integer> alpha;
	private Stack<move> moves;
	
	private String first;
	private String second;
	private StringBuilder xResult;
	private StringBuilder yResult;
	private StringBuilder res;
	
	/**
	 * Constructor for alignment
	 * @param X String which is the first String to match
	 * @param Y String which is the second String to match
	 */
	public Alignment(String X, String Y){
		//initialisations
		this.xResult = new StringBuilder();
		this.yResult = new StringBuilder();
		this.first = X;
		this.second = Y;
		this.maxLength = Integer.max(X.length()+1, Y.length()+1);
		this.scoreMatrix = new int[first.length()+1][second.length()+1];
		this.alpha = new HashMap<String,Integer>();
		this.movements = new move[first.length()+1][second.length()+1];
		this.moves = new Stack<move>();
		this.res = new StringBuilder();
		
		initialiseMap();
	}
	
	/**
	 * Initialise the Alpha Values for each combination
	 */
	private void initialiseMap(){
		this.alpha.put("GG", 1);
		this.alpha.put("CC", 1);
		this.alpha.put("AA", 1);
		this.alpha.put("TT", 1);
		
		this.alpha.put("GC", -1);
		this.alpha.put("GA", -1);
		this.alpha.put("GT", -1);
		
		this.alpha.put("CG", -1);
		this.alpha.put("CA", -1);
		this.alpha.put("CT", -1);
		
		this.alpha.put("AG", -1);
		this.alpha.put("AC", -1);
		this.alpha.put("AT", -1);
		
		this.alpha.put("TG", -1);
		this.alpha.put("TC", -1);
		this.alpha.put("TA", -1);
		
	}
	
	/**
	 * Aligns the two strings to obtain the highest score
	 * @return Returns the array of 2 strings which are aligned 
	 */
	public String[] optAlign(){
		String x = this.first;
		String y = this.second;
		StringBuilder m = new StringBuilder();
		StringBuilder n = new StringBuilder();
		
		
		
		//initialise the score matrix

		this.movements[0][0] = null;
		for(int i = 0; i < this.first.length()+1; i ++){
			this.scoreMatrix[i][0] = i * this.DELTA;
			this.movements[i][0] = move.UP;
		}
		for(int i = 0; i < this.second.length()+1; i ++){
			this.scoreMatrix[0][i] = i * this.DELTA;
			this.movements[0][i] = move.RI;
		}
		this.movements[0][0] = null;
		
		for(int j = 1; j <= y.length(); j++){
			for(int i = 1; i <= x.length(); i++){
				this.scoreMatrix[i][j] = OPT(i,j);
//				System.out.println(OPT(i,j));
			}
		}
		System.out.println("\n Score Matrix:");
		for(int i = this.first.length()+1- 1; i >= 0 ; i--){
			for(int j = 0; j < this.second.length()+1; j++){
				System.out.print(this.scoreMatrix[i][j] + " \t| ");
			}
			System.out.print("\n");
		}
//		this.populateMovements();
		for(int i = this.first.length(); i >= 0 ; i--){
			for(int j = 0; j < this.second.length()+1; j++){
//				System.out.print(this.movements[i][j] + " | ");
			}
//			System.out.print("\n");
		}
	
		this.createResult();
		System.out.println("Alignment:");
		System.out.println(this.xResult.toString());
		System.out.println(this.yResult.toString());
		System.out.println(this.res.toString());
		System.out.println("FinalScore: " + this.scoreMatrix[this.first.length()][this.second.length()]);
		String[] result = {this.xResult.toString(), this.yResult.toString()};
		return result;
	}
	
	/**
	 * Recursively finds the alignment to find the highest score
	 * @param x the first string to be aligned
	 * @param y the second string to be aligned
	 * @return returns the score for the alignment
	 */
	private int OPT(int x, int y){
		if(x == 0 && y == 0){
			return 0;
		}
		else if(x == 0 && y != 0){
			this.movements[x][y] = move.RI;
			return DELTA + OPT(x,y-1);
		}
		else if(y == 0 && x !=  0){
			this.movements[x][y] = move.UP;
			return DELTA + OPT(x-1,y);
		}
		
		StringBuilder alphaCode = new StringBuilder();
		alphaCode.append(this.first.substring(x-1, x)).append(this.second.substring(y-1, y));
		
		int alphaScore = this.alpha.get(alphaCode.toString());
		
		int a = alphaScore + OPT(x-1,y-1);
		int b = this.DELTA + OPT(x-1,y);
		int c = this.DELTA + OPT(x,y-1);
		
		if(a >= b && a >= c){
			this.movements[x][y] = move.DI;
			return a;
		}
		else if(b > c && b > a){
			this.movements[x][y] = move.UP;
			return b;
			
		}
		else if(c > b && c > a){
			this.movements[x][y] = move.RI;
			return c;
		}
		else return -1;
//		return Integer.max(a, Integer.max(b, c));
	}
	
	/**
	 * Method used to create the final string alignment
	 */
	private void createResult(){
		this.populateMoves();
		int x = this.first.length();
		int y = this.second.length();
		
		int i = 0;
		int j = 0;
		while(!this.moves.isEmpty()){
			move m = this.moves.pop();
			
			
			if(m == move.DI){
				String xChar = this.first.substring(i, i+1);
				String yChar = this.second.substring(j, j+1);
				this.xResult.append(xChar);
				this.yResult.append(yChar);
				
				if(xChar.equals(yChar)){
					this.res.append("+");
				}
				else{
					this.res.append("-");
				}
				i++;
				j++;
			}
			else if(m == move.UP){
				this.xResult.append(this.first.substring(i, i+1));
				this.yResult.append("-");
				this.res.append("*");
				i++;
			}
			else if(m == move.RI){
				this.xResult.append("-");
				this.yResult.append(this.second.substring(j, j+1));
				this.res.append("*");
				j++;
			}
		}

	}
	
	/**
	 * Method to populate the movement matrix
	 */
	private void populateMoves(){
		int i = this.first.length();
		int j = this.second.length();
		
		while(i!=0 || j!=0){
			move m = this.movements[i][j];
			
			this.moves.push(m);
			if(m == move.DI){
				i--;
				j--;
			}
			else if(m == move.RI){
				j--;
			}
			else if(m == move.UP){
				i--;
			}
		}
	}
	
	
	
}
