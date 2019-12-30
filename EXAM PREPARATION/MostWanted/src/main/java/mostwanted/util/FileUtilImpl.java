package mostwanted.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileUtilImpl implements FileUtil {

    @Override
    public String readFile(String filePath) throws IOException {
//
//        File file = new File(filePath);
//
//        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
//
//        StringBuilder sb = new StringBuilder();
//
//        String line;
//
//        while ((line  = reader.readLine())  != null){
//            sb.append(line).append(System.lineSeparator());
//        }
//
//        return sb.toString().trim();

        List<String> lines = Files.readAllLines(Path.of(filePath));

        return String.join("\n", lines);
    }
}