package com.innowise.corelib.util;

import lombok.experimental.UtilityClass;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@UtilityClass
public class FileUtil {

    public static Path store(String directory, String file, byte[] content) throws IOException {
        Path directoryPath = Paths.get(directory);
        Files.createDirectories(directoryPath);
        Path filePath = directoryPath.resolve(file);

        return Files.write(filePath, content);
    }

    public static Path storeDoc(String directory, String file, String content) throws IOException {
        Path directoryPath = Paths.get(directory);
        Files.createDirectories(directoryPath);
        Path filePath = directoryPath.resolve(file);

        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.createRun().setText(content);

        try (FileOutputStream outputStream = new FileOutputStream(filePath.toFile())) {
            document.write(outputStream);
        }
        document.close();

        return filePath;
    }
}
