package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        String gridPath = args[0];
        String wordListPath = args[1];
        int minLength = args.length > 2 ? Integer.parseInt(args[2]) : 0;

        char[][] grid = readGrid(gridPath);
        Set<String> wordSet = Files.lines(Paths.get(wordListPath)).collect(Collectors.toSet());

        List<String> foundWords = findWordsInGrid(grid, wordSet, minLength);
        Collections.sort(foundWords);
        foundWords.forEach(System.out::println);
    }

    static char[][] readGrid(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));
        char[][] grid = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            grid[i] = lines.get(i).toCharArray();
        }
        return grid;
    }

    static List<String> findWordsInGrid(char[][] grid, Set<String> wordSet, int minLength) {
        Set<String> results = new HashSet<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                explore(grid, i, j, wordSet, "", results, new boolean[grid.length][grid[0].length], minLength);
            }
        }
        return new ArrayList<>(results);
    }

    private static void explore(char[][] grid, int x, int y, Set<String> wordSet, String currentWord, Set<String> results, boolean[][] visited, int minLength) {
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[x].length || visited[x][y])
            return;

        currentWord += grid[x][y];
        visited[x][y] = true;

        if (currentWord.length() >= minLength && wordSet.contains(currentWord)) {
            results.add(currentWord);
        }

        explore(grid, x + 1, y, wordSet, currentWord, results, visited, minLength);
        explore(grid, x - 1, y, wordSet, currentWord, results, visited, minLength);
        explore(grid, x, y + 1, wordSet, currentWord, results, visited, minLength);
        explore(grid, x, y - 1, wordSet, currentWord, results, visited, minLength);

        visited[x][y] = false;
    }
}