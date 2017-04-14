import java.util.*;
import java.io.File;

class TestNinePuzzle{

	public static void main(String[] args){
		NinePuzzle puz = new NinePuzzle();

		TestGetIndexOfEmpty(puz);
		TestCreateGraph(puz);
		TestBFS(puz);

	}

	public static void TestSolveNinePuzzle(int[][] B){
		
		
	}

	public static void TestPrintBoard(int[][] B){


	}

	public static void TestGetIndexFromBoard(int[][] B){

	}

	public static void TestGetBoardFromIndex(int id){


	}

	public static void TestGetIndexOfEmpty(NinePuzzle puz){
		System.out.println("\n\n\nTESTING getIndexOfEmpty...");

		int[][] goalBoard = puz.getBoardFromIndex(0);
		int[] emptyTile = puz.getIndexOfEmpty(goalBoard);
		int[] expected = {2,2};

		if(emptyTile != expected){
			System.out.println("Pass: {" + emptyTile[0] + ", " + emptyTile[1] + "} == " + "{2, 2}" );
		}
			
		else
			System.out.println("Fail: {" + emptyTile[0] + ", " + emptyTile[1] + "} != " + "{2, 2}" );

	}

	public static void TestCreateGraph(NinePuzzle puz){
		System.out.println("\n\n\nTESTING createGraph() ....");
		int[][] adjacentBoard1 = {{1,2,3}, {4,5,6}, {7,0,8}};
		int[][] adjacentBoard2 = {{1,2,3}, {4,5,0}, {7,8,6}};

		puz.printBoard(adjacentBoard1);
		puz.printBoard(adjacentBoard2);

		int[][] G = puz.createGraph();
		System.out.println("this is adjacentBoard1: " + puz.getIndexFromBoard(adjacentBoard1));
		System.out.println("G2: " + G[0][2]);

		System.out.println("this is adjacentBoard2: " + puz.getIndexFromBoard(adjacentBoard2));
		System.out.println("G1: " + G[0][1]);
		// puz.printBoard();

		System.out.println("OUTSIDE WHILE: this is the current value of EdgeTo: " + G[0][4]);
		int i = 2;
		while(G[0][i] != -1 && i<5 ){
			System.out.println("this is the current value of EdgeTo: " + G[0][i]);
			System.out.println("This is i before ++: " + i);	
			int autoAdj = G[0][i];
			if(puz.getIndexFromBoard(adjacentBoard2) == autoAdj || puz.getIndexFromBoard(adjacentBoard1) == autoAdj){
				System.out.println("Pass board comparisons");
			}
			else{
				System.out.println("Fail board comparisons");
			}
			i++;
			System.out.println("This is i after ++: " + i);	
		}

		// if(G[0][1] puz.getIndexFromBoard(goalBoard))

		//System.out.println("\n\n\nTESTING THE GRAPH ....");


	}

	public static void TestBFS(NinePuzzle puz){
		System.out.println("\n\n\nTESTING BFS() ....");
		int[][] G = puz.createGraph();
		

		if(puz.BFS(G, 122964)){

			System.out.println("\n\n\n GETTING SHORTEST PATH ....");
		
			int i = G[0][1];
			puz.printBoard(puz.getBoardFromIndex(0));

			while(i != -1){

				System.out.println("this is id: " + i);
				puz.printBoard(puz.getBoardFromIndex(i));
				i = G[i][1];

			}

		}

		else{
			System.out.println("Is not solvable");
		}

		
		//System.out.println(0);

		// System.out.println("\n");
		// puz.printBoard(puz.getBoardFromIndex(8));

		// System.out.println("\n");
		// puz.printBoard(puz.getBoardFromIndex(2933));
		// int[] testPath1 = {8,0};
		// int[] path = puz.BFS(G, 8);

		// if(puz.BFS(G, 8))
		// 	System.out.println("Pass:");
		// else
		// 	System.out.println("Fail:");

		// if(Arrays.equals(testPath1, path))
		// 	System.out.println("Pass: " + path + " == " + testPath1);
		// else
		// 	System.out.println("Fail: " + path + " != " + testPath1);
	}


}