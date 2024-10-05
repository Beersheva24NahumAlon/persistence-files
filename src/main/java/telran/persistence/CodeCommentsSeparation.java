package telran.persistence;
//Test comment
import java.io.*;

public class CodeCommentsSeparation {

    //Record contains streams wich using in the application
    record Streams(BufferedReader reader, PrintWriter writerCode, PrintWriter writerCommnets) {
    }

    //Main method of application
    public static void main(String[] args) throws Exception {
        try {
            Streams streams = openStreams(args);
            workWithStreams(streams);
            closeStreams(streams);
        } catch (RuntimeException e) {
            e.printStackTrace();    
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Method is closing opened streams
    private static void closeStreams(Streams streams) throws Exception {
        streams.reader.close();
        streams.writerCode.close();
        streams.writerCommnets.close();
    }

    //Method is parsing arguments nad opening streams for reading and writing files
    private static Streams openStreams(String[] args) throws Exception {
        if (args.length != 3 ) {
            throw new Exception("Must be 3 parametrs!");
        }
        return new Streams(
                new BufferedReader(new FileReader(args[0])), 
                new PrintWriter(args[1]),
                new PrintWriter(args[2])
        );
    }

    //Method for separating code and comments
    private static void workWithStreams(Streams streams) throws Exception {
        String currentLine = "";
        while (streams.reader.ready()) {
            currentLine = streams.reader.readLine();
            if (currentLine.trim().length() >= 2 
                    && currentLine.trim().substring(0, 2).equals("//")) {
                streams.writerCommnets.println(currentLine);
            } else {
                streams.writerCode.println(currentLine);
            }
        }
    }
}
