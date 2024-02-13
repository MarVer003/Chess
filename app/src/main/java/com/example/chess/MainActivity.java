package com.example.chess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chess.Pieces.Bishop;
import com.example.chess.Pieces.King;
import com.example.chess.Pieces.Knight;
import com.example.chess.Pieces.Pawn;
import com.example.chess.Pieces.Piece;
import com.example.chess.Pieces.Queen;
import com.example.chess.Pieces.Rook;

import java.util.ArrayList;

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {

    public static final int CHECK_MATE = 1;
    public static final int STALE_MATE = 2;
    public static final int DRAW_INSUFFICIENT_MATERIAL = 3;

    public static final int QUEEN_SELECETED = 1;
    public static final int ROOK_SELECETED = 2;
    public static final int BISHOP_SELECETED = 3;
    public static final int KNIGHT_SELECETED = 4;

    public static final int STATE_LISTENING = 1;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_CONNECTED = 3;
    public static final int STATE_CONNECTION_FAILED = 4;
    public static final int STATE_MESSAGE_RECEIVED = 5;
    //
    public static final int MESSAGE_READ = 10;
    public static final int MESSAGE_WRITE = 11;
    public static final int MESSAGE_TOAST = 12;


    Button B_newGame;
    Button B_closeResultCard;

    ConstraintLayout endingCardLayout;
    TextView TW_winDrawLost;
    TextView TW_resultWhy;

    TextView TW_timer;

    LinearLayout pawnPromotionLayout;
    ImageView IB_queen;
    ImageView IB_rook;
    ImageView IB_bishop;
    ImageView IB_knight;

    TextView[][] movesBoard = new TextView[8][8];
    TextView[][] backgroundBoard = new TextView[8][8];
    TextView[][] displayBoard = new TextView[8][8];
    static Position[][] board = new Position[8][8];
    Coordinates clickedPos = new Coordinates();
    Coordinates selectedPiecePos;
    Coordinates pawnPromotingPos;
    Coordinates pawnPromotingPosBefore;

    ArrayList<Coordinates> allowedMoves = new ArrayList<>();


    MediaPlayer mp_move;
    MediaPlayer mp_eat;
    MediaPlayer mp_gameOver;

    BluetoothService bluetoothService;
//    BluetoothService.ConnectedThread connectedThread;

    public static boolean bluetoothGame;
    public static boolean server;

    public static boolean gamePause = false;
    public static boolean kingInDanger = false;
    public static boolean whiteTurn;
    public static Coordinates enPassantablePeasant = null;
    public static int promotingPieceSelected = 0;

    static King w_king;
    static King b_king;

    Queen w_queen;
    Queen b_queen;

    Rook w_rook1;
    Rook w_rook2;
    Rook b_rook1;
    Rook b_rook2;

    Knight w_knight1;
    Knight w_knight2;
    Knight b_knight1;
    Knight b_knight2;

    Bishop w_bishop1;
    Bishop w_bishop2;
    Bishop b_bishop1;
    Bishop b_bishop2;

    Pawn w_pawn1;
    Pawn w_pawn2;
    Pawn w_pawn3;
    Pawn w_pawn4;
    Pawn w_pawn5;
    Pawn w_pawn6;
    Pawn w_pawn7;
    Pawn w_pawn8;

    Pawn b_pawn1;
    Pawn b_pawn2;
    Pawn b_pawn3;
    Pawn b_pawn4;
    Pawn b_pawn5;
    Pawn b_pawn6;
    Pawn b_pawn7;
    Pawn b_pawn8;

    public int dpToPx(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        afterActivityOpenLogic(getIntent());
        findViewByIds();
        setupGrid();
        initializeBoard();
        onClickListeners();
        setupSounds();

    }

    private void afterActivityOpenLogic(Intent intent) {
        bluetoothGame = intent.getBooleanExtra("Bluetooth", false);

        if(bluetoothGame) {
            bluetoothService = new BluetoothService(this, handler);
            server = intent.getBooleanExtra("Server", false);

            if(server) {
                bluetoothService.startServer();
            }
            else {
                BluetoothDevice device = intent.getParcelableExtra("Bluetooth Device");
                if(device != null)
                    bluetoothService.connect(device);
            }

        }
    }

    private void setupGrid() {

        int pixelsWidth = getResources().getDisplayMetrics().widthPixels;
        int pixelsSquare = pixelsWidth / 8;
        // TODO: fill any screen with chessboard by width
        // You'll need to remove all xml squares and make them here in java, that goes for both Sxx and S0xx.
        // You can use View.generateViewId() and then you can use SparseArray to store them or maybe HashMap.
        // You'll also need to create another clickListener, because that onClick method won't work anymore.

        GridLayout gridLayout = findViewById(R.id.gridLayer3);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                movesBoard[i][j] = new TextView(this);
                movesBoard[i][j].setElevation(2);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = pixelsSquare;
                params.height = pixelsSquare;
                params.columnSpec = GridLayout.spec(i);
                params.rowSpec = GridLayout.spec(j);

                movesBoard[i][j].setLayoutParams(params);
                displayBoard[i][j].setLayoutParams(params);
                backgroundBoard[i][j].setLayoutParams(params);
                gridLayout.addView(movesBoard[i][j]);
            }
        }

    }

    private void setupSounds() {
        mp_move = MediaPlayer.create(this, R.raw.chess_moved_sound);
        mp_eat = MediaPlayer.create(this, R.raw.chess_eat_sound);
        mp_gameOver = MediaPlayer.create(this, R.raw.game_over_sound);
    }

    @SuppressLint("DiscouragedApi")
    private void findViewByIds() {

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int viewId = getResources().getIdentifier("S" + x + y, "id", getPackageName());
                displayBoard[x][y] = findViewById(viewId);

                int backgroundViewId = getResources().getIdentifier("S0" + x + y, "id", getPackageName());
                backgroundBoard[x][y] = findViewById(backgroundViewId);

                if(viewId == 0 || backgroundViewId == 0)
                    Toast.makeText(this, "Something is not right", Toast.LENGTH_SHORT).show();
            }
        }

        B_newGame = findViewById(R.id.B_newGame);
        B_closeResultCard = findViewById(R.id.B_closeResultCard);

        pawnPromotionLayout = findViewById(R.id.pawnPromotionLayout);
        IB_queen = findViewById(R.id.IB_queen);
        IB_rook = findViewById(R.id.IB_rook);
        IB_bishop = findViewById(R.id.IB_bishop);
        IB_knight = findViewById(R.id.IB_knight);

        endingCardLayout = findViewById(R.id.endingCardLayout);
        TW_winDrawLost = findViewById(R.id.TW_winDrawLost);
        TW_resultWhy = findViewById(R.id.TW_resultWhy);

        TW_timer = findViewById(R.id.TW_timer);
    }

    private void initializeBoard() {

        if(bluetoothGame) {
            B_newGame.setEnabled(false);
            gamePause = !server;
        }
        else {
        gamePause = false;
        }

        endingCardLayout.setVisibility(View.GONE);
        whiteTurn = true;
        kingInDanger = false;
        pawnPromotionLayout.setVisibility(View.GONE);


        // white
        w_pawn1 = new Pawn(true);
        w_pawn2 = new Pawn(true);
        w_pawn3 = new Pawn(true);
        w_pawn4 = new Pawn(true);
        w_pawn5 = new Pawn(true);
        w_pawn6 = new Pawn(true);
        w_pawn7 = new Pawn(true);
        w_pawn8 = new Pawn(true);

        w_rook1 = new Rook(true);
        w_rook2 = new Rook(true);

        w_knight1 = new Knight(true);
        w_knight2 = new Knight(true);

        w_bishop1 = new Bishop(true);
        w_bishop2 = new Bishop(true);

        w_king = new King(true, new Coordinates(4, 7));
        w_queen = new Queen(true);
        //

        // black
        b_pawn1 = new Pawn(false);
        b_pawn2 = new Pawn(false);
        b_pawn3 = new Pawn(false);
        b_pawn4 = new Pawn(false);
        b_pawn5 = new Pawn(false);
        b_pawn6 = new Pawn(false);
        b_pawn7 = new Pawn(false);
        b_pawn8 = new Pawn(false);

        b_rook1 = new Rook(false);
        b_rook2 = new Rook(false);

        b_knight1 = new Knight(false);
        b_knight2 = new Knight(false);

        b_bishop1 = new Bishop(false);
        b_bishop2 = new Bishop(false);

        b_king = new King(false, new Coordinates(4, 0));
        b_queen = new Queen(false);
        //

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                board[i][j] = new Position(null);
            }
        }

        // black
        board[0][0].setPiece(b_rook1);
        board[1][0].setPiece(b_knight1);
        board[2][0].setPiece(b_bishop1);
        board[3][0].setPiece(b_queen);
        board[4][0].setPiece(b_king);
        board[5][0].setPiece(b_bishop2);
        board[6][0].setPiece(b_knight2);
        board[7][0].setPiece(b_rook2);
        board[0][0].setPiece(b_rook1);
        board[0][1].setPiece(b_pawn1);
        board[1][1].setPiece(b_pawn2);
        board[2][1].setPiece(b_pawn3);
        board[3][1].setPiece(b_pawn4);
        board[4][1].setPiece(b_pawn5);
        board[5][1].setPiece(b_pawn6);
        board[6][1].setPiece(b_pawn7);
        board[7][1].setPiece(b_pawn8);
        //

        // white
        board[0][7].setPiece(w_rook1);
        board[1][7].setPiece(w_knight1);
        board[2][7].setPiece(w_bishop1);
        board[3][7].setPiece(w_queen);
        board[4][7].setPiece(w_king);
        board[5][7].setPiece(w_bishop2);
        board[6][7].setPiece(w_knight2);
        board[7][7].setPiece(w_rook2);
        board[0][7].setPiece(w_rook1);
        board[0][6].setPiece(w_pawn1);
        board[1][6].setPiece(w_pawn2);
        board[2][6].setPiece(w_pawn3);
        board[3][6].setPiece(w_pawn4);
        board[4][6].setPiece(w_pawn5);
        board[5][6].setPiece(w_pawn6);
        board[6][6].setPiece(w_pawn7);
        board[7][6].setPiece(w_pawn8);
        //

        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece tmp = board[i][j].getPiece();
                displayBoard[i][j].setBackgroundResource(pieceDisplay(tmp));
                returnColor(i, j);
            }
        }
    }

    private void onClickListeners() {

        B_newGame.setOnClickListener(v -> initializeBoard());

        B_closeResultCard.setOnClickListener(v -> endingCardLayout.setVisibility(View.GONE));

    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {

        if(gamePause) return;

        clickedPos = new Coordinates();
        switch (view.getId()) {
            case R.id.S00:
                clickedPos.setX(0);
                clickedPos.setY(0);
                break;
            case R.id.S10:
                clickedPos.setX(1);
                clickedPos.setY(0);
                break;
            case R.id.S20:
                clickedPos.setX(2);
                clickedPos.setY(0);
                break;
            case R.id.S30:
                clickedPos.setX(3);
                clickedPos.setY(0);
                break;
            case R.id.S40:
                clickedPos.setX(4);
                clickedPos.setY(0);
                break;
            case R.id.S50:
                clickedPos.setX(5);
                clickedPos.setY(0);
                break;
            case R.id.S60:
                clickedPos.setX(6);
                clickedPos.setY(0);
                break;
            case R.id.S70:
                clickedPos.setX(7);
                clickedPos.setY(0);
                break;
            case R.id.S01:
                clickedPos.setX(0);
                clickedPos.setY(1);
                break;
            case R.id.S11:
                clickedPos.setX(1);
                clickedPos.setY(1);
                break;
            case R.id.S21:
                clickedPos.setX(2);
                clickedPos.setY(1);
                break;
            case R.id.S31:
                clickedPos.setX(3);
                clickedPos.setY(1);
                break;
            case R.id.S41:
                clickedPos.setX(4);
                clickedPos.setY(1);
                break;
            case R.id.S51:
                clickedPos.setX(5);
                clickedPos.setY(1);
                break;
            case R.id.S61:
                clickedPos.setX(6);
                clickedPos.setY(1);
                break;
            case R.id.S71:
                clickedPos.setX(7);
                clickedPos.setY(1);
                break;
            case R.id.S02:
                clickedPos.setX(0);
                clickedPos.setY(2);
                break;
            case R.id.S12:
                clickedPos.setX(1);
                clickedPos.setY(2);
                break;
            case R.id.S22:
                clickedPos.setX(2);
                clickedPos.setY(2);
                break;
            case R.id.S32:
                clickedPos.setX(3);
                clickedPos.setY(2);
                break;
            case R.id.S42:
                clickedPos.setX(4);
                clickedPos.setY(2);
                break;
            case R.id.S52:
                clickedPos.setX(5);
                clickedPos.setY(2);
                break;
            case R.id.S62:
                clickedPos.setX(6);
                clickedPos.setY(2);
                break;
            case R.id.S72:
                clickedPos.setX(7);
                clickedPos.setY(2);
                break;
            case R.id.S03:
                clickedPos.setX(0);
                clickedPos.setY(3);
                break;
            case R.id.S13:
                clickedPos.setX(1);
                clickedPos.setY(3);
                break;
            case R.id.S23:
                clickedPos.setX(2);
                clickedPos.setY(3);
                break;
            case R.id.S33:
                clickedPos.setX(3);
                clickedPos.setY(3);
                break;
            case R.id.S43:
                clickedPos.setX(4);
                clickedPos.setY(3);
                break;
            case R.id.S53:
                clickedPos.setX(5);
                clickedPos.setY(3);
                break;
            case R.id.S63:
                clickedPos.setX(6);
                clickedPos.setY(3);
                break;
            case R.id.S73:
                clickedPos.setX(7);
                clickedPos.setY(3);
                break;
            case R.id.S04:
                clickedPos.setX(0);
                clickedPos.setY(4);
                break;
            case R.id.S14:
                clickedPos.setX(1);
                clickedPos.setY(4);
                break;
            case R.id.S24:
                clickedPos.setX(2);
                clickedPos.setY(4);
                break;
            case R.id.S34:
                clickedPos.setX(3);
                clickedPos.setY(4);
                break;
            case R.id.S44:
                clickedPos.setX(4);
                clickedPos.setY(4);
                break;
            case R.id.S54:
                clickedPos.setX(5);
                clickedPos.setY(4);
                break;
            case R.id.S64:
                clickedPos.setX(6);
                clickedPos.setY(4);
                break;
            case R.id.S74:
                clickedPos.setX(7);
                clickedPos.setY(4);
                break;
            case R.id.S05:
                clickedPos.setX(0);
                clickedPos.setY(5);
                break;
            case R.id.S15:
                clickedPos.setX(1);
                clickedPos.setY(5);
                break;
            case R.id.S25:
                clickedPos.setX(2);
                clickedPos.setY(5);
                break;
            case R.id.S35:
                clickedPos.setX(3);
                clickedPos.setY(5);
                break;
            case R.id.S45:
                clickedPos.setX(4);
                clickedPos.setY(5);
                break;
            case R.id.S55:
                clickedPos.setX(5);
                clickedPos.setY(5);
                break;
            case R.id.S65:
                clickedPos.setX(6);
                clickedPos.setY(5);
                break;
            case R.id.S75:
                clickedPos.setX(7);
                clickedPos.setY(5);
                break;
            case R.id.S06:
                clickedPos.setX(0);
                clickedPos.setY(6);
                break;
            case R.id.S16:
                clickedPos.setX(1);
                clickedPos.setY(6);
                break;
            case R.id.S26:
                clickedPos.setX(2);
                clickedPos.setY(6);
                break;
            case R.id.S36:
                clickedPos.setX(3);
                clickedPos.setY(6);
                break;
            case R.id.S46:
                clickedPos.setX(4);
                clickedPos.setY(6);
                break;
            case R.id.S56:
                clickedPos.setX(5);
                clickedPos.setY(6);
                break;
            case R.id.S66:
                clickedPos.setX(6);
                clickedPos.setY(6);
                break;
            case R.id.S76:
                clickedPos.setX(7);
                clickedPos.setY(6);
                break;
            case R.id.S07:
                clickedPos.setX(0);
                clickedPos.setY(7);
                break;
            case R.id.S17:
                clickedPos.setX(1);
                clickedPos.setY(7);
                break;
            case R.id.S27:
                clickedPos.setX(2);
                clickedPos.setY(7);
                break;
            case R.id.S37:
                clickedPos.setX(3);
                clickedPos.setY(7);
                break;
            case R.id.S47:
                clickedPos.setX(4);
                clickedPos.setY(7);
                break;
            case R.id.S57:
                clickedPos.setX(5);
                clickedPos.setY(7);
                break;
            case R.id.S67:
                clickedPos.setX(6);
                clickedPos.setY(7);
                break;
            case R.id.S77:
                clickedPos.setX(7);
                clickedPos.setY(7);
                break;
        }

        int x = clickedPos.getX();
        int y = clickedPos.getY();
        Position pressedSquare = board[x][y];

        if(pressedSquare.getPiece() != null && (whiteTurn == pressedSquare.getPiece().isWhite())) {

            for (Coordinates move : allowedMoves) {
                movesBoard[move.getX()][move.getY()].setBackgroundResource(0);
            }

            if(selectedPiecePos != null)
                returnColor(selectedPiecePos.getX(), selectedPiecePos.getY());
            selectedPiecePos = clickedPos;

            if(kingInDanger && board[x][y].getPiece() instanceof King) {
                backgroundBoard[x][y].setBackgroundColor(Color.RED);
            }
            else {
                if (isSquareWhite(x, y))
                    backgroundBoard[x][y].setBackgroundResource(R.color.black_selected_square);
                else
                    backgroundBoard[x][y].setBackgroundResource(R.color.white_selected_square);
            }

            allowedMoves = checkAllowedMoves(x, y, true);

        } else if(selectedPiecePos != null) {

            for (Coordinates move : allowedMoves) {
                movesBoard[move.getX()][move.getY()].setBackgroundResource(0);
            }

            if(movePiece(selectedPiecePos, clickedPos)) {

                if(bluetoothGame && pawnPromotingPos == null)
                    bluetoothService.sendMove((byte) selectedPiecePos.getX(),
                            (byte) selectedPiecePos.getY(),
                            (byte) clickedPos.getX(),
                            (byte) clickedPos.getY(),
                            (byte) -1,
                            (byte) -1);

                if (!gamePause) {
                    afterMoveLogic();
                    if(bluetoothGame)
                        gamePause = true;
                }
            }

            returnColor(selectedPiecePos.getX(), selectedPiecePos.getY());
            selectedPiecePos = null;
        }
    }

    private boolean movePiece(Coordinates beforePos, Coordinates afterPos) {
        Piece tmpPiece = board[beforePos.getX()][beforePos.getY()].getPiece();

        allowedMoves = checkAllowedMoves(beforePos.getX(), beforePos.getY(), false);

        for (Coordinates move : allowedMoves) {

            if (move.getX() == afterPos.getX() && move.getY() == afterPos.getY()) {

                kingInDanger = false;

                if(board[afterPos.getX()][afterPos.getY()].getPiece() != null) {
                    mp_eat.start();
                }
                else {
                    mp_move.start();
                }

                board[beforePos.getX()][beforePos.getY()].setPiece(null);
                displayBoard[beforePos.getX()][beforePos.getY()].setBackgroundResource(0);

                board[afterPos.getX()][afterPos.getY()].setPiece(tmpPiece);
                displayBoard[afterPos.getX()][afterPos.getY()].setBackgroundResource(pieceDisplay(tmpPiece));


                if(enPassantablePeasant != null &&
                        tmpPiece instanceof Pawn &&
                        enPassantablePeasant.getX() == afterPos.getX()) {
                    if((whiteTurn && enPassantablePeasant.getY() == afterPos.getY() + 1) ||
                            (!whiteTurn && enPassantablePeasant.getY() == afterPos.getY() - 1)) {
                        board[enPassantablePeasant.getX()][enPassantablePeasant.getY()].setPiece(null);
                        displayBoard[enPassantablePeasant.getX()][enPassantablePeasant.getY()].setBackgroundResource(0);
                    }
                }
                enPassantablePeasant = null;

                if(tmpPiece instanceof Rook)
                    ((Rook) tmpPiece).setCastleable(false);
                else if(tmpPiece instanceof King) {
                    ((King) tmpPiece).setCastleable(false);
                    if(Math.abs(afterPos.getX() - beforePos.getX()) == 2) {
                        if(afterPos.getX() == 2) {
                            board[0][beforePos.getY()].setPiece(null);
                            displayBoard[0][beforePos.getY()].setBackgroundResource(0);
                            if (whiteTurn) {
                                board[3][beforePos.getY()].setPiece(w_rook1);
                                displayBoard[3][beforePos.getY()].setBackgroundResource(R.drawable.w_rook);
                            }
                            else {
                                board[3][beforePos.getY()].setPiece(b_rook1);
                                displayBoard[3][beforePos.getY()].setBackgroundResource(R.drawable.b_rook2);
                            }
                        }
                        else {
                            board[7][beforePos.getY()].setPiece(null);
                            displayBoard[7][beforePos.getY()].setBackgroundResource(0);
                            if (whiteTurn) {
                                board[5][beforePos.getY()].setPiece(w_rook2);
                                displayBoard[5][beforePos.getY()].setBackgroundResource(R.drawable.w_rook);
                            }
                            else {
                                board[5][beforePos.getY()].setPiece(b_rook2);
                                displayBoard[5][beforePos.getY()].setBackgroundResource(R.drawable.b_rook2);
                            }
                        }
                    }
                }

                returnColor(w_king.getKingPosition().getX(), w_king.getKingPosition().getY());
                returnColor(b_king.getKingPosition().getX(), b_king.getKingPosition().getY());

                if(tmpPiece instanceof King) {
                    if(tmpPiece.isWhite()) {
                        w_king.setKingPosition(afterPos);
                    }
                    else {
                        b_king.setKingPosition(afterPos);
                    }
                }

                if(tmpPiece instanceof Pawn) {
                    if (Math.abs(beforePos.getY() - afterPos.getY()) == 2)
                        enPassantablePeasant = afterPos;
                    else if(afterPos.getY() == 0 || afterPos.getY() == 7) {
                        pawnPromotingPos = new Coordinates(afterPos.getX(), afterPos.getY());
                        pawnPromotingPosBefore = new Coordinates(beforePos.getX(), beforePos.getY());
                        changePromotionPiecesColor();
                        pawnPromotionLayout.setVisibility(View.VISIBLE);
                        gamePause = true;
                    }
                }

//                B_undo.setEnabled(true);

                return true;
            }
        }
        return false;
    }

    private void afterMoveLogic() {

        whiteTurn = !whiteTurn;

        Coordinates kingCord;
        if (whiteTurn) {
            kingCord = w_king.getKingPosition();
        } else {
            kingCord = b_king.getKingPosition();
        }
        int kingCordX = kingCord.getX();
        int kingCordY = kingCord.getY();
        if (isKingInDanger(kingCord)) {
            backgroundBoard[kingCordX][kingCordY].setBackgroundColor(Color.RED);
            kingInDanger = true;
        }

        int gameStatus = gameStatus();
        if(gameStatus != 0) {
            mp_gameOver.start();
            B_newGame.setEnabled(true);
        }
        if (gameStatus == CHECK_MATE) {
            endingCardLayout.setVisibility(View.VISIBLE);
                TW_resultWhy.setText("By checkmate");
            if(whiteTurn) {
                TW_winDrawLost.setText("BLACK WON!");
            }
            else {
                TW_winDrawLost.setText("WHITE WON!");
            }
            gamePause = true;
        }
        else if (gameStatus == STALE_MATE) {
            endingCardLayout.setVisibility(View.VISIBLE);

            TW_resultWhy.setText("By stalemate");
                TW_winDrawLost.setText("DRAW!");
            gamePause = true;
        }
        else if (gameStatus == DRAW_INSUFFICIENT_MATERIAL) {
            endingCardLayout.setVisibility(View.VISIBLE);

            TW_resultWhy.setText("By insufficient material");
            TW_winDrawLost.setText("DRAW!");
            gamePause = true;
        }
    }

    private int gameStatus() {

        boolean possibleMoves = false;
        int wPawns = 0;
        int wBishops = 0;
        int wKnights = 0;
        int wRooks = 0;
        int wQueen = 0;
        int wWBishop = 0;
        int wBBishop = 0;

        int bPawns = 0;
        int bBishops = 0;
        int bKnights = 0;
        int bRooks = 0;
        int bQueen = 0;
        int bWBishop = 0;
        int bBBishop = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece tmpPiece = board[i][j].getPiece();
                boolean pieceWhite = false;
                if(tmpPiece != null)
                    pieceWhite  = board[i][j].getPiece().isWhite();

                if(tmpPiece != null && pieceWhite == whiteTurn) {
                    if (possibleMoves || checkAllowedMoves(i, j, false).size() != 0)
                        possibleMoves = true;

                    if(tmpPiece instanceof Pawn)    wPawns++;
                    if(tmpPiece instanceof Bishop) {
                        wBishops++;
                        if(isSquareWhite(i, j)) wWBishop++;
                        else wBBishop++;
                    }
                    if(tmpPiece instanceof Knight)  wKnights++;
                    if(tmpPiece instanceof Rook)    wRooks++;
                    if(tmpPiece instanceof Queen)   wQueen++;
                }
                else if(tmpPiece != null) {

                    if(tmpPiece instanceof Pawn)    bPawns++;
                    if(tmpPiece instanceof Bishop) {
                        bBishops++;
                        if(isSquareWhite(i, j)) bWBishop++;
                        else bBBishop++;
                    }
                    if(tmpPiece instanceof Knight)  bKnights++;
                    if(tmpPiece instanceof Rook)    bRooks++;
                    if(tmpPiece instanceof Queen)   bQueen++;
                }

            }
        }

        boolean drawCondition =

                possibleMoves &&
                        wPawns == 0 && bPawns == 0 &&
                        wQueen == 0 && bQueen == 0 &&
                        wRooks == 0 && bRooks == 0 &&
                        ((wKnights == 1 && bKnights == 0 && wBishops == 0 && bBishops == 0) ||
                        (wKnights == 0 && bKnights == 1 && wBishops == 0 && bBishops == 0) ||
                        (wKnights == 0 && bKnights == 0 && wBishops == 1 && bBishops == 0) ||
                        (wKnights == 0 && bKnights == 0 && wBishops == 0 && bBishops == 1) ||
                        (wKnights == 0 && bKnights == 0 && wBishops == 0 && bBishops == 0) ||
                        (wKnights == 0 && bKnights == 0 && wWBishop == 1 && wBBishop == 0 && bWBishop == 1 && bBBishop == 0) ||
                        (wKnights == 0 && bKnights == 0 && wWBishop == 0 && wBBishop == 1 && bWBishop == 0 && bBBishop == 1));


        if(possibleMoves) {
            if(drawCondition)
                return DRAW_INSUFFICIENT_MATERIAL;
            return 0;
        }

        if(kingInDanger)
            return CHECK_MATE;
        else
            return STALE_MATE;
    }

    private ArrayList<Coordinates> checkAllowedMoves(int x, int y, boolean visualFeedback) {

        ArrayList<Coordinates> tmpAllowedMoves = new ArrayList<>();

        Position pressedSquare = board[x][y];
        Coordinates position = new Coordinates(x, y);

        allowedMoves = pressedSquare.getPiece().allowedMoves(board, position);

        for (Coordinates move : allowedMoves) {
            Coordinates kingPos = whiteTurn ? w_king.getKingPosition() : b_king.getKingPosition();
            Piece tmpPiece = pressedSquare.getPiece();
            Piece tmpPiece2 = board[move.getX()][move.getY()].getPiece();
            board[x][y].setPiece(null);
            board[move.getX()][move.getY()].setPiece(tmpPiece);
            if(!(tmpPiece instanceof King) && !isKingInDanger(kingPos)) {
                tmpAllowedMoves.add(move);
                if(visualFeedback)
                    movesBoard[move.getX()][move.getY()].setBackgroundResource(R.drawable.possible_move2);
            }
            else if(tmpPiece instanceof King && !isKingInDanger(move)) {
                tmpAllowedMoves.add(move);
                if(visualFeedback)
                    movesBoard[move.getX()][move.getY()].setBackgroundResource(R.drawable.possible_move2);

            }
            board[x][y].setPiece(tmpPiece);
            board[move.getX()][move.getY()].setPiece(tmpPiece2);
        }
        return tmpAllowedMoves;
    }


    private void changePromotionPiecesColor() {

        int q, r, b, k;

        if(whiteTurn) {
            q = R.drawable.w_queen;
            r = R.drawable.w_rook;
            b = R.drawable.w_bishop;
            k = R.drawable.w_knight;
        }
        else {
            q = R.drawable.b_queen;
            r = R.drawable.b_rook2;
            b = R.drawable.b_bishop;
            k = R.drawable.b_knight;
        }
        IB_queen.setBackgroundResource(q);
        IB_rook.setBackgroundResource(r);
        IB_bishop.setBackgroundResource(b);
        IB_knight.setBackgroundResource(k);

    }

    private void promote(Coordinates pos, int pieceInt) {

        Piece piece;
        if(pieceInt == QUEEN_SELECETED) {
            piece = new Queen(whiteTurn);
        }
        else if(pieceInt == ROOK_SELECETED) {
            piece = new Rook(whiteTurn);
        }
        else if(pieceInt == BISHOP_SELECETED) {
            piece = new Bishop(whiteTurn);
        }
        else if(pieceInt == KNIGHT_SELECETED){
            piece = new Knight(whiteTurn);
        }
        else {
            piece = null;
        }

        board[pos.getX()][pos.getY()].setPiece(piece);
        displayBoard[pos.getX()][pos.getY()].setBackgroundResource(pieceDisplay(piece));

    }

    private void pawnPromotion(Coordinates pos) {

        if (promotingPieceSelected != 0) {
            promote(pos, promotingPieceSelected);
            pawnPromotionLayout.setVisibility(View.GONE);

            if(bluetoothGame) {
                bluetoothService.sendMove((byte) pawnPromotingPosBefore.getX(),
                        (byte) pawnPromotingPosBefore.getY(),
                        (byte) pos.getX(),
                        (byte) pos.getY(),
                        (byte) 1,
                        (byte) promotingPieceSelected);
                gamePause = true;
                afterMoveLogic();
            }
        }
    }

    private void returnColor(int x, int y) {

        if(kingInDanger && board[x][y].getPiece() instanceof King) {
            backgroundBoard[x][y].setBackgroundColor(Color.RED);
        }
        else {
            if (isSquareWhite(x, y))
                backgroundBoard[x][y].setBackgroundResource(R.color.black_square);
            else
                backgroundBoard[x][y].setBackgroundResource(R.color.white_square);
        }
    }

    private boolean isSquareWhite(int x, int y) {
        if(x % 2 == 0) {
            return y % 2 != 0;
        }
        else {
            return y % 2 == 0;
        }
    }

    private int pieceDisplay(Piece piece) {

        if(piece instanceof Pawn) {
            if(piece.isWhite()) return R.drawable.w_pawn;
            else return R.drawable.b_pawn2;
        }
        if(piece instanceof Rook) {
            if(piece.isWhite()) return R.drawable.w_rook;
            else return R.drawable.b_rook2;
        }
        if(piece instanceof Knight) {
            if(piece.isWhite()) return R.drawable.w_knight;
            else return R.drawable.b_knight;
        }
        if(piece instanceof Bishop) {
            if(piece.isWhite()) return R.drawable.w_bishop;
            else return R.drawable.b_bishop;
        }
        if(piece instanceof King) {
            if(piece.isWhite()) return R.drawable.w_king;
            else return R.drawable.b_king;
        }
        if(piece instanceof Queen) {
            if(piece.isWhite()) return R.drawable.w_queen;
            else return R.drawable.b_queen;
        }
        return 0;
    }

    public static boolean isKingInDanger(Coordinates kingPosition) {

//        Coordinates kingPosition = whiteTurn ? w_king.getKingPosition() : b_king.getKingPosition();

        ArrayList<Coordinates> allPossibleChecksQueen =
                new ArrayList<>((new Queen(whiteTurn).allowedMoves(board, kingPosition)));

        ArrayList<Coordinates> allPossibleChecksKnight =
                new ArrayList<>(new Knight(whiteTurn).allowedMoves(board, kingPosition));

        ArrayList<Coordinates> allPossibleChecksPawn =
                new ArrayList<>(new Pawn(whiteTurn).allowedMoves(board, kingPosition));

        ArrayList<Coordinates> allPossibleChecksKing =
                new ArrayList<>(new King(whiteTurn).allowedMoves(board, kingPosition));



        for (Coordinates move : allPossibleChecksQueen) {
            if(board[move.getX()][move.getY()].getPiece() != null &&
                    board[move.getX()][move.getY()].getPiece().isWhite() != whiteTurn) {
                if(board[move.getX()][move.getY()].getPiece() instanceof Queen) {
                    return true;
                }
                else if(board[move.getX()][move.getY()].getPiece() instanceof Rook &&
                        (move.getX() == kingPosition.getX() || move.getY() == kingPosition.getY())) {
                    return true;
                }
                else if (board[move.getX()][move.getY()].getPiece() instanceof Bishop &&
                        (move.getX() != kingPosition.getX() && move.getY() != kingPosition.getY())) {
                    return true;
                }
            }
        }

        for (Coordinates move : allPossibleChecksKnight) {
            if(board[move.getX()][move.getY()].getPiece() != null &&
                    board[move.getX()][move.getY()].getPiece().isWhite() != whiteTurn &&
                    board[move.getX()][move.getY()].getPiece() instanceof Knight) {
                return true;
            }
        }

        for (Coordinates move : allPossibleChecksPawn) {
            if(board[move.getX()][move.getY()].getPiece() != null &&
                    board[move.getX()][move.getY()].getPiece().isWhite() != whiteTurn &&
                    board[move.getX()][move.getY()].getPiece() instanceof Pawn) {
                return true;
            }
        }

        for (Coordinates move : allPossibleChecksKing) {
            if(board[move.getX()][move.getY()].getPiece() != null &&
                    board[move.getX()][move.getY()].getPiece().isWhite() != whiteTurn &&
                    board[move.getX()][move.getY()].getPiece() instanceof King) {
                return true;
            }
        }

        return false;
    }

    public void onPromotionClick(View view) {
        if(view.getId() == R.id.IB_queen) {
            promotingPieceSelected = QUEEN_SELECETED;
        }
        else if(view.getId() == R.id.IB_rook) {
            promotingPieceSelected = ROOK_SELECETED;
        }
        else if(view.getId() == R.id.IB_bishop) {
            promotingPieceSelected = BISHOP_SELECETED;
        }
        else {
            promotingPieceSelected = KNIGHT_SELECETED;
        }

        pawnPromotion(pawnPromotingPos);
        if(!bluetoothGame)
            gamePause = false;
        pawnPromotingPos = null;
        if(!bluetoothGame)
            afterMoveLogic();

    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            switch (msg.what)
            {
                case STATE_LISTENING:
                    Toast.makeText(MainActivity.this, "listening...", Toast.LENGTH_SHORT).show();
                    break;
                case STATE_CONNECTING:
                    Toast.makeText(MainActivity.this, "connecting...", Toast.LENGTH_SHORT).show();
                    break;
                case STATE_CONNECTED:
                    Toast.makeText(MainActivity.this, "connected.", Toast.LENGTH_SHORT).show();
                    if(server)
                        gamePause = false;
                    break;
                case STATE_CONNECTION_FAILED:
                    Toast.makeText(MainActivity.this, "connection failed.", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case STATE_MESSAGE_RECEIVED:
                    Toast.makeText(MainActivity.this, "message received.", Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_READ:
                    gamePause = false;
                    byte[] buffer = (byte[]) msg.obj;
                    int fromX = buffer[0];
                    int fromY = buffer[1];
                    int toX = buffer[2];
                    int toY = buffer[3];
                    int pawnPromotion = buffer[4];
                    int whatPiece = buffer[5];
                    if(pawnPromotion == -1) {
                        movePiece(new Coordinates(fromX, fromY), new Coordinates(toX, toY));
                    }
                    else {
                        removePiece(new Coordinates(fromX, fromY));
                        promote(new Coordinates(toX, toY), whatPiece);
                    }
                    afterMoveLogic();
                    break;
                case MESSAGE_WRITE:
                    //TODO: Maybe feature for the future...
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(MainActivity.this, msg.getData().toString(), Toast.LENGTH_LONG).show();
                    break;
            }

            return true;
        }
    });

    private void removePiece(Coordinates pos) {

        board[pos.getX()][pos.getY()].setPiece(null);
        displayBoard[pos.getX()][pos.getY()].setBackgroundResource(0);

    }
}