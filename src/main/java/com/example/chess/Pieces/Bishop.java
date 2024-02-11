package com.example.chess.Pieces;

import com.example.chess.Coordinates;
import com.example.chess.Position;

import java.util.ArrayList;

public class Bishop extends Piece {

    public Bishop(boolean white) {
        super(white);
    }

    @Override
    public ArrayList<Coordinates> allowedMoves(Position[][] board, Coordinates piecePosition) {

        ArrayList<Coordinates> allowedMoves = new ArrayList<>();
        int x = piecePosition.getX();
        int y = piecePosition.getY();

        allowedMoves.addAll(bishopRightUpDiagonal(board, x, y));
        allowedMoves.addAll(bishopLeftDownDiagonal(board, x, y));
        allowedMoves.addAll(bishopLeftUpDiagonal(board, x, y));
        allowedMoves.addAll(bishopRightDownDiagonal(board, x, y));

        return allowedMoves;
    }

    private ArrayList<Coordinates> bishopRightUpDiagonal(Position[][] board, int x, int y) {

        ArrayList<Coordinates> allowedMoves = new ArrayList<>();

        if(this.isWhite()) {
            for(int i = x+1, j = y-1; i < 8 && j >= 0; i++, j--) {
                if(board[i][j].getPiece() == null) {
                    allowedMoves.add(new Coordinates(i, j));
                }
                else if(board[i][j].getPiece().isWhite() != this.isWhite()) {
                    allowedMoves.add(new Coordinates(i, j));
                    break;
                }
                else
                    break;
            }
        }
        else {
            for(int i = x-1, j = y+1; i >= 0 && j < 8; i--, j++) {
                if(board[i][j].getPiece() == null) {
                    allowedMoves.add(new Coordinates(i, j));
                }
                else if(board[i][j].getPiece().isWhite() != this.isWhite()) {
                    allowedMoves.add(new Coordinates(i, j));
                    break;
                }
                else
                    break;
            }
        }
        return allowedMoves;
    }

    private ArrayList<Coordinates> bishopRightDownDiagonal(Position[][] board, int x, int y) {

        ArrayList<Coordinates> allowedMoves = new ArrayList<>();

        if(this.isWhite()) {
            for(int i = x+1, j = y+1; i < 8 && j < 8; i++, j++) {
                if(board[i][j].getPiece() == null) {
                    allowedMoves.add(new Coordinates(i, j));
                }
                else if(board[i][j].getPiece().isWhite() != this.isWhite()) {
                    allowedMoves.add(new Coordinates(i, j));
                    break;
                }
                else
                    break;
            }
        }
        else {
            for(int i = x-1, j = y-1; i >= 0 && j >= 0; i--, j--) {
                if(board[i][j].getPiece() == null) {
                    allowedMoves.add(new Coordinates(i, j));
                }
                else if(board[i][j].getPiece().isWhite() != this.isWhite()) {
                    allowedMoves.add(new Coordinates(i, j));
                    break;
                }
                else
                    break;
            }
        }
        return allowedMoves;
    }

    private ArrayList<Coordinates> bishopLeftUpDiagonal(Position[][] board, int x, int y) {

        ArrayList<Coordinates> allowedMoves = new ArrayList<>();

        if(this.isWhite()) {
            for(int i = x-1, j = y-1; i >= 0 && j >= 0; i--, j--) {
                if(board[i][j].getPiece() == null) {
                    allowedMoves.add(new Coordinates(i, j));
                }
                else if(board[i][j].getPiece().isWhite() != this.isWhite()) {
                    allowedMoves.add(new Coordinates(i, j));
                    break;
                }
                else
                    break;
            }
        }
        else {
            for(int i = x+1, j = y+1; i < 8 && j < 8; i++, j++) {
                if(board[i][j].getPiece() == null) {
                    allowedMoves.add(new Coordinates(i, j));
                }
                else if(board[i][j].getPiece().isWhite() != this.isWhite()) {
                    allowedMoves.add(new Coordinates(i, j));
                    break;
                }
                else
                    break;
            }
        }
        return allowedMoves;
    }

    private ArrayList<Coordinates> bishopLeftDownDiagonal(Position[][] board, int x, int y) {

        ArrayList<Coordinates> allowedMoves = new ArrayList<>();

        if(this.isWhite()) {
            for(int i = x-1, j = y+1; i >= 0 && j < 8; i--, j++) {
                if(board[i][j].getPiece() == null) {
                    allowedMoves.add(new Coordinates(i, j));
                }
                else if(board[i][j].getPiece().isWhite() != this.isWhite()) {
                    allowedMoves.add(new Coordinates(i, j));
                    break;
                }
                else
                    break;
            }

        }
        else {
            for(int i = x+1, j = y-1; i < 8 && j >= 0; i++, j--) {
                if(board[i][j].getPiece() == null) {
                    allowedMoves.add(new Coordinates(i, j));
                }
                else if(board[i][j].getPiece().isWhite() != this.isWhite()) {
                    allowedMoves.add(new Coordinates(i, j));
                    break;
                }
                else
                    break;
            }
        }
        return allowedMoves;
    }
}
