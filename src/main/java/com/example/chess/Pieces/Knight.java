package com.example.chess.Pieces;

import com.example.chess.Coordinates;
import com.example.chess.Position;

import java.util.ArrayList;

public class Knight extends Piece {

    public Knight(boolean white) {
        super(white);
    }

    @Override
    public ArrayList<Coordinates> allowedMoves(Position[][] board, Coordinates piecePosition) {

        ArrayList<Coordinates> allowedMoves = new ArrayList<>();
        int x = piecePosition.getX();
        int y = piecePosition.getY();
        boolean indexInside;

        indexInside = x < 7 && y > 1;
        if(indexInside && (board[x+1][y-2].getPiece() == null || board[x+1][y-2].getPiece().isWhite() != this.isWhite())) {
            allowedMoves.add(new Coordinates(x+1, y-2));
        }
        indexInside = x < 6 && y > 0;
        if(indexInside && (board[x+2][y-1].getPiece() == null || board[x+2][y-1].getPiece().isWhite() != this.isWhite())) {
            allowedMoves.add(new Coordinates(x+2, y-1));
        }
        indexInside = x < 6 && y < 7;
        if(indexInside && (board[x+2][y+1].getPiece() == null || board[x+2][y+1].getPiece().isWhite() != this.isWhite())) {
            allowedMoves.add(new Coordinates(x+2, y+1));
        }
        indexInside = x < 7 && y < 6;
        if(indexInside && (board[x+1][y+2].getPiece() == null || board[x+1][y+2].getPiece().isWhite() != this.isWhite())) {
            allowedMoves.add(new Coordinates(x+1, y+2));
        }
        indexInside = x > 0 && y < 6;
        if(indexInside && (board[x-1][y+2].getPiece() == null || board[x-1][y+2].getPiece().isWhite() != this.isWhite())) {
            allowedMoves.add(new Coordinates(x-1, y+2));
        }
        indexInside = x > 1 && y < 7;
        if(indexInside && (board[x-2][y+1].getPiece() == null || board[x-2][y+1].getPiece().isWhite() != this.isWhite())) {
            allowedMoves.add(new Coordinates(x-2, y+1));
        }
        indexInside = x > 1 && y > 0;
        if(indexInside && (board[x-2][y-1].getPiece() == null || board[x-2][y-1].getPiece().isWhite() != this.isWhite())) {
            allowedMoves.add(new Coordinates(x-2, y-1));
        }
        indexInside = x > 0 && y > 1;
        if(indexInside && (board[x-1][y-2].getPiece() == null || board[x-1][y-2].getPiece().isWhite() != this.isWhite())) {
            allowedMoves.add(new Coordinates(x-1, y-2));
        }


        return allowedMoves;
    }
}
