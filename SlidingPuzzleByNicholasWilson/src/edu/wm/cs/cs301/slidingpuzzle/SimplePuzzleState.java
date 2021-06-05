package edu.wm.cs.cs301.slidingpuzzle;

public class SimplePuzzleState implements PuzzleState {

	private PuzzleState parent;
	private int[][] board;
	private Operation operation;
	private int pathLength;
	private int dim;
	
	public SimplePuzzleState() {
		
	}
	
	private SimplePuzzleState(PuzzleState p, int[][] b, Operation op, int pL, int d) {//Private Constructor that lets you create a new SimplePuzzleState with set fields
		// TODO Auto-generated constructor stub
		parent = p;
		board = b;
		operation = op;
		pathLength = pL;
		dim = d;
	}
	
	@Override
	public void setToInitialState(int dimension, int numberOfEmptySlots) {
		// TODO Auto-generated method stub
		pathLength = 0;
		dim = dimension;
		board = new int[dimension][dimension];
		for(int row = 0; row < dimension; row++) {
			for(int col = 0; col < dimension; col++) {
				int linearIndex = row * dimension + col;
				if(dimension * dimension - linearIndex - numberOfEmptySlots > 0) {
					board[row][col] = linearIndex + 1;
				}
				else {
					board[row][col] = 0;
				}
			}
		}
	}

	@Override
	public int getValue(int row, int column) {
		// TODO Auto-generated method stub
		return board[row][column];
	}

	@Override
	public PuzzleState getParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	@Override
	public Operation getOperation() {
		// TODO Auto-generated method stub
		return operation;
	}

	@Override
	public int getPathLength() {
		// TODO Auto-generated method stub
		return pathLength;
	}

	@Override
	public PuzzleState move(int row, int column, Operation op) {
		// TODO Auto-generated method stub
		if (isMovePossible(row, column, op)) { //Is the move possible?
			int[][] newBoard; 
			switch(op) {
				case MOVELEFT: //In each operation:
					newBoard = swap(row, column, row, column - 1); //Create a new board with the values swapped
				case MOVERIGHT:
					newBoard = swap(row, column, row, column + 1);
				case MOVEUP:
					newBoard = swap(row, column, row - 1, column);
				case MOVEDOWN:
					newBoard = swap(row, column, row + 1, column);
				default:
					newBoard = board;
			}
			return new SimplePuzzleState(this, newBoard, op, pathLength + 1, dim); //Create and return a new SimplePuzzleState
		}
		return null;
	}

	@Override
	public PuzzleState drag(int startRow, int startColumn, int endRow, int endColumn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuzzleState shuffleBoard(int pathLength) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty(int row, int column) {
		// TODO Auto-generated method stub
		return(board[row][column]==0);
	}

	@Override
	public PuzzleState getStateWithShortestPath() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private boolean isMovePossible(int row, int col, Operation op) { //Tells you if the move is possible
		switch(op) { //Different calculations for different moves
			case MOVERIGHT:
				if(col == dim - 1)
					return false;
				else
					return(board[row][col + 1] == 0);
			case MOVELEFT:
				if(col == 0)
					return false;
				else
					return(board[row][col - 1] == 0);
			case MOVEDOWN:
				if(row == dim - 1)
					return false;
				else
					return(board[row + 1][col] == 0);
			case MOVEUP:
				if(row == 0)
					return false;
				else
					return(board[row - 1][col] == 0);
			default:
				return false;
		}
	}
	
	private int[][] swap(int r1, int c1, int r2, int c2){ //Swaps two values and returns a new array with the values swapped
		int[][] newBoard = board.clone();
		int val1 = newBoard[r1][c1];
		newBoard[r1][c1] = newBoard[r2][c2];
		newBoard[r2][c2]= val1; 
		return newBoard;
	}
}
