/* NinePuzzle.java
   CSC 225 - Spring 2017
   Assignment 4 - Template for the 9-puzzle
   
   This template includes some testing code to help verify the implementation.
   Input boards can be provided with standard input or read from a file.
   
   To provide test inputs with standard input, run the program with
	java NinePuzzle
   To terminate the input, use Ctrl-D (which signals EOF).
   
   To read test inputs from a file (e.g. boards.txt), run the program with
    java NinePuzzle boards.txt
	
   The input format for both input methods is the same. Input consists
   of a series of 9-puzzle boards, with the '0' character representing the 
   empty square. For example, a sample board with the middle square empty is
   
    1 2 3
    4 0 5
    6 7 8
   
   And a solved board is
   
    1 2 3
    4 5 6
    7 8 0
   
   An input file can contain an unlimited number of boards; each will be 
   processed separately.
  
   B. Bird    - 07/11/2014
   M. Simpson - 11/07/2015
*/
import java.util.*;
import java.io.File;

public class NinePuzzle{

	//The total number of possible boards is 9! = 1*2*3*4*5*6*7*8*9 = 362880
	public static final int NUM_BOARDS = 362880;


	/*  SolveNinePuzzle(B)
		Given a valid 9-puzzle board (with the empty space represented by the 
		value 0),return true if the board is solvable and false otherwise. 
		If the board is solvable, a sequence of moves which solves the board
		will be printed, using the printBoard function below.
	*/
	public static boolean SolveNinePuzzle(int[][] B){
		
		/* ... Your code here ... */
		int[][] G = createGraph();
		int boardID = getIndexFromBoard(B);
		boolean isSolvable = BFS(G, boardID);

		if(isSolvable){
			//Print the shortest path boards
			LinkedList<Integer> shortestPath = new LinkedList<Integer>();
			int i = G[0][1];
			shortestPath.addFirst(0);

			while(i != -1){

				shortestPath.addFirst(i);
				i = G[i][1];

			}

			while(shortestPath.size() != 0){
				printBoard(getBoardFromIndex(shortestPath.removeFirst()));
			}
			
		}
		

		return isSolvable;
		
	}

	public static int[][] createGraph(){
		int dim = 3; //Dimension of the board is 3x3
		int[][] B;
		int[][] G = new int[NUM_BOARDS][6];// [vertexID's][Marked, PreviousNode, EdgeTo1, ... ,EdgeTo4]
		int i, j;

		for(int v = 0; v<NUM_BOARDS; v++){
			B = getBoardFromIndex(v);
			int[] emptyTile = getIndexOfEmpty(B);
			i = emptyTile[0];
			j = emptyTile[1];
			int edgeTo = 2;
			G[v][edgeTo+1] = -1;

			if(i > 0){

				int[][] Bprime1 = getBoardFromIndex(v);
				int temp = Bprime1[i-1][j];
				Bprime1[i-1][j] = 0;
				Bprime1[i][j] = temp;

				G[v][edgeTo] = getIndexFromBoard(Bprime1);
				G[v][edgeTo+1] = -1;//Minus one in the edgeTo column signals the end of edgeto Values
				edgeTo++;

			}

			if(i < dim-1){

				int[][] Bprime2 = getBoardFromIndex(v);
				int temp = Bprime2[i+1][j];
				Bprime2[i+1][j] = 0;
				Bprime2[i][j] = temp;

				G[v][edgeTo] = getIndexFromBoard(Bprime2);
				G[v][edgeTo+1] = -1;//Minus one in the edgeTo column signals the end of edgeto Values
				edgeTo++;

			}

			if(j < dim-1){

				int[][] Bprime3 = getBoardFromIndex(v);
				int temp = Bprime3[i][j+1];
				Bprime3[i][j+1] = 0;
				Bprime3[i][j] = temp;

				G[v][edgeTo] = getIndexFromBoard(Bprime3);
				G[v][edgeTo+1] = -1;//Minus one in the edgeTo column signals the end of edgeto Values
				edgeTo++;
	
			}

			if(j > 0){

				int[][] Bprime4 = getBoardFromIndex(v);
				int temp = Bprime4[i][j-1];
				Bprime4[i][j-1] = 0;
				Bprime4[i][j] = temp;

				G[v][edgeTo] = getIndexFromBoard(Bprime4);
				if(edgeTo < 4)
					G[v][edgeTo+1] = -1;//Minus one in the edgeTo column signals the end of edgeto Values
				edgeTo++;
			}


		}

		return G;

	}

	public static boolean BFS(int[][] G, int v){

		int[] path = new int[NUM_BOARDS];
		LinkedList<Integer> queue = new LinkedList<Integer>();
		G[v][0] = 1; // set v as marked
		G[v][1] = -1; // indicates that there is no previous node visited
		queue.add(v);
		boolean isSolvable = false;

		while(queue.size() != 0){

			int u = queue.poll();
			if(u == 0) isSolvable = true;

            //System.out.print(u+" \n\n");
            // printBoard(getBoardFromIndex(u));

            int i = 2;
            while(G[u][i] != -1 && i<5){

                int w = G[u][i];
                if (G[w][0] != 1){
                	G[w][1] = u; // set previous node visited as u
                    G[w][0] = 1; // set w as marked
                    queue.add(w);
                }

                i++;
            }
		}

		return isSolvable;
 
	}

	public static int[] getIndexOfEmpty(int[][] B){
		int[] empty = new int[2];
		for(int i = 0; i<3; i++){
			for(int j = 0; j<3; j++){
				if(B[i][j] == 0){
					empty[0] = i;
					empty[1] = j;
					return empty;
				}
			}
		}
		return empty;
	}
	
	/*  printBoard(B)
		Print the given 9-puzzle board. The SolveNinePuzzle method above should
		use this method when printing the sequence of moves which solves the input
		board. If any other method is used (e.g. printing the board manually), the
		submission may lose marks.
	*/
	public static void printBoard(int[][] B){
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++)
				System.out.printf("%d ",B[i][j]);
			System.out.println();
		}
		System.out.println();
	}
	
	
	/* Board/Index conversion functions
	   These should be treated as black boxes (i.e. don't modify them, don't worry about
	   understanding them). The conversion scheme used here is adapted from
		 W. Myrvold and F. Ruskey, Ranking and Unranking Permutations in Linear Time,
		 Information Processing Letters, 79 (2001) 281-284. 
	*/
	public static int getIndexFromBoard(int[][] B){
		int i,j,tmp,s,n;
		int[] P = new int[9];
		int[] PI = new int[9];
		for (i = 0; i < 9; i++){
			P[i] = B[i/3][i%3];
			PI[P[i]] = i;
		}
		int id = 0;
		int multiplier = 1;
		for(n = 9; n > 1; n--){
			s = P[n-1];
			P[n-1] = P[PI[n-1]];
			P[PI[n-1]] = s;
			
			tmp = PI[s];
			PI[s] = PI[n-1];
			PI[n-1] = tmp;
			id += multiplier*s;
			multiplier *= n;
		}
		return id;
	}
		
	public static int[][] getBoardFromIndex(int id){
		int[] P = new int[9];
		int i,n,tmp;
		for (i = 0; i < 9; i++)
			P[i] = i;
		for (n = 9; n > 0; n--){
			tmp = P[n-1];
			P[n-1] = P[id%n];
			P[id%n] = tmp;
			id /= n;
		}
		int[][] B = new int[3][3];
		for(i = 0; i < 9; i++)
			B[i/3][i%3] = P[i];
		return B;
	}
	

	public static void main(String[] args){
		/* Code to test your implementation */
		/* You may modify this, but nothing in this function will be marked */

		
		Scanner s;

		if (args.length > 0){
			//If a file argument was provided on the command line, read from the file
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			//Otherwise, read from standard input
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		
		int graphNum = 0;
		double totalTimeSeconds = 0;
		
		//Read boards until EOF is encountered (or an error occurs)
		while(true){
			graphNum++;
			if(graphNum != 1 && !s.hasNextInt())
				break;
			System.out.printf("Reading board %d\n",graphNum);
			int[][] B = new int[3][3];
			int valuesRead = 0;
			for (int i = 0; i < 3 && s.hasNextInt(); i++){
				for (int j = 0; j < 3 && s.hasNextInt(); j++){
					B[i][j] = s.nextInt();
					valuesRead++;
				}
			}
			if (valuesRead < 9){
				System.out.printf("Board %d contains too few values.\n",graphNum);
				break;
			}
			System.out.printf("Attempting to solve board %d...\n",graphNum);
			long startTime = System.currentTimeMillis();
			boolean isSolvable = SolveNinePuzzle(B);
			long endTime = System.currentTimeMillis();
			totalTimeSeconds += (endTime-startTime)/1000.0;
			
			if (isSolvable)
				System.out.printf("Board %d: Solvable.\n",graphNum);
			else
				System.out.printf("Board %d: Not solvable.\n",graphNum);
		}
		graphNum--;
		System.out.printf("Processed %d board%s.\n Average Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>1)?totalTimeSeconds/graphNum:0);

	}

}

