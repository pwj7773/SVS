package com.svs.stayvision.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.svs.stayvision.util.FileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FileController {
	@Value("${spring.servlet.multipart.location}")
	private String uploadPath; // 위의 속성 값을 좌측 멤버 변수에 저장
	
	@PostMapping("/summerimages")
	@ResponseBody
	public String summerimages(@RequestParam MultipartFile file) {
		log.debug("File : {}", file.getOriginalFilename());
		String savedFileName = FileService.saveFile(file, uploadPath);
		log.debug("savedFileName : {}", savedFileName);
		

		return "/image/"+savedFileName;
	}
	
	@GetMapping("image/{filename:.+}")
	public ResponseEntity<Resource> getImage(@PathVariable String filename) throws MalformedURLException, IOException {
	    Path path = Paths.get("C:/upload/" + filename);
	    log.debug("{}",path);
	    UrlResource resource = new UrlResource(path.toUri());
	    log.debug("{}",resource);
	    return ResponseEntity.ok()
	            .body(resource);
	}
}
