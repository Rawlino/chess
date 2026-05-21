package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import static chess.ChessGame.TeamColor.*;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChessPiece that)) {
            return false;
        }
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    public boolean isInBounds(int row, int col) {
        if (row > 8 || row < 1 || col > 8 || col < 1) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s, %s", pieceColor, type);
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);
        ArrayList<ChessMove> moves = new ArrayList<>();

        if (piece.getPieceType() == PieceType.KING) {
            int[][] kingMoves = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
            kingAndKnightMoves(board, myPosition, kingMoves, piece, moves);
            return moves;
        } else if (piece.getPieceType() == PieceType.QUEEN) {
            straightUp(myPosition, piece, board, moves);
            straightDown(myPosition, piece, board, moves);
            straightLeft(myPosition, piece, board, moves);
            straightRight(myPosition, piece, board, moves);
            straightUpRight(myPosition, piece, board, moves);
            straightDownRight(myPosition, piece, board, moves);
            straightUpLeft(myPosition, piece, board, moves);
            straightDownLeft(myPosition, piece, board, moves);
            return moves;
        } else if (piece.getPieceType() == PieceType.BISHOP) {
            straightUpRight(myPosition, piece, board, moves);
            straightDownRight(myPosition, piece, board, moves);
            straightUpLeft(myPosition, piece, board, moves);
            straightDownLeft(myPosition, piece, board, moves);
            return moves;
        } else if (piece.getPieceType() == PieceType.KNIGHT) {
            int[][] knightMoves = {{2, -1}, {2, 1}, {1, 2}, {1, -2}, {-1, -2}, {-1, 2}, {-2, -1}, {-2, 1}};
            kingAndKnightMoves(board, myPosition, knightMoves, piece, moves);
            return moves;
        } else if (piece.getPieceType() == PieceType.ROOK) {
            straightUp(myPosition, piece, board, moves);
            straightDown(myPosition, piece, board, moves);
            straightLeft(myPosition, piece, board, moves);
            straightRight(myPosition, piece, board, moves);
            return moves;
        } else if (piece.getPieceType() == PieceType.PAWN) {
            int[][] whitePawnMoves = {{1, 0}, {1, 1}, {1, -1}, {2, 0}};
            int[][] blackPawnMoves = {{-1, 0}, {-1, -1}, {-1, 1}, {-2, 0}};
            if (piece.getTeamColor() == WHITE) {
                for (int i = 0; i < 4; i++) {
                    if (!isInBounds(myPosition.getRow() + whitePawnMoves[i][0], myPosition.getColumn() + whitePawnMoves[i][1])) {
                        //pass
                    } else if (i == 3 && myPosition.getRow() == 2) {
                        if (board.getPiece(new ChessPosition(myPosition.getRow() + whitePawnMoves[i][0], myPosition.getColumn() + whitePawnMoves[i][1])) != null) {
                            //pass
                        } else if (board.getPiece(new ChessPosition(myPosition.getRow() + whitePawnMoves[i][0] - 1, myPosition.getColumn() + whitePawnMoves[i][1])) != null) {
                            //pass
                        } else {
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + whitePawnMoves[i][0], myPosition.getColumn() + whitePawnMoves[i][1]), null));
                        }
                    } else if (i == 1 || i == 2) {
                        if (board.getPiece(new ChessPosition(myPosition.getRow() + whitePawnMoves[i][0], myPosition.getColumn() + whitePawnMoves[i][1])) != null) {
                            if ((board.getPiece(new ChessPosition(myPosition.getRow() + whitePawnMoves[i][0], myPosition.getColumn() + whitePawnMoves[i][1])).getTeamColor() == piece.getTeamColor())) {
                                //pass
                            } else if ((board.getPiece(new ChessPosition(myPosition.getRow() + whitePawnMoves[i][0], myPosition.getColumn() + whitePawnMoves[i][1])).getTeamColor() != piece.getTeamColor())) {
                                if (myPosition.getRow() + whitePawnMoves[i][0] == 8) {
                                    moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + whitePawnMoves[i][0], myPosition.getColumn() + whitePawnMoves[i][1]), PieceType.QUEEN));
                                    moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + whitePawnMoves[i][0], myPosition.getColumn() + whitePawnMoves[i][1]), PieceType.BISHOP));
                                    moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + whitePawnMoves[i][0], myPosition.getColumn() + whitePawnMoves[i][1]), PieceType.KNIGHT));
                                    moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + whitePawnMoves[i][0], myPosition.getColumn() + whitePawnMoves[i][1]), PieceType.ROOK));
                                } else {
                                    moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + whitePawnMoves[i][0], myPosition.getColumn() + whitePawnMoves[i][1]), null));
                                }
                            } else {
                                //pass
                            }
                        }
                    } else if (i == 0) {
                        if (board.getPiece(new ChessPosition(myPosition.getRow() + whitePawnMoves[i][0], myPosition.getColumn() + whitePawnMoves[i][1])) != null) {
                            //pass
                        } else {
                            if (myPosition.getRow() + whitePawnMoves[i][0] == 8) {
                                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + whitePawnMoves[i][0], myPosition.getColumn() + whitePawnMoves[i][1]), PieceType.QUEEN));
                                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + whitePawnMoves[i][0], myPosition.getColumn() + whitePawnMoves[i][1]), PieceType.BISHOP));
                                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + whitePawnMoves[i][0], myPosition.getColumn() + whitePawnMoves[i][1]), PieceType.KNIGHT));
                                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + whitePawnMoves[i][0], myPosition.getColumn() + whitePawnMoves[i][1]), PieceType.ROOK));
                            } else {
                                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + whitePawnMoves[i][0], myPosition.getColumn() + whitePawnMoves[i][1]), null));
                            }
                        }
                    }
                }
            } else {
                //Black pawn moves
                for (int i = 0; i < 4; i++) {
                    if (!isInBounds(myPosition.getRow() + blackPawnMoves[i][0], myPosition.getColumn() + blackPawnMoves[i][1])) {
                        //pass
                    } else if (i == 3 && myPosition.getRow() == 7) {
                        if (board.getPiece(new ChessPosition(myPosition.getRow() + blackPawnMoves[i][0], myPosition.getColumn() + blackPawnMoves[i][1])) != null) {
                            //pass
                        } else if (board.getPiece(new ChessPosition(myPosition.getRow() + blackPawnMoves[i][0] + 1, myPosition.getColumn() + blackPawnMoves[i][1])) != null) {
                            //pass
                        } else {
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + blackPawnMoves[i][0], myPosition.getColumn() + blackPawnMoves[i][1]), null));
                        }
                    } else if (i == 1 || i == 2) {
                        if (board.getPiece(new ChessPosition(myPosition.getRow() + blackPawnMoves[i][0], myPosition.getColumn() + blackPawnMoves[i][1])) != null) {
                            if ((board.getPiece(new ChessPosition(myPosition.getRow() + blackPawnMoves[i][0], myPosition.getColumn() + blackPawnMoves[i][1])).getTeamColor() == piece.getTeamColor())) {
                                //pass
                            } else if ((board.getPiece(new ChessPosition(myPosition.getRow() + blackPawnMoves[i][0], myPosition.getColumn() + blackPawnMoves[i][1])).getTeamColor() != piece.getTeamColor())) {
                                if (myPosition.getRow() + blackPawnMoves[i][0] == 1) {
                                    moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + blackPawnMoves[i][0], myPosition.getColumn() + blackPawnMoves[i][1]), PieceType.QUEEN));
                                    moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + blackPawnMoves[i][0], myPosition.getColumn() + blackPawnMoves[i][1]), PieceType.BISHOP));
                                    moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + blackPawnMoves[i][0], myPosition.getColumn() + blackPawnMoves[i][1]), PieceType.KNIGHT));
                                    moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + blackPawnMoves[i][0], myPosition.getColumn() + blackPawnMoves[i][1]), PieceType.ROOK));
                                } else {
                                    moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + blackPawnMoves[i][0], myPosition.getColumn() + blackPawnMoves[i][1]), null));
                                }
                            } else {
                                //pass
                            }
                        }
                    } else if (i == 0) {
                        if (board.getPiece(new ChessPosition(myPosition.getRow() + blackPawnMoves[i][0], myPosition.getColumn() + blackPawnMoves[i][1])) != null) {
                            //pass
                        } else {
                            if (myPosition.getRow() + blackPawnMoves[i][0] == 1) {
                                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + blackPawnMoves[i][0], myPosition.getColumn() + blackPawnMoves[i][1]), PieceType.QUEEN));
                                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + blackPawnMoves[i][0], myPosition.getColumn() + blackPawnMoves[i][1]), PieceType.BISHOP));
                                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + blackPawnMoves[i][0], myPosition.getColumn() + blackPawnMoves[i][1]), PieceType.KNIGHT));
                                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + blackPawnMoves[i][0], myPosition.getColumn() + blackPawnMoves[i][1]), PieceType.ROOK));
                            } else {
                                moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + blackPawnMoves[i][0], myPosition.getColumn() + blackPawnMoves[i][1]), null));
                            }
                        }
                    }
                }
            }
            return moves;
        }
        return null;
    }

    private void kingAndKnightMoves(ChessBoard board, ChessPosition myPosition, int[][] possibleMoves, ChessPiece piece,
                                    ArrayList<ChessMove> moves) {
        for (int i = 0; i < 8; i++) {
            if (!isInBounds(myPosition.getRow() + possibleMoves[i][0], myPosition.getColumn()
                    + possibleMoves[i][1])) {
            } else {
                if (board.getPiece(new ChessPosition(myPosition.getRow() + possibleMoves[i][0],
                        myPosition.getColumn() + possibleMoves[i][1])) != null) {
                    if ((board.getPiece(new ChessPosition(myPosition.getRow() + possibleMoves[i][0],
                            myPosition.getColumn() + possibleMoves[i][1])).getTeamColor() ==
                            piece.getTeamColor())) {
                        //pass
                    } else {
                        stupidFetchingMethod(moves, myPosition, possibleMoves, i);
                    }
                } else {
                    stupidFetchingMethod(moves, myPosition, possibleMoves, i);
                }
            }
        }
    }

    private void stupidFetchingMethod(ArrayList<ChessMove> moves, ChessPosition myPosition, int[][] possibleMoves,
                                      int i) {
        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()),
                new ChessPosition(myPosition.getRow() + possibleMoves[i][0],
                        myPosition.getColumn() + possibleMoves[i][1]), null));
    }

    private void straightDownLeft(ChessPosition myPosition, ChessPiece piece, ChessBoard board,
                                  ArrayList<ChessMove> moves) {
        int left = -1;
        int down = -1;

        while (true) {
            if (!isInBounds(myPosition.getRow() + down, myPosition.getColumn() + left)) {
                break;
            } else {
                if (board.getPiece(new ChessPosition(myPosition.getRow() + down, myPosition.getColumn()
                        + left)) != null) {
                    if ((board.getPiece(new ChessPosition(myPosition.getRow() + down, myPosition.getColumn()
                            + left)).getTeamColor() == piece.getTeamColor())) {
                        break;
                    } else {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()),
                                new ChessPosition(myPosition.getRow() + down, myPosition.getColumn() + left),
                                null));
                        break;
                    }
                } else {
                    moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()),
                            new ChessPosition(myPosition.getRow() + down, myPosition.getColumn() + left),
                            null));
                    left--;
                    down--;
                }
            }
        }
    }

    private void straightUpLeft(ChessPosition myPosition, ChessPiece piece, ChessBoard board,
                                ArrayList<ChessMove> moves) {
        int up = 1;
        int left = -1;

        while (true) {
            if (!isInBounds(myPosition.getRow() + up, myPosition.getColumn() + left)) {
                break;
            } else {
                if (board.getPiece(new ChessPosition(myPosition.getRow() + up, myPosition.getColumn() + left))
                        != null) {
                    if ((board.getPiece(new ChessPosition(myPosition.getRow() + up, myPosition.getColumn()
                            + left)).getTeamColor() == piece.getTeamColor())) {
                        break;
                    } else {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()),
                                new ChessPosition(myPosition.getRow() + up, myPosition.getColumn() + left),
                                null));
                        break;
                    }
                } else {
                    moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()),
                            new ChessPosition(myPosition.getRow() + up, myPosition.getColumn() + left),
                            null));
                    left--;
                    up++;
                }
            }
        }
    }

    private void straightDownRight(ChessPosition myPosition, ChessPiece piece, ChessBoard board,
                                   ArrayList<ChessMove> moves) {
        int down = -1;
        int right = 1;

        while (true) {
            if (!isInBounds(myPosition.getRow() + down, myPosition.getColumn() + right)) {
                break;
            } else {
                if (board.getPiece(new ChessPosition(myPosition.getRow() + down, myPosition.getColumn()
                        + right)) != null) {
                    if ((board.getPiece(new ChessPosition(myPosition.getRow() + down, myPosition.getColumn()
                            + right)).getTeamColor() == piece.getTeamColor())) {
                        break;
                    } else {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()),
                                new ChessPosition(myPosition.getRow() + down, myPosition.getColumn() + right),
                                null));
                        break;
                    }
                } else {
                    moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()),
                            new ChessPosition(myPosition.getRow() + down, myPosition.getColumn() + right),
                            null));
                    down--;
                    right++;
                }
            }
        }
    }

    private void straightUpRight(ChessPosition myPosition, ChessPiece piece, ChessBoard board,
                                 ArrayList<ChessMove> moves) {
        int up = 1;
        int right = 1;

        while (true) {
            if (!isInBounds(myPosition.getRow() + up, myPosition.getColumn() + right)) {
                break;
            } else {
                if (board.getPiece(new ChessPosition(myPosition.getRow() + up, myPosition.getColumn()
                        + right)) != null) {
                    if ((board.getPiece(new ChessPosition(myPosition.getRow() + up, myPosition.getColumn()
                            + right)).getTeamColor() == piece.getTeamColor())) {
                        break;
                    } else {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()),
                                new ChessPosition(myPosition.getRow() + up, myPosition.getColumn() + right),
                                null));
                        break;
                    }
                } else {
                    moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()),
                            new ChessPosition(myPosition.getRow() + up, myPosition.getColumn() + right),
                            null));
                    up++;
                    right++;
                }
            }
        }
    }

    private void straightRight(ChessPosition myPosition, ChessPiece piece, ChessBoard board,
                               ArrayList<ChessMove> moves) {
        int right = 1;

        while (true) {
            if (!isInBounds(myPosition.getRow(), myPosition.getColumn() + right)) {
                break;
            } else {
                if (board.getPiece(new ChessPosition(myPosition.getRow(), myPosition.getColumn()
                        + right)) != null) {
                    if ((board.getPiece(new ChessPosition(myPosition.getRow(), myPosition.getColumn()
                            + right)).getTeamColor() == piece.getTeamColor())) {
                        break;
                    } else {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()),
                                new ChessPosition(myPosition.getRow(), myPosition.getColumn() + right),
                                null));
                        break;
                    }
                } else {
                    moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()),
                            new ChessPosition(myPosition.getRow(), myPosition.getColumn() + right),
                            null));
                    right++;
                }
            }
        }
    }

    private void straightLeft(ChessPosition myPosition, ChessPiece piece, ChessBoard board,
                              ArrayList<ChessMove> moves) {
        int left = -1;

        while (true) {
            if (!isInBounds(myPosition.getRow(), myPosition.getColumn() + left)) {
                break;
            } else {
                if (board.getPiece(new ChessPosition(myPosition.getRow(), myPosition.getColumn()
                        + left)) != null) {
                    if ((board.getPiece(new ChessPosition(myPosition.getRow(), myPosition.getColumn()
                            + left)).getTeamColor() == piece.getTeamColor())) {
                        break;
                    } else {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()),
                                new ChessPosition(myPosition.getRow(), myPosition.getColumn() + left),
                                null));
                        break;
                    }
                } else {
                    moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()),
                            new ChessPosition(myPosition.getRow(), myPosition.getColumn() + left),
                            null));
                    left--;
                }
            }
        }
    }

    private void straightDown(ChessPosition myPosition, ChessPiece piece, ChessBoard board,
                              ArrayList<ChessMove> moves) {
        int down = -1;

        while (true) {
            if (!isInBounds(myPosition.getRow() + down, myPosition.getColumn())) {
                break;
            } else {
                if (board.getPiece(new ChessPosition(myPosition.getRow() + down, myPosition.getColumn()))
                        != null) {
                    if ((board.getPiece(new ChessPosition(myPosition.getRow() + down,
                            myPosition.getColumn())).getTeamColor() == piece.getTeamColor())) {
                        break;
                    } else {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()),
                                new ChessPosition(myPosition.getRow() + down, myPosition.getColumn()),
                                null));
                        break;
                    }
                } else {
                    moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()),
                            new ChessPosition(myPosition.getRow() + down, myPosition.getColumn()),
                            null));
                    down--;
                }
            }
        }
    }

    private void straightUp(ChessPosition myPosition, ChessPiece piece, ChessBoard board, ArrayList<ChessMove> moves) {
        int up = 1;

        while (true) {
            if (!isInBounds(myPosition.getRow() + up, myPosition.getColumn())) {
                break;
            } else {
                if (board.getPiece(new ChessPosition(myPosition.getRow() + up, myPosition.getColumn())) != null) {
                    if ((board.getPiece(new ChessPosition(myPosition.getRow() + up,
                            myPosition.getColumn())).getTeamColor() == piece.getTeamColor())) {
                        break;
                    } else {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()),
                                new ChessPosition(myPosition.getRow() + up, myPosition.getColumn()),
                                null));
                        break;
                    }
                } else {
                    moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()),
                            new ChessPosition(myPosition.getRow() + up, myPosition.getColumn()),
                            null));
                    up++;
                }
            }
        }
    }

}
