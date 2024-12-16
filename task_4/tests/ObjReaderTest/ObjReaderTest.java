package ObjReaderTest;

import com.cgvsu.math.typesVectors.Vector2f;
import com.cgvsu.math.typesVectors.Vector3f;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.objreader.ObjReaderException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ObjReaderTest extends ObjReader {
    @Test
    void read() {
        File file = new File("tests/ObjReaderTest/objectReaderTest.obj");
        Path fileName = Path.of(file.getAbsolutePath());
        String fileContent;
        try {
            fileContent = Files.readString(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ObjReader.read(fileContent);
    }

    @Test
    void testParseVertex01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.03f);
        assertEquals(result, expectedResult);
    }

    @Test
    void testParseVertex02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1"));
        Vector3f result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.10f);
        Assertions.assertNotEquals(result, expectedResult);
    }

    @Test
    void testParseVertex03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few vertex arguments.";
            assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseVertex04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("ab", "o", "ba"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseVertex05() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "5.0l"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseVertex06() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0", "4.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too many vertex arguments.";
            assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseTextureVertex01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02"));
        Vector2f result = ObjReader.parseTextureVertex(wordsInLineWithoutToken, 5);
        Vector2f expectedResult = new Vector2f(1.01f, 1.02f);
        assertEquals(result, expectedResult);
    }

    @Test
    void testParseTextureVertex02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too many texture vertex arguments.";
            assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseTextureVertex03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02l"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseNormal01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03", "1.04"));
        try {
            ObjReader.parseNormal(wordsInLineWithoutToken, 8);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 8. Too many normal arguments.";
            assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseNormal02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.03f);
        Vector3f result = ObjReader.parseNormal(wordsInLineWithoutToken, 8);
        assertEquals(expectedResult, result);
    }

    @Test
    void testParseNormal03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02l"));
        try {
            ObjReader.parseNormal(wordsInLineWithoutToken, 8);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 8. Failed to parse float value.";
            assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseNormal04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02"));
        try {
            ObjReader.parseNormal(wordsInLineWithoutToken, 8);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 8. Too few normal arguments.";
            assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseFace01() {
        String fileContent =
                """
                        v 1 2 3
                        v 2 3 4
                        v 3 4 5
                        vt 1 2
                        vt 1 2
                        vt 1 2
                        vn 1 2 3
                        vn 2 4 1
                        f 1//2 2/3/1""";
        ObjReaderException thrown = Assertions.assertThrows(ObjReaderException.class, () -> ObjReader.read(fileContent));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 9. Incorrect face format."));
    }

    @Test
    void testParseFace02() {
        String fileContent =
                """
                        v 1 2 3
                        v 2 3 4
                        v 3 4 5
                        vt 1 2
                        vt 1 2
                        vt 1 2
                        vn 1 2 3
                        vn 2 4 1
                        f 1//2 2//1""";
        ObjReaderException thrown = Assertions.assertThrows(ObjReaderException.class, () -> ObjReader.read(fileContent));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 9. Incorrect face format."));
    }

    @Test
    void testParseFace03() {
        String fileContent =
                """
                        v 1 2 3
                        v 2 3 4
                        v 3 4 5
                        vt 1 2
                        vt 1 2
                        vt 1 2
                        vn 1 2 3
                        vn 2 4 1
                        f 1 2""";
        ObjReaderException thrown = Assertions.assertThrows(ObjReaderException.class, () -> ObjReader.read(fileContent));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 9. Incorrect face format."));
    }

    @Test
    void testParseFace04() {
        String fileContent =
                """
                        v 1 2 3
                        v 2 3 4
                        v 3 4 5
                        vt 1 2
                        vt 1 2
                        vt 1 2
                        vn 1 2 3
                        vn 2 4 1
                        f 1//3 2""";
        ObjReaderException thrown = Assertions.assertThrows(ObjReaderException.class, () -> ObjReader.read(fileContent));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 9. Incorrect face format."));
    }

    @Test
    void testParseFace05() {
        String fileContent =
                """
                        v 1 2 3
                        v 2 3 4
                        v 3 4 5
                        vt 1 2
                        vt 1 2
                        vt 1 2
                        vn 1 2 3
                        vn 2 4 1
                        f 1//2 3/2/1 4/4/2""";
        ObjReaderException thrown = Assertions.assertThrows(ObjReaderException.class, () -> ObjReader.read(fileContent));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 9. Incorrect face format."));
    }

    @Test
    void testParseFace06() {
        String fileContent =
                """
                        v 1 2 3
                        v 2 3 4
                        v 3 4 5
                        vt 1 2
                        vt 1 2
                        vt 1 2
                        vn 1 2 3
                        vn 2 4 1
                        f 1/2/3 3/2/1 a/b/c""";
        ObjReaderException thrown = Assertions.assertThrows(ObjReaderException.class, () -> ObjReader.read(fileContent));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 9. Failed to parse int value."));
    }

    @Test
    void testParseFace07() {
        String fileContent =
                """
                        v 1 2 3
                        v 2 3 4
                        v 3 4 5
                        vt 1 2
                        vt 1 2
                        vt 1 2
                        vn 1 2 3
                        vn 2 4 1
                        f 3/2/1 a/b/c god""";
        ObjReaderException thrown = Assertions.assertThrows(ObjReaderException.class, () -> ObjReader.read(fileContent));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 9. Failed to parse int value."));
    }

    @Test
    void testParseFace08(){
        String fileContent =
                """
                        v 1 2 3
                        v 2 3 4
                        v 3 4 5
                        vt 1 2
                        vt 1 2
                        vt 1 2
                        vn 1 2 3
                        vn 2 4 1
                        f 1/1/1 2/2/2 3/3/""";
        ObjReaderException thrown = Assertions.assertThrows(ObjReaderException.class, () -> ObjReader.read(fileContent));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 9. Incorrect face format."));
    }

    @Test
    void testParseFace09() {
        String fileContent =
                """
                        v 1 2 3
                        v 2 3 4
                        v 3 4 5
                        vt 1 2
                        vt 1 2
                        vt 1 2
                        vn 1 2 3
                        vn 2 4 1
                        f 1/2/3 4/5/6 7/8/9""";
        ObjReaderException thrown = Assertions.assertThrows(ObjReaderException.class, () -> ObjReader.read(fileContent));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 9. Index is too much."));
    }

}