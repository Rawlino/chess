package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);
        //Haha it's not passing. Some of the basic functions of this file are, but the moves aren't passing. Time to work on this! Passed every other test.
        ArrayList<ChessMove> moves = new ArrayList<>();

        if (piece.getPieceType() == PieceType.KING) {
            int[][] kingMoves = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
            for (int i = 0; i < 8; i++) {
                if (!isInBounds(myPosition.getRow() + kingMoves[i][0], myPosition.getColumn() + kingMoves[i][1])) {
                } else {
                    if (board.getPiece(new ChessPosition(myPosition.getRow() + kingMoves[i][0], myPosition.getColumn() + kingMoves[i][1])) != null) {
                        if ((board.getPiece(new ChessPosition(myPosition.getRow() + kingMoves[i][0], myPosition.getColumn() + kingMoves[i][1])).getTeamColor() == piece.getTeamColor())) {
                            //pass
                        } else {
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + kingMoves[i][0], myPosition.getColumn() + kingMoves[i][1]), null));
                        }
                    } else {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + kingMoves[i][0], myPosition.getColumn() + kingMoves[i][1]), null));
                    }
                }
            }
            return moves;
        } else if (piece.getPieceType() == PieceType.QUEEN) {
            int up = 1;
            int down = -1;
            int left = -1;
            int right = 1;
            while (true) {
                if (!isInBounds(myPosition.getRow() + up, myPosition.getColumn())) {
                    break;
                } else {
                    if (board.getPiece(new ChessPosition(myPosition.getRow() + up, myPosition.getColumn())) != null) {
                        if ((board.getPiece(new ChessPosition(myPosition.getRow() + up, myPosition.getColumn())).getTeamColor() == piece.getTeamColor())) {
                            break;
                        } else {
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + up, myPosition.getColumn()), null));
                            break;
                        }
                    } else {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + up, myPosition.getColumn()), null));
                        up++;
                    }
                }
            }
            while (true) {
                if (!isInBounds(myPosition.getRow() + down, myPosition.getColumn())) {
                    break;
                } else {
                    if (board.getPiece(new ChessPosition(myPosition.getRow() + down, myPosition.getColumn())) != null) {
                        if ((board.getPiece(new ChessPosition(myPosition.getRow() + down, myPosition.getColumn())).getTeamColor() == piece.getTeamColor())) {
                            break;
                        } else {
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + down, myPosition.getColumn()), null));
                            break;
                        }
                    } else {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + down, myPosition.getColumn()), null));
                        down--;
                    }
                }
            }
            while (true) {
                if (!isInBounds(myPosition.getRow(), myPosition.getColumn() + left)) {
                    break;
                } else {
                    if (board.getPiece(new ChessPosition(myPosition.getRow(), myPosition.getColumn() + left)) != null) {
                        if ((board.getPiece(new ChessPosition(myPosition.getRow(), myPosition.getColumn() + left)).getTeamColor() == piece.getTeamColor())) {
                            break;
                        } else {
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow(), myPosition.getColumn() + left), null));
                            break;
                        }
                    } else {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow(), myPosition.getColumn() + left), null));
                        left--;
                    }
                }
            }
            while (true) {
                if (!isInBounds(myPosition.getRow(), myPosition.getColumn() + right)) {
                    break;
                } else {
                    if (board.getPiece(new ChessPosition(myPosition.getRow(), myPosition.getColumn() + right)) != null) {
                        if ((board.getPiece(new ChessPosition(myPosition.getRow(), myPosition.getColumn() + right)).getTeamColor() == piece.getTeamColor())) {
                            break;
                        } else {
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow(), myPosition.getColumn() + right), null));
                            break;
                        }
                    } else {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow(), myPosition.getColumn() + right), null));
                        right++;
                    }
                }
            }
            up = 1;
            down = -1;
            right = 1;
            left = -1;
            while (true) {
                if (!isInBounds(myPosition.getRow() + up, myPosition.getColumn() + right)) {
                    break;
                } else {
                    if (board.getPiece(new ChessPosition(myPosition.getRow() + up, myPosition.getColumn() + right)) != null) {
                        if ((board.getPiece(new ChessPosition(myPosition.getRow() + up, myPosition.getColumn() + right)).getTeamColor() == piece.getTeamColor())) {
                            break;
                        } else {
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + up, myPosition.getColumn() + right), null));
                            break;
                        }
                    } else {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + up, myPosition.getColumn() + right), null));
                        up++;
                        right++;
                    }
                }
            }
            down = -1;
            right = 1;
            while (true) {
                if (!isInBounds(myPosition.getRow() + down, myPosition.getColumn() + right)) {
                    break;
                } else {
                    if (board.getPiece(new ChessPosition(myPosition.getRow() + down, myPosition.getColumn() + right)) != null) {
                        if ((board.getPiece(new ChessPosition(myPosition.getRow() + down, myPosition.getColumn() + right)).getTeamColor() == piece.getTeamColor())) {
                            break;
                        } else {
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + down, myPosition.getColumn() + right), null));
                            break;
                        }
                    } else {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + down, myPosition.getColumn() + right), null));
                        down--;
                        right++;
                    }
                }
            }
            up = 1;
            left = -1;
            while (true) {
                if (!isInBounds(myPosition.getRow() + up, myPosition.getColumn() + left)) {
                    break;
                } else {
                    if (board.getPiece(new ChessPosition(myPosition.getRow() + up, myPosition.getColumn() + left)) != null) {
                        if ((board.getPiece(new ChessPosition(myPosition.getRow() + up, myPosition.getColumn() + left)).getTeamColor() == piece.getTeamColor())) {
                            break;
                        } else {
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + up, myPosition.getColumn() + left), null));
                            break;
                        }
                    } else {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + up, myPosition.getColumn() + left), null));
                        left--;
                        up++;
                    }
                }
            }
            down = -1;
            left = -1;
            while (true) {
                if (!isInBounds(myPosition.getRow() + down, myPosition.getColumn() + left)) {
                    break;
                } else {
                    if (board.getPiece(new ChessPosition(myPosition.getRow() + down, myPosition.getColumn() + left)) != null) {
                        if ((board.getPiece(new ChessPosition(myPosition.getRow() + down, myPosition.getColumn() + left)).getTeamColor() == piece.getTeamColor())) {
                            break;
                        } else {
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + down, myPosition.getColumn() + left), null));
                            break;
                        }
                    } else {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + down, myPosition.getColumn() + left), null));
                        left--;
                        down--;
                    }
                }
            }
            return moves;
        } else if (piece.getPieceType() == PieceType.BISHOP) {
            int up = 1;
            int down = -1;
            int right = 1;
            int left = -1;
            while (true) {
                if (!isInBounds(myPosition.getRow() + up, myPosition.getColumn() + right)) {
                    break;
                } else {
                    if (board.getPiece(new ChessPosition(myPosition.getRow() + up, myPosition.getColumn() + right)) != null) {
                        if ((board.getPiece(new ChessPosition(myPosition.getRow() + up, myPosition.getColumn() + right)).getTeamColor() == piece.getTeamColor())) {
                            break;
                        } else {
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + up, myPosition.getColumn() + right), null));
                            break;
                        }
                    } else {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + up, myPosition.getColumn() + right), null));
                        up++;
                        right++;
                    }
                }
            }
            down = -1;
            right = 1;
            while (true) {
                if (!isInBounds(myPosition.getRow() + down, myPosition.getColumn() + right)) {
                    break;
                } else {
                    if (board.getPiece(new ChessPosition(myPosition.getRow() + down, myPosition.getColumn() + right)) != null) {
                        if ((board.getPiece(new ChessPosition(myPosition.getRow() + down, myPosition.getColumn() + right)).getTeamColor() == piece.getTeamColor())) {
                            break;
                        } else {
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + down, myPosition.getColumn() + right), null));
                            break;
                        }
                    } else {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + down, myPosition.getColumn() + right), null));
                        down--;
                        right++;
                    }
                }
            }
            up = 1;
            left = -1;
            while (true) {
                if (!isInBounds(myPosition.getRow() + up, myPosition.getColumn() + left)) {
                    break;
                } else {
                    if (board.getPiece(new ChessPosition(myPosition.getRow() + up, myPosition.getColumn() + left)) != null) {
                        if ((board.getPiece(new ChessPosition(myPosition.getRow() + up, myPosition.getColumn() + left)).getTeamColor() == piece.getTeamColor())) {
                            break;
                        } else {
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + up, myPosition.getColumn() + left), null));
                            break;
                        }
                    } else {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + up, myPosition.getColumn() + left), null));
                        left--;
                        up++;
                    }
                }
            }
            down = -1;
            left = -1;
            while (true) {
                if (!isInBounds(myPosition.getRow() + down, myPosition.getColumn() + left)) {
                    break;
                } else {
                    if (board.getPiece(new ChessPosition(myPosition.getRow() + down, myPosition.getColumn() + left)) != null) {
                        if ((board.getPiece(new ChessPosition(myPosition.getRow() + down, myPosition.getColumn() + left)).getTeamColor() == piece.getTeamColor())) {
                            break;
                        } else {
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + down, myPosition.getColumn() + left), null));
                            break;
                        }
                    } else {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + down, myPosition.getColumn() + left), null));
                        left--;
                        down--;
                    }
                }
            }
            return moves;
        } else if (piece.getPieceType() == PieceType.KNIGHT) {
            int[][] knightMoves = {{2, -1}, {2, 1}, {1, 2}, {1, -2}, {-1, -2}, {-1, 2}, {-2, -1}, {-2, 1}};
            for (int i = 0; i < 8; i++) {
                if (!isInBounds(myPosition.getRow() + knightMoves[i][0], myPosition.getColumn() + knightMoves[i][1])) {
                } else {
                    if (board.getPiece(new ChessPosition(myPosition.getRow() + knightMoves[i][0], myPosition.getColumn() + knightMoves[i][1])) != null) {
                        if ((board.getPiece(new ChessPosition(myPosition.getRow() + knightMoves[i][0], myPosition.getColumn() + knightMoves[i][1])).getTeamColor() == piece.getTeamColor())) {
                            //pass
                        } else {
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + knightMoves[i][0], myPosition.getColumn() + knightMoves[i][1]), null));
                        }
                    } else {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + knightMoves[i][0], myPosition.getColumn() + knightMoves[i][1]), null));
                    }
                }
            }
            return moves;
        } else if (piece.getPieceType() == PieceType.ROOK) {
            int up = 1;
            int down = -1;
            int left = -1;
            int right = 1;
            while (true) {
                if (!isInBounds(myPosition.getRow() + up, myPosition.getColumn())) {
                    break;
                } else {
                    if (board.getPiece(new ChessPosition(myPosition.getRow() + up, myPosition.getColumn())) != null) {
                        if ((board.getPiece(new ChessPosition(myPosition.getRow() + up, myPosition.getColumn())).getTeamColor() == piece.getTeamColor())) {
                            break;
                        } else {
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + up, myPosition.getColumn()), null));
                            break;
                        }
                    } else {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + up, myPosition.getColumn()), null));
                        up++;
                    }
                }
            }
            while (true) {
                if (!isInBounds(myPosition.getRow() + down, myPosition.getColumn())) {
                    break;
                } else {
                    if (board.getPiece(new ChessPosition(myPosition.getRow() + down, myPosition.getColumn())) != null) {
                        if ((board.getPiece(new ChessPosition(myPosition.getRow() + down, myPosition.getColumn())).getTeamColor() == piece.getTeamColor())) {
                            break;
                        } else {
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + down, myPosition.getColumn()), null));
                            break;
                        }
                    } else {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow() + down, myPosition.getColumn()), null));
                        down--;
                    }
                }
            }
            while (true) {
                if (!isInBounds(myPosition.getRow(), myPosition.getColumn() + left)) {
                    break;
                } else {
                    if (board.getPiece(new ChessPosition(myPosition.getRow(), myPosition.getColumn() + left)) != null) {
                        if ((board.getPiece(new ChessPosition(myPosition.getRow(), myPosition.getColumn() + left)).getTeamColor() == piece.getTeamColor())) {
                            break;
                        } else {
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow(), myPosition.getColumn() + left), null));
                            break;
                        }
                    } else {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow(), myPosition.getColumn() + left), null));
                        left--;
                    }
                }
            }
            while (true) {
                if (!isInBounds(myPosition.getRow(), myPosition.getColumn() + right)) {
                    break;
                } else {
                    if (board.getPiece(new ChessPosition(myPosition.getRow(), myPosition.getColumn() + right)) != null) {
                        if ((board.getPiece(new ChessPosition(myPosition.getRow(), myPosition.getColumn() + right)).getTeamColor() == piece.getTeamColor())) {
                            break;
                        } else {
                            moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow(), myPosition.getColumn() + right), null));
                            break;
                        }
                    } else {
                        moves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(myPosition.getRow(), myPosition.getColumn() + right), null));
                        right++;
                    }
                }
            }
            return moves;
        } else if (piece.getPieceType() == PieceType.PAWN) {
            return List.of(new ChessMove(new ChessPosition(5, 4), new ChessPosition(1, 8), null));
        }
        return null;
    }
}
