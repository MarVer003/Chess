package com.example.chess.Pieces;

import com.example.chess.Coordinates;
import com.example.chess.MainActivity;
import com.example.chess.Position;

import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(boolean white) {
        super(white);
    }


    @Override
    public ArrayList<Coordinates> allowedMoves(Position[][] board, Coordinates piecePosition) {

        ArrayList<Coordinates> allowedMoves = new ArrayList<>();
        int x = piecePosition.getX();
        int y = piecePosition.getY();

        if((y != 0 || !this.isWhite()) && (y != 7 || this.isWhite())) {
            allowedMoves.addAll(pawnMoveUp(board, x, y));
            allowedMoves.addAll(pawnEatLeftRightUp(board, x, y));
            if (MainActivity.enPassantablePeasant != null) {
                allowedMoves.addAll(enPassant(x, y));
            }
        }

        return allowedMoves;
    }

    private ArrayList<Coordinates> pawnMoveUp(Position[][] board, int x, int y) {
        final int BW = this.isWhite() ? -1 : 1;

        ArrayList<Coordinates> allowedMoves = new ArrayList<>();

        if(board[x][y+BW].getPiece() == null) {
            allowedMoves.add(new Coordinates(x, y+BW));
            if(this.isWhite() && y == 6 && board[x][y-2].getPiece() == null) {
                allowedMoves.add(new Coordinates(x, y-2));
            }
            if(!this.isWhite() && y == 1 && board[x][y+2].getPiece() == null) {
                allowedMoves.add(new Coordinates(x, y+2));
            }
        }

        return allowedMoves;
    }

    private ArrayList<Coordinates> pawnEatLeftRightUp(Position[][] board, int x, int y) {
        final int BW = this.isWhite() ? -1 : 1;

        ArrayList<Coordinates> allowedMoves = new ArrayList<>();

        // left-up
        if((!this.isWhite() || x != 0) && (this.isWhite() || x != 7)) {
            if (board[x + BW][y + BW].getPiece() != null && (board[x + BW][y + BW].getPiece().isWhite() != this.isWhite())) {
                allowedMoves.add(new Coordinates(x + BW, y + BW));
            }
        }

        // right-up
        if((this.isWhite() || x != 0) && (!this.isWhite() || x != 7)) {
            if (board[x - BW][y + BW].getPiece() != null && (board[x - BW][y + BW].getPiece().isWhite() != this.isWhite())) {
                allowedMoves.add(new Coordinates(x - BW, y + BW));
            }
        }


        return allowedMoves;
    }

    private ArrayList<Coordinates> enPassant(int x, int y) {

        ArrayList<Coordinates> allowedMove = new ArrayList<>();

        if(this.isWhite() && y == 3) {
            if(MainActivity.enPassantablePeasant.getX() == x + 1) {
                allowedMove.add(new Coordinates(x+1, y-1));
            }
            else if(MainActivity.enPassantablePeasant.getX() == x - 1) {
                allowedMove.add(new Coordinates(x-1, y-1));
            }
        }
        else if(!this.isWhite() && y == 4) {
            if(MainActivity.enPassantablePeasant.getX() == x + 1) {
                allowedMove.add(new Coordinates(x+1, y+1));
            }
            else if(MainActivity.enPassantablePeasant.getX() == x - 1) {
                allowedMove.add(new Coordinates(x-1, y+1));
            }
        }
        return allowedMove;
    }
}
