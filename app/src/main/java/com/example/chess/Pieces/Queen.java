package com.example.chess.Pieces;

import com.example.chess.Coordinates;
import com.example.chess.Position;

import java.util.ArrayList;

public class Queen extends Piece {

    public Queen(boolean white) {
        super(white);
    }

    @Override
    public ArrayList<Coordinates> allowedMoves(Position[][] board, Coordinates piecePosition) {

        ArrayList<Coordinates> allowedMoves = new ArrayList<>();
        allowedMoves.addAll(new Bishop(this.isWhite()).allowedMoves(board, piecePosition));
        allowedMoves.addAll(new Rook(this.isWhite()).allowedMoves(board, piecePosition));

        return allowedMoves;
    }
}
