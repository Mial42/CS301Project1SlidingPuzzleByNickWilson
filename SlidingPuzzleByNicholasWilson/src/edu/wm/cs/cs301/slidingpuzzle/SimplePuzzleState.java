package edu.wm.cs.cs301.slidingpuzzle;

public class SimplePuzzleState implements PuzzleState {

	private PuzzleState parent;
	private int[][] board;
	
	@Override
	public void setToInitialState(int dimension, int numberOfEmptySlots) {
		// TODO Auto-generated method stub
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
		return null;
	}

	@Override
	public Operation getOperation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPathLength() {
		// TODO Auto-generated method stub
		return 0;
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
