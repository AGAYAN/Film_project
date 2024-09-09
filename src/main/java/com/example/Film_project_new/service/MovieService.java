package com.example.Film_project_new.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class MovieService {

    private static final String VIDEO_DIRECTORY = "src/main/resources/static";

    public ResponseEntity<byte[]> streamMovie(String filename, HttpServletRequest request) throws IOException {
        Path filePath = Paths.get(VIDEO_DIRECTORY, filename);
        File file = filePath.toFile();

        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        long fileSize = file.length();
        String rangeHeader = request.getHeader(HttpHeaders.RANGE);
        long start = 0;
        long end = fileSize - 1;

        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
            String[] ranges = rangeHeader.substring("bytes=".length()).split("-");
            try {
                start = Long.parseLong(ranges[0]);
                if (ranges.length > 1) {
                    end = Long.parseLong(ranges[1]);
                }
            } catch (NumberFormatException e) {
                return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE).body(null);
            }
        }

        if (end > fileSize - 1) {
            end = fileSize - 1;
        }

        byte[] fileBytes = readByteRange(file, start, end);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(Files.probeContentType(filePath)));
        headers.setContentLength(fileBytes.length);
        headers.add("Content-Range", "bytes " + start + "-" + end + "/" + fileSize);

        return new ResponseEntity<>(fileBytes, headers, HttpStatus.PARTIAL_CONTENT);
    }

    private byte[] readByteRange(File file, long start, long end) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        raf.seek(start);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = raf.read(buffer)) != -1 && start <= end) {
            baos.write(buffer, 0, bytesRead);
            start += bytesRead;
        }
        raf.close();
        return baos.toByteArray();
    }
}