package edu.wm.cs.cs301.slidingpuzzle;

public class SimplePuzzleState implements PuzzleState {

	private PuzzleState parent;
	private int[][] board;
	private Operation operation;
	private int pathLength;
	
	public SimplePuzzleState() {
		
	}
	
	private SimplePuzzleState(PuzzleState p, int[][] b, Operation op, int pL) {//Private Constructor that lets you create a new SimplePuzzleState with set fields
		// TODO Auto-generated constructor stub
		parent = p;
		board = b;
		operation = op;
		pathLength = pL;
	}
	
	@Override
	public void setToInitialState(int dimension, int numberOfEmptySlots) {
		// TODO Auto-generated method stub
		pathLength = 0;
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

}
