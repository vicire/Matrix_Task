import java.util.ArrayList;
import java.util.List;

public class WordInMatrix {
    public String getCells(String matrix, String word) {
        checkingForErrors(matrix, word);
        matrix = matrix.toUpperCase();
        word = word.toUpperCase();
        if (word.length() == 1 && matrix.contains(word)) {
            return addToCellsSequence(matrix.indexOf(word), (int) Math.sqrt(matrix.length()))
                    .replace("->", "");
        }
        for (int i = 0; i < matrix.length() && word.length() > 1; i++) {
            int letterPosition = matrix.indexOf(word.charAt(0), i);
            if (letterPosition == -1) {
                throw new RuntimeException("Your request is not acceptable, try to change word");
            }
            List<String> cellsSequence = new ArrayList<>();
            int row = (int) Math.sqrt(matrix.length());
            String backup = matrix;
            matrix = markMyWay(matrix, letterPosition, word.charAt(0));
            cellsSequence = checkSells(matrix, word.substring(1),
                    letterPosition, row, cellsSequence);
            if (!(cellsSequence.get(cellsSequence.size() - 1).equals("Wrong word"))) {
                StringBuilder letterCells = new StringBuilder();
                letterCells.append(addToCellsSequence(letterPosition, row));
                for (String cell : cellsSequence) {
                    letterCells.append(cell);
                }
                return letterCells.toString().replaceFirst("->", "");
            }
            matrix = backup;
        }
        throw new RuntimeException("Your request is not acceptable. Please, try to change word");
    }

    private List<String> checkSells(String matrix, String word,
                              int letterPosition, int row, List<String> cellsSequence) {
        if (word.length() == 0) {
            return cellsSequence;
        }
        if (letterPosition >= row && word.charAt(0) == matrix.charAt(letterPosition - row)) {
            letterPosition = letterPosition - row;
            cellsSequence.add(addToCellsSequence(letterPosition, row));
            matrix = markMyWay(matrix, letterPosition, word.charAt(0));
            cellsSequence = checkSells(matrix, word.substring(1),
                    letterPosition, row, cellsSequence);
            if (cellsSequence.get(cellsSequence.size() - 1).equals("Wrong word")) {
                cellsSequence.remove("Wrong word");
                cellsSequence.remove(cellsSequence.size() - 1);
                letterPosition += row;
            } else {
                return cellsSequence;
            }
        }
        if (letterPosition + row < matrix.length()
                && word.charAt(0) == matrix.charAt(letterPosition + row)) {
            letterPosition = letterPosition + row;
            cellsSequence.add(addToCellsSequence(letterPosition, row));
            matrix = markMyWay(matrix, letterPosition, word.charAt(0));
            cellsSequence = checkSells(matrix, word.substring(1),
                    letterPosition, row, cellsSequence);
            if ((cellsSequence.get(cellsSequence.size() - 1).equals("Wrong word"))) {
                cellsSequence.remove("Wrong word");
                cellsSequence.remove(cellsSequence.size() - 1);
                letterPosition -= row;
            } else {
                return cellsSequence;
            }

        }
        if (letterPosition % row != 0 && word.charAt(0) == matrix.charAt(letterPosition - 1)) {
            letterPosition = letterPosition - 1;
            cellsSequence.add(addToCellsSequence(letterPosition, row));
            matrix = markMyWay(matrix, letterPosition, word.charAt(0));
            cellsSequence = checkSells(matrix, word.substring(1),
                    letterPosition, row, cellsSequence);
            if (cellsSequence.get(cellsSequence.size() - 1).equals("Wrong word")) {
                cellsSequence.remove("Wrong word");
                cellsSequence.remove(cellsSequence.size() - 1);
                letterPosition += 1;
            } else {
                return cellsSequence;
            }

        }
        if (letterPosition % row != row - 1
                && word.charAt(0) == matrix.charAt(letterPosition + 1)) {
            letterPosition = letterPosition + 1;
            cellsSequence.add(addToCellsSequence(letterPosition, row));
            matrix = markMyWay(matrix, letterPosition, word.charAt(0));
            cellsSequence = checkSells(matrix, word.substring(1),
                    letterPosition, row, cellsSequence);
            return cellsSequence;
        }
        cellsSequence.add("Wrong word");
        return cellsSequence;
    }

    private String markMyWay(String matrix, int letterPosition, char letterToChange) {
        StringBuilder changeString = new StringBuilder(matrix);
        changeString.setCharAt(letterPosition, (char) (letterToChange + 32));
        return changeString.toString();
    }

    private String addToCellsSequence(int letterPosition, int row) {
        StringBuilder singleCell = new StringBuilder();
        singleCell.append("->[")
                .append(letterPosition / row)
                .append(",")
                .append(letterPosition % row)
                .append("]");
        return singleCell.toString();
    }

    private void checkingForErrors(String matrix, String word) {
        if (matrix == null || word == null || word.length() == 0 || matrix.length() == 0) {
            throw new RuntimeException("Your request is not acceptable. "
                    + "Please, set the matrix or word correctly");
        }
        if (Math.pow((int) Math.sqrt(matrix.length()), 2) != matrix.length()
                || (matrix.length() < word.length())) {
            throw new RuntimeException("Your request is not acceptable. "
                    + "Please, set the correct number of character in your matrix");
        }
        for (int i = 0; i < word.length(); i++) {
            if (!(Character.isLetter(word.charAt(i)))) {
                throw new RuntimeException("Your request is not acceptable, "
                        + "your word has non-literal characters. Please, try to change word");
            }
        }
    }
}
