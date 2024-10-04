package telran.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

import org.junit.jupiter.api.*;

public class StringStreamsTest {
    private static final String PRINT_STREAM_FILE = "printStreamFile.txt";
    private static final String PRINT_WRITER_FILE = "printWriterFile.txt";
    
    public class MyFileVisitor extends SimpleFileVisitor<Path> {
        private static final String SHIFT = "   ";
        Path root;

        public MyFileVisitor(Path root) {
            this.root = root;
        }

        private void dysplayPath(Path path, String msg) {
            int count = path.getNameCount() - root.getNameCount();
            System.out.printf("%s%s%s\n", SHIFT.repeat(count), path.getFileName(), msg);
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            dysplayPath(dir, "");
            return FileVisitResult.CONTINUE;
        }
    
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            dysplayPath(file, "");
            return FileVisitResult.CONTINUE;
        }
    
        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) { 
            dysplayPath(file, " - can't access to path (input/output error)");     
            return FileVisitResult.CONTINUE;
        }
    }

    @Test
    @Disabled
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
    @Disabled
    void bufferedReaderTest() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(PRINT_WRITER_FILE));
        assertEquals("Hello", reader.readLine());
        reader.close();
    }

    @Test 
    void printingDirectoryTest() throws IOException {
        printDirectoryContent("C:\\Windows\\appcompat", 4); 
    }

    private void printDirectoryContent(String pathString, int depth) throws IOException{
        Path path = Path.of(pathString);
        Files.walkFileTree(path, EnumSet.of(FileVisitOption.FOLLOW_LINKS), depth, new MyFileVisitor(path));
    } 
}
