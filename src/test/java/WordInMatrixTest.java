import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WordInMatrixTest {
    private static WordInMatrix wordInMatrix;

    @BeforeAll
    public static void beforeAll() {
        wordInMatrix = new WordInMatrix();
    }

    @Test
    public void checkForNullAndEmptyStatements() {
        String notNullWord = "WHO";
        String notNullMatrix = "GHWFOHFBC";
        assertThrows(RuntimeException.class, () -> wordInMatrix.getCells(null, null));
        assertThrows(RuntimeException.class, () -> wordInMatrix.getCells(notNullMatrix, null));
        assertThrows(RuntimeException.class, () -> wordInMatrix.getCells(null, notNullWord));
        assertThrows(RuntimeException.class, () -> wordInMatrix.getCells("", ""));
        assertThrows(RuntimeException.class, () -> wordInMatrix.getCells(notNullMatrix, ""));
        assertThrows(RuntimeException.class, () -> wordInMatrix.getCells("", notNullWord));
    }

    @Test
    public void checkForAppropriateMatrixLength() {
        String word = "CAR";
        assertThrows(RuntimeException.class, () -> wordInMatrix.getCells("AR", word));
        assertThrows(RuntimeException.class, () -> wordInMatrix.getCells("ARTCT", word));
        assertThrows(RuntimeException.class, () ->
                wordInMatrix.getCells("HKFDVLDTGDGNMCABXQZRH", word));
    }

    @Test
    public void checkForNonLiteralInWord() {
        String wordWithDigit = "APP1E";
        String matrixWithDigit = "UKJVXNAPBXE1PZHVNCDKBVVNM";
        assertThrows(RuntimeException.class, ()
                -> wordInMatrix.getCells(matrixWithDigit, wordWithDigit));
        String wordWithRegex = "AP^LE";
        String matrixWithRegex = "UKJVXNAPBXEL^ZHVNCDKBVVNM";
        assertThrows(RuntimeException.class, ()
                -> wordInMatrix.getCells(matrixWithRegex, wordWithRegex));
        String wordWithSpace = "APPL ";
        String matrixWithSpace = "UKJVXNAPBX LPZHVNCDKBVVNM";
        assertThrows(RuntimeException.class, ()
                -> wordInMatrix.getCells(matrixWithSpace, wordWithSpace));
    }

    @Test
    public void checkForLowerCase_OK() {
        assertEquals("[1,2]->[1,3]->[0,3]->[0,2]",
                wordInMatrix.getCells("QLGNAEKIRLRNGEAE", "kinG"));
        assertEquals("[1,2]->[1,3]->[0,3]->[0,2]",
                wordInMatrix.getCells("qLGnAEkIRLRNGEgE", "KING"));
    }

    @Test
    public void checkWord_OK() {
        assertEquals("[0,0]", wordInMatrix.getCells("d", "d"));
        assertEquals("[0,1]", wordInMatrix.getCells("QLGNAEKIRLRNGEAE", "l"));
        assertEquals("[2,2]->[3,2]", wordInMatrix.getCells("RTBAUYzrlmAWSETV", "AT"));
        assertEquals("[1,1]->[1,2]->[2,2]->[2,1]->[2,0]",
                wordInMatrix.getCells("UKJVXNAPBxELPLHVNLDkbvVnm", "APPLE"));
        assertEquals("[4,3]->[3,3]->[3,2]->[3,1]->[2,1]->[2,0]->[1,0]",
                wordInMatrix.getCells("ZURTXsIaqbladJlmmiNiausaH", "animals"));
    }

    @Test
    public void checkWord_NotOK() {
        assertThrows(RuntimeException.class, ()
                -> wordInMatrix.getCells("abghywujkbszcbsizhgnipfyiwarqxvbmnlr", "RAISING"));
        assertThrows(RuntimeException.class, () -> wordInMatrix.getCells("RTSUDWLAT", "DAD"));
    }

    @Test
    public void checkMatrixWithLotOfWrongChainForWord_Ok() {
        assertEquals("[0,1]->[1,1]->[1,2]->[1,3]->[2,3]",
                wordInMatrix.getCells("RPEIVaEaCXCAYhTzcqpesBOfd", "peach"));
    }
}
