package com.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	private void saveUploadedFile(MultipartFile file) throws IOException {
	    if (!file.isEmpty()) {
	        byte[] bytes = file.getBytes();
	        Path path = Paths.get("resources//images//" + file.getOriginalFilename());
	        Files.write(path, bytes);
	    }
	}

}
