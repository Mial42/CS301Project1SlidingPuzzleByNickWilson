package edu.wm.cs.cs301.slidingpuzzle;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimplePuzzleState implements PuzzleState {

	private PuzzleState parent;
	private int[][] board;
	private Operation operation;
	private int pathLength;
	private int dim;
	
	public SimplePuzzleState() {
		
	}
	
	private SimplePuzzleState(PuzzleState p, int[][] b, Operation op, int pL, int d) {//Private Constructor that lets you create a new SimplePuzzleState with set fields
		parent = p;
		board = b;
		operation = op;
		pathLength = pL;
		dim = d;
	}
	
	@Override
	public void setToInitialState(int dimension, int numberOfEmptySlots) {
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
		return board[row][column];
	}

	@Override
	public PuzzleState getParent() {
		return parent;
	}

	@Override
	public Operation getOperation() {
		return operation;
	}

	@Override
	public int getPathLength() {
		return pathLength;
	}

	@Override
	public PuzzleState move(int row, int column, Operation op) {
		if (isMovePossible(row, column, op)) { //Is the move possible?
			int[][] newBoard; 
			switch(op) {
				case MOVELEFT: //In each operation:
					newBoard = swap(row, column, row, column - 1); //Create a new board with the values swapped
					return new SimplePuzzleState(this, newBoard, op, pathLength + 1, dim); //Create and return a new SimplePuzzleState
				case MOVERIGHT:
					newBoard = swap(row, column, row, column + 1);
					return new SimplePuzzleState(this, newBoard, op, pathLength + 1, dim); //Create and return a new SimplePuzzleState
				case MOVEUP:
					newBoard = swap(row, column, row - 1, column);
					return new SimplePuzzleState(this, newBoard, op, pathLength + 1, dim); //Create and return a new SimplePuzzleState
				case MOVEDOWN:
					newBoard = swap(row, column, row + 1, column);
					return new SimplePuzzleState(this, newBoard, op, pathLength + 1, dim); //Create and return a new SimplePuzzleState
			}
		}
		return null;
	}

	@Override
	public PuzzleState drag(int startRow, int startColumn, int endRow, int endColumn) {
		return null;
	}

	@Override
	public PuzzleState shuffleBoard(int pL) { //Changed variable name to not conflict with field
		Set<PuzzleState> previousStates = new HashSet<PuzzleState>();
		SimplePuzzleState curPuzzleState = this;
		for(int k = 0; k < pL;) { //Do this (create a semi-random board one move away) pL number of times
			//Find a position with an empty neighbor non-deterministically
			Object[] eNL = getPosWithEmptyNeighborAndLegalMove(curPuzzleState); //Empty Neighbor Legal
			int row = (int)eNL[0];
			int col = (int)eNL[1];
			Operation op = (Operation)eNL[2];
			
			//Move that position to its empty neighbor
			curPuzzleState = (SimplePuzzleState)curPuzzleState.move(row, col, op);
			
			//If the new board isn't a duplicate, swap to it and add 1
			if(!previousStates.contains(curPuzzleState)) {
				k++;
			}
			else {
				curPuzzleState = (SimplePuzzleState)curPuzzleState.getParent();
			}
			
		}
		return curPuzzleState;
	}
	/*Returns an array
	 * emptyNeighborLegal[0] is a row number
	 * emptyNeighborLegal[1] is a col number
	 * emptyNeighborLegal[1] is a legal move
	 */ 
	private Object[] getPosWithEmptyNeighborAndLegalMove(SimplePuzzleState p) { //Non-deterministically get a value and a legal move
		Object[] emptyNeighborLegal = new Object[3];
		int startRow = (int)(Math.random() * dim);
		int startCol = (int)(Math.random() * dim);	//Non determinism comes from random starting position
		for(int r = startRow; r < dim; r++) {
			for(int c = startCol; c < dim; c++) {
				if(r > 0 && p.board[r - 1][c] == 0) { //If there are multiple legal moves, defaults to this order.
					emptyNeighborLegal[0] = r; //Shouldn't cause infinite loops thanks to random starting position
					emptyNeighborLegal[1] = c;
					emptyNeighborLegal[2] = Operation.MOVEUP;
					return emptyNeighborLegal;
				}
				if(r < dim - 1 && p.board[r + 1][c] == 0) {
					emptyNeighborLegal[0] = r;
					emptyNeighborLegal[1] = c;
					emptyNeighborLegal[2] = Operation.MOVEDOWN;
					return emptyNeighborLegal;
				}
				if(c > 0 && p.board[r][c - 1] == 0) {
					emptyNeighborLegal[0] = r;
					emptyNeighborLegal[1] = c;
					emptyNeighborLegal[2] = Operation.MOVELEFT;
					return emptyNeighborLegal;
				}
				if(c < dim - 1 && p.board[r][c + 1] == 0) {
					emptyNeighborLegal[0] = r;
					emptyNeighborLegal[1] = c;
					emptyNeighborLegal[2] = Operation.MOVERIGHT;
					return emptyNeighborLegal;
				}
			}
		}
		for(int r = 0; r < startRow; r++) {
			for(int c = 0; c < startCol; c++) {
				if(r > 0 && p.board[r - 1][c] == 0) {
					emptyNeighborLegal[0] = r;
					emptyNeighborLegal[1] = c;
					emptyNeighborLegal[2] = Operation.MOVEUP;
					return emptyNeighborLegal;
				}
				if(r < dim - 1 && p.board[r + 1][c] == 0) {
					emptyNeighborLegal[0] = r;
					emptyNeighborLegal[1] = c;
					emptyNeighborLegal[2] = Operation.MOVEDOWN;
					return emptyNeighborLegal;
				}
				if(c > 0 && p.board[r][c - 1] == 0) {
					emptyNeighborLegal[0] = r;
					emptyNeighborLegal[1] = c;
					emptyNeighborLegal[2] = Operation.MOVELEFT;
					return emptyNeighborLegal;
				}
				if(c < dim - 1 && p.board[r][c + 1] == 0) {
					emptyNeighborLegal[0] = r;
					emptyNeighborLegal[1] = c;
					emptyNeighborLegal[2] = Operation.MOVERIGHT;
					return emptyNeighborLegal;
				}
			}
		}
		return emptyNeighborLegal;
	}

	private Operation genRandomLegalOperation(int row, int col) { //Generates a semi-random, legal operation
		boolean[] legalOps = legalOperations(row, col);
		List<Integer> randomInts1to4 = Arrays.asList(1, 2, 3, 4); //4 possible moves, not 4x4 dimension
		Collections.shuffle(randomInts1to4);
		//Return null if there are no legal moves
		
		for(int i = 0; i < 3; i++) {
			int r = randomInts1to4.get(i);//Random Int from 1 to 4 
			if(legalOps[r] == true && r == 0) {
				return Operation.MOVEUP; //Once you hit a legal move, return it
			}
			else if(legalOps[r] == true && r == 1) {
				return Operation.MOVEDOWN;
			}
			else if(legalOps[r] == true && r == 2) {
				return Operation.MOVELEFT;
			}
			else if(legalOps[r] == true && r == 3) {
				return Operation.MOVERIGHT;
			}
		}
		return null;
	}
	//Returns an array of booleans corresponding to legalOperations
	//legalOps[0] = true means you can move up
	//legalOps[1] = true means you can move down
	//legalOps[2] = true means you can move left
	//legalOps[3] = true means you can move right
	private boolean[] legalOperations(int row, int col) {
		boolean[] legalOps = {false, false, false, false};
		if(row != 0 && isEmpty(row - 1, col)) {
			legalOps[0] = true;
		}
		if(row != dim - 1 && isEmpty(row + 1, col)) {
			legalOps[1] = true;
		}
		if(col != 0 && isEmpty(row, col - 1)) {
			legalOps[2] = true;
		}
		if(col != dim - 1 && isEmpty(row, col + 1)) {
			legalOps[3] = true;
		}
		return legalOps;
	}
	@Override //Overriding the equals method
	public boolean equals(Object o) { //Returns true if two puzzlestates have exactly the same board
		if(o == null) {
			return false;
		}
		if(this.getClass() != o.getClass())
			return false;
		SimplePuzzleState p = (SimplePuzzleState)o;
		return Arrays.deepEquals(board, p.board);
	}
	
    @Override
	public int hashCode() {
		return Arrays.deepHashCode(board);
	} 
	
    
	@Override
	public boolean isEmpty(int row, int column) {
		return(board[row][column]==0);
	}

	@Override
	public PuzzleState getStateWithShortestPath() {
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
		int[][] newBoard = new int[dim][dim];
		for(int r = 0; r < dim; r++) {
			newBoard[r] = board[r].clone();
		}
		
		int val1 = newBoard[r1][c1];
		newBoard[r1][c1] = newBoard[r2][c2];
		newBoard[r2][c2]= val1; 
		return newBoard;
	}
}
