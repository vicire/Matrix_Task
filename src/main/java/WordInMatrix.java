public class WordInMatrix {
    public String getCells(String matrix, String word) {
        checkingForErrors(matrix, word);
        matrix = matrix.toUpperCase();
        word = word.toUpperCase();
        for (int i = 0; i < matrix.length(); i++) {
            int letterPosition = matrix.indexOf(word.charAt(0), i);
            if (letterPosition == -1) {
                throw new RuntimeException("Your request is not acceptable, try to change word");
            }
            String backup = matrix;
            matrix = markMyWay(matrix, letterPosition, word.charAt(0));
            String cellsSequence = "";
            int row = (int) Math.sqrt(matrix.length());
            String checkAndAddCells = checkSells(matrix, word.substring(1),
                    letterPosition, row, cellsSequence);
            if (!(checkAndAddCells.equals("Wrong word"))) {
                StringBuilder firstLetterCell = new StringBuilder("[");
                firstLetterCell.append(letterPosition / row)
                        .append(",")
                        .append(letterPosition % row)
                        .append("]")
                        .append(checkAndAddCells);
                return firstLetterCell.toString();
            }
            matrix = backup;
        }
        throw new RuntimeException("Your request is not acceptable. Please, try to change word");
    }

    private String checkSells(String matrix, String word,
                              int letterPosition, int row, String cellsSequence) {
        if (word.length() == 0) {
            return cellsSequence;
        }
        if (letterPosition >= row && word.charAt(0) == matrix.charAt(letterPosition - row)) {
            letterPosition = letterPosition - row;
            String oneCell = cellsSequence;
            oneCell = addToCellsSequence(oneCell, letterPosition, row);
            matrix = markMyWay(matrix, letterPosition, word.charAt(0));
            oneCell = checkSells(matrix,word.substring(1), letterPosition, row, oneCell);
            if (oneCell.equals("Wrong word")) {
                letterPosition += row;
            } else {
                return oneCell;
            }
        }
        if (letterPosition + row < matrix.length()
                && word.charAt(0) == matrix.charAt(letterPosition + row)) {
            letterPosition = letterPosition + row;
            String oneCell = cellsSequence;
            oneCell = addToCellsSequence(oneCell, letterPosition, row);
            matrix = markMyWay(matrix, letterPosition, word.charAt(0));
            oneCell = checkSells(matrix,word.substring(1), letterPosition, row, oneCell);
            if ((oneCell.equals("Wrong word"))) {
                letterPosition -= row;
            } else {
                return oneCell;
            }
        }
        if (letterPosition % row != 0 && word.charAt(0) == matrix.charAt(letterPosition - 1)) {
            letterPosition = letterPosition - 1;
            String oneCell = cellsSequence;
            oneCell = addToCellsSequence(oneCell, letterPosition, row);
            matrix = markMyWay(matrix, letterPosition, word.charAt(0));
            oneCell = checkSells(matrix,word.substring(1), letterPosition, row, oneCell);
            if (oneCell.equals("Wrong word")) {
                letterPosition += 1;
            } else {
                return oneCell;
            }
        }
        if (letterPosition % row != row - 1
                && word.charAt(0) == matrix.charAt(letterPosition + 1)) {
            letterPosition = letterPosition + 1;
            String oneCell = cellsSequence;
            oneCell = addToCellsSequence(oneCell, letterPosition, row);
            matrix = markMyWay(matrix, letterPosition, word.charAt(0));
            oneCell = checkSells(matrix,word.substring(1), letterPosition, row, oneCell);
            return oneCell;
        }
        return "Wrong word";
    }

    private String markMyWay(String matrix, int letterPosition, char letterToChange) {
        StringBuilder changeString = new StringBuilder(matrix);
        changeString.setCharAt(letterPosition, (char) (letterToChange + 32));
        return changeString.toString();
    }

    private String addToCellsSequence(String cellsSequence, int letterPosition, int row) {
        StringBuilder singleCell = new StringBuilder(cellsSequence);
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
