package com.example.chess.Pieces;

import com.example.chess.Coordinates;
import com.example.chess.Position;

import java.util.ArrayList;

public class Rook extends Piece {

    private boolean castleable;

    public Rook(boolean white) {
        super(white);
        this.castleable = true;
    }

    public void setCastleable(boolean castleable) {
        this.castleable = castleable;
    }

    public boolean isCastleable() {
        return castleable;
    }

    @Override
    public ArrayList<Coordinates> allowedMoves(Position[][] board, Coordinates piecePosition) {

        ArrayList<Coordinates> allowedMoves = new ArrayList<>();
        int x = piecePosition.getX();
        int y = piecePosition.getY();

        allowedMoves.addAll(rookMoveUp(board, x, y));
        allowedMoves.addAll(rookMoveDown(board, x, y));
        allowedMoves.addAll(rookMoveRight(board, x, y));
        allowedMoves.addAll(rookMoveLeft(board, x, y));

        return allowedMoves;
    }

    private ArrayList<Coordinates> rookMoveUp(Position[][] board, int x, int y) {

        ArrayList<Coordinates> allowedMoves = new ArrayList<>();

        if(this.isWhite() && x >= 0) {
            for (int i = y-1; i >= 0; i--) {
                if(board[x][i].getPiece() == null)
                    allowedMoves.add(new Coordinates(x, i));
                else if(board[x][i].getPiece().isWhite() != this.isWhite()) {
                    allowedMoves.add(new Coordinates(x, i));
                    break;
                }
                else
                    break;
            }
        }
        else {
            for (int i = y+1; i < 8; i++) {
                if(board[x][i].getPiece() == null)
                    allowedMoves.add(new Coordinates(x, i));
                else if(board[x][i].getPiece().isWhite() != this.isWhite()) {
                    allowedMoves.add(new Coordinates(x, i));
                    break;
                }
                else
                    break;
            }
        }
        return allowedMoves;
    }

    private ArrayList<Coordinates> rookMoveDown(Position[][] board, int x, int y) {

        ArrayList<Coordinates> allowedMoves = new ArrayList<>();

        if(this.isWhite()) {
            for (int i = y+1; i < 8; i++) {
                if(board[x][i].getPiece() == null)
                    allowedMoves.add(new Coordinates(x, i));
                else if(board[x][i].getPiece().isWhite() != this.isWhite()) {
                    allowedMoves.add(new Coordinates(x, i));
                    break;
                }
                else
                    break;
            }
        }
        else {
            for (int i = y-1; i >= 0; i--) {
                if(board[x][i].getPiece() == null)
                    allowedMoves.add(new Coordinates(x, i));
                else if(board[x][i].getPiece().isWhite() != this.isWhite()) {
                    allowedMoves.add(new Coordinates(x, i));
                    break;
                }
                else
                    break;
            }
        }
        return allowedMoves;
    }

    private ArrayList<Coordinates> rookMoveRight(Position[][] board, int x, int y) {

        ArrayList<Coordinates> allowedMoves = new ArrayList<>();

        if(this.isWhite()) {
            for (int i = x+1; i < 8 && i >= 0; i++) {
                if(board[i][y].getPiece() == null)
                    allowedMoves.add(new Coordinates(i, y));
                else if(board[i][y].getPiece().isWhite() != this.isWhite()) {
                    allowedMoves.add(new Coordinates(i, y));
                    break;
                }
                else
                    break;
            }
        }
        else {
            for (int i = x-1; i >= 0; i--) {
                if(board[i][y].getPiece() == null)
                    allowedMoves.add(new Coordinates(i, y));
                else if(board[i][y].getPiece().isWhite() != this.isWhite()) {
                    allowedMoves.add(new Coordinates(i, y));
                    break;
                }
                else
                    break;
            }
        }
        return allowedMoves;
    }

    private ArrayList<Coordinates> rookMoveLeft(Position[][] board, int x, int y) {

        ArrayList<Coordinates> allowedMoves = new ArrayList<>();

        if(this.isWhite()) {
            for (int i = x-1; i >= 0; i--) {
                if(board[i][y].getPiece() == null)
                    allowedMoves.add(new Coordinates(i, y));
                else if(board[i][y].getPiece().isWhite() != this.isWhite()) {
                    allowedMoves.add(new Coordinates(i, y));
                    break;
                }
                else
                    break;
            }
        }
        else {
            for (int i = x+1; i < 8; i++) {
                if(board[i][y].getPiece() == null)
                    allowedMoves.add(new Coordinates(i, y));
                else if(board[i][y].getPiece().isWhite() != this.isWhite()) {
                    allowedMoves.add(new Coordinates(i, y));
                    break;
                }
                else
                    break;
            }
        }
        return allowedMoves;
    }
}
