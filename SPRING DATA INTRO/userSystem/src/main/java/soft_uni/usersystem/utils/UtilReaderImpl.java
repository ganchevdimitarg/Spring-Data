package soft_uni.usersystem.utils;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class UtilReaderImpl implements UtilReader {

    @Override
    public String lines() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line = reader.readLine();

            return line;
    }
}
