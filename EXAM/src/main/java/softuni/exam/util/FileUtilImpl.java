package softuni.exam.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileUtilImpl implements FileUtil {

    @Override
    public String readFile(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(filePath));

        return String.join("\n", lines);
    }
}
