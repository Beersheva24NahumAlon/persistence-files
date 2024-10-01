package telran.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.*;
import java.nio.file.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class StringStreamsTest {
    private static final String PRINT_STREAM_FILE = "printStreamFile.txt";
    private static final String PRINT_WRITER_FILE = "printWriterFile.txt";

    @Test
    void printStreamTest() throws FileNotFoundException {
        PrintStream printStream = new PrintStream(PRINT_STREAM_FILE);
        printStream.println("Hello");
        printStream.close();
    }

    @Test
    @Disabled
    void printWriterTest() throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(PRINT_WRITER_FILE);
        printWriter.println("Hello");
        printWriter.close();
    }

    @Test
    void bufferedReaderTest() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(PRINT_WRITER_FILE));
        assertEquals("Hello", reader.readLine());
        reader.close();
    }

    @Test 
    void printingDirectoryTest() throws Exception {
        printDirectoryContent("C:\\Users\\Admin\\Documents", 3);
    }

    private void printDirectoryContent(String pathString, int depth) throws IOException {
        Path path = Path.of(pathString);
        try {
            Files.walk(path, depth).forEach(p -> printPath(p, path));
        } catch (Exception e) {
            // TODO: handle exception
        } 
    }

    private int levelOfFileSystem(Path path) {
        return path.toAbsolutePath().toString().split("\\\\").length;
    }
 
    private void printPath(Path currentPath, Path rootPath) {
        String shift = "   ";
        int countOfShifts = levelOfFileSystem(currentPath) - levelOfFileSystem(rootPath);
        System.out.printf("%s%s\n",shift.repeat(countOfShifts), currentPath.getFileName());
    }
}
