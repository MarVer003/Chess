package com.example.chess;

import androidx.annotation.NonNull;

import com.example.chess.Pieces.Piece;

public class MoveInfo {
    private Piece pieceBefore;
    private Piece pieceAfter;
    private Piece eatenPiece;
    private Coordinates positionBefore;
    private Coordinates positionAfter;
    private boolean rightCastle;
    private boolean leftCastle;
    private boolean enPassant;
    // TODO: implement these 3 possibilities in the code

    public MoveInfo(Piece pieceBefore, Piece pieceAfter, Piece eatenPiece, Coordinates positionBefore, Coordinates positionAfter) {
        this.pieceBefore = pieceBefore;
        this.pieceAfter = pieceAfter;
        this.eatenPiece = eatenPiece;
        this.positionBefore = positionBefore;
        this.positionAfter = positionAfter;
        this.rightCastle = false;
        this.leftCastle = false;
        this.enPassant = false;
    }

    public boolean isRightCastle() {
        return rightCastle;
    }

    public void setRightCastle(boolean rightCastle) {
        this.rightCastle = rightCastle;
    }

    public boolean isLeftCastle() {
        return leftCastle;
    }

    public void setLeftCastle(boolean leftCastle) {
        this.leftCastle = leftCastle;
    }

    public boolean isEnPassant() {
        return enPassant;
    }

    public void setEnPassant(boolean enPassant) {
        this.enPassant = enPassant;
    }

    public Piece getPieceBefore() {
        return pieceBefore;
    }

    public void setPieceBefore(Piece pieceBefore) {
        this.pieceBefore = pieceBefore;
    }

    public Piece getPieceAfter() {
        return pieceAfter;
    }

    public void setPieceAfter(Piece pieceAfter) {
        this.pieceAfter = pieceAfter;
    }

    public Piece getEatenPiece() {
        return this.eatenPiece;
    }

    public void setEatenPiece(Piece eatenPiece) {
        this.eatenPiece = eatenPiece;
    }

    public Coordinates getPositionBefore() {
        return positionBefore;
    }

    public void setPositionBefore(Coordinates positionBefore) {
        this.positionBefore = positionBefore;
    }

    public Coordinates getPositionAfter() {
        return positionAfter;
    }

    public void setPositionAfter(Coordinates positionAfter) {
        this.positionAfter = positionAfter;
    }
}
