package org.example;

import org.junit.BeforeClass;
import org.junit.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.Assert.*;

public class MainTest {
    private static Set<String> wordSet;
    private static final String GRID_FILE_PATH = "./../../grid10x10.txt";
    private static final String SOLUTION_FILE_PATH = "./../../solution10x10.txt";

    @BeforeClass
    public static void setUpClass() throws Exception {
        // Replace with the path to your words file
        Path wordFilePath = Paths.get("./../../words.txt");
        wordSet = new HashSet<>(Files.readAllLines(wordFilePath));
    }

    @Test
    public void testReadGrid() throws Exception {
        //Arrange
        Path gridPath = Paths.get(GRID_FILE_PATH);
        char[][] expectedGrid = {
                {'j', 'o', 'p'},
                {'s', 'h', 'n'},
                {'t', 'v', 'q'}
        };

        //Act
        char[][] actualGrid = Main.readGrid(gridPath.toString());

        //Assert
        assertArrayEquals("The grids do not match", expectedGrid, actualGrid);
    }

    @Test
    public void testFindWordsInGridUsingFiles() throws Exception {
        //Arrange
        Path gridPath = Paths.get(GRID_FILE_PATH);
        char[][] grid = Main.readGrid(gridPath.toString());

        Path solutionPath = Paths.get(SOLUTION_FILE_PATH);
        List<String> expectedWords = Files.readAllLines(solutionPath);

        //Act
        List<String> foundWords = Main.findWordsInGrid(grid, wordSet, 0);

        //Assert
        Collections.sort(expectedWords);
        Collections.sort(foundWords);
        assertEquals("The found words do not match the expected solution", expectedWords, foundWords);
    }
}
