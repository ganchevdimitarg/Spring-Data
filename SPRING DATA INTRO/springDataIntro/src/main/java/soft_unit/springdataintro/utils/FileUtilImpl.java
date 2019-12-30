package soft_unit.springdataintro.utils;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileUtilImpl implements FileUtil {

    @Override
    public String[] fileText(String path) throws IOException {
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        List<String> fileLines = new ArrayList<>();

        String line = reader.readLine();

        while (line != null) {
            fileLines.add(line);

            line = reader.readLine();
        }


        return fileLines.stream().filter(f -> !f.equals("")).toArray(String[]::new);
    }
}
