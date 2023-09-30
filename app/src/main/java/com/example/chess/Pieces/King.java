package com.example.chess.Pieces;

import com.example.chess.Coordinates;
import com.example.chess.MainActivity;
import com.example.chess.Position;

import java.util.ArrayList;

public class King extends Piece {

    private boolean castleable;
    private Coordinates kingPosition;

    public King(boolean white, Coordinates kingPosition) {
        super(white);
        this.kingPosition = kingPosition;
        this.castleable = true;
    }

    public King(boolean white) {
        super(white);
        this.kingPosition = null;
        this.castleable = false;
    }

    public void setCastleable(boolean castleable) {
        this.castleable = castleable;
    }

    public Coordinates getKingPosition() {
        return kingPosition;
    }

    public void setKingPosition(Coordinates kingPosition) {
        this.kingPosition = kingPosition;
    }

    @Override
    public ArrayList<Coordinates> allowedMoves(Position[][] board, Coordinates piecePosition) {

        ArrayList<Coordinates> allowedMoves = new ArrayList<>();
        int x = piecePosition.getX();
        int y = piecePosition.getY();
        boolean indexInside;

        indexInside = x > 0 && y > 0;
        if(indexInside && (board[x-1][y-1].getPiece() == null || board[x-1][y-1].getPiece().isWhite() != this.isWhite())) {
            allowedMoves.add(new Coordinates(x-1, y-1));
        }
        indexInside = y > 0;
        if(indexInside && (board[x][y-1].getPiece() == null || board[x][y-1].getPiece().isWhite() != this.isWhite())) {
            allowedMoves.add(new Coordinates(x, y-1));
        }
        indexInside = x < 7 && y > 0;
        if(indexInside && (board[x+1][y-1].getPiece() == null || board[x+1][y-1].getPiece().isWhite() != this.isWhite())) {
            allowedMoves.add(new Coordinates(x+1, y-1));
        }
        indexInside = x > 0;
        if(indexInside && (board[x-1][y].getPiece() == null || board[x-1][y].getPiece().isWhite() != this.isWhite())) {
            allowedMoves.add(new Coordinates(x-1, y));
        }
        indexInside = x < 7;
        if(indexInside  && (board[x+1][y].getPiece() == null || board[x+1][y].getPiece().isWhite() != this.isWhite())) {
            allowedMoves.add(new Coordinates(x+1, y));
        }
        indexInside = x > 0 && y < 7;
        if(indexInside && (board[x-1][y+1].getPiece() == null || board[x-1][y+1].getPiece().isWhite() != this.isWhite())) {
            allowedMoves.add(new Coordinates(x-1, y+1));
        }
        indexInside = y < 7;
        if(indexInside && (board[x][y+1].getPiece() == null || board[x][y+1].getPiece().isWhite() != this.isWhite())) {
            allowedMoves.add(new Coordinates(x, y+1));
        }
        indexInside = x < 7 && y < 7;
        if(indexInside && (board[x+1][y+1].getPiece() == null || board[x+1][y+1].getPiece().isWhite() != this.isWhite())) {
            allowedMoves.add(new Coordinates(x+1, y+1));
        }


        if(this.castleable && !MainActivity.kingInDanger && !MainActivity.isKingInDanger(new Coordinates(x-1, y)) &&
                board[0][y].getPiece() instanceof Rook &&
                ((Rook) board[0][y].getPiece()).isCastleable() &&
                board[1][y].getPiece() == null &&
                board[2][y].getPiece() == null &&
                board[3][y].getPiece() == null) {
            allowedMoves.add(new Coordinates(x-2, y));
        }

        if(this.castleable && !MainActivity.kingInDanger && !MainActivity.isKingInDanger(new Coordinates(x+1, y)) &&
                board[7][y].getPiece() instanceof Rook &&
                ((Rook) board[7][y].getPiece()).isCastleable() &&
                board[6][y].getPiece() == null &&
                board[5][y].getPiece() == null) {
            allowedMoves.add(new Coordinates(x+2, y));
        }

        return allowedMoves;
    }
}
