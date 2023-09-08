package com.example.chess.Pieces;

import com.example.chess.Coordinates;
import com.example.chess.Position;

import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(boolean white) {
        super(white);
    }

    @Override
    public ArrayList<Coordinates> allowedMoves(Position[][] board, Coordinates coordinates) {

        ArrayList<Coordinates> allowedMoves = new ArrayList<>();
        int x = coordinates.getX();
        int y = coordinates.getY();

        allowedMoves.addAll(moveUp(board, x, y));
        allowedMoves.addAll(eatLeftRightUp(board, x, y));

        return allowedMoves;
    }

    private ArrayList<Coordinates> moveUp(Position[][] board, int x, int y) {
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

    private ArrayList<Coordinates> eatLeftRightUp(Position[][] board, int x, int y) {
        final int BW = this.isWhite() ? -1 : 1;

        ArrayList<Coordinates> allowedMoves = new ArrayList<>();

        // left-up
        if((x != 0 || !this.isWhite()) && (x != 7 || this.isWhite())) {
            if (board[x + BW][y + BW].getPiece() != null && (board[x + BW][y + BW].getPiece().isWhite() != this.isWhite())) {
                allowedMoves.add(new Coordinates(x + BW, y + BW));
            }
        }

        // right-up
        if((x != 0 || this.isWhite()) && (x != 7 || !this.isWhite())) {
            if (board[x - BW][y + BW].getPiece() != null && (board[x - BW][y + BW].getPiece().isWhite() != this.isWhite())) {
                allowedMoves.add(new Coordinates(x - BW, y + BW));
            }
        }

        // TODO: en passant

        return allowedMoves;
    }
}
