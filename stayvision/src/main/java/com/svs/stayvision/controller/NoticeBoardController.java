package com.svs.stayvision.controller;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import com.svs.stayvision.service.board.BoardService;
import com.svs.stayvision.util.FileService;
import com.svs.stayvision.util.PageNavigator;
import com.svs.stayvision.vo.Board;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@Controller
public class NoticeBoardController {
	//게시판 목록의 페이지당 글 수
	//annotation을 가져오는 곳이 lombok이 아닌것에 주! 의!
	@Value("${user.board.page}")
	int countPerPage;

	//게시판 목록의 페이지 이동 링크 수
	@Value("${user.board.group}")
	int pagePerGroup;
	
	@Value("${spring.servlet.multipart.location}")
	private String uploadPath; // 위의 속성 값을 좌측 멤버 변수에 저장
	
	@Autowired
	private BoardService bService;
	private final String REDIRECT_LIST = "redirect:/notice/boardList";
	
	//글 목록보기
	@GetMapping("notice/boardList")
	public String boardList(String id, String category, String keyword, Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
		log.debug("BoardList() 실행");
		log.debug("page : {}", page);
		PageNavigator navi = bService.getPageNavigator(pagePerGroup, countPerPage, page, category, keyword,id);
		log.debug(navi.toString());
		List<Board> board = bService.boardSelectAll(navi);
		log.debug("Board: {}",board);
		model.addAttribute("board",board);
		model.addAttribute("navi", navi);
		model.addAttribute("keyword",keyword);
		model.addAttribute("category",category);
		model.addAttribute("id",id);
		return "board/boardList";
	}
	
	//글쓰기화면 보여주기
	@GetMapping("notice/boardWrite")
	public String boardWrite(String id, Model model) {
		model.addAttribute("id",id);
		return "board/boardWrite";
	}
	
	//글쓰기
	@PostMapping("notice/boardWrite")
	public String boardWrite(Board board,@AuthenticationPrincipal UserDetails user, @RequestParam MultipartFile file) {
		log.debug("boardWrite() 실행");
		board.setUserId(user.getUsername());
		log.debug("File : {}",file.getOriginalFilename());
		//file이 있으면 저장하고 아니면 건너뛰자...
		if(file.isEmpty() == false) { // 파일이 비어있지 않다면
			// FileService 사용해서 파일 저장
			String savedFileName =  FileService.saveFile(file, uploadPath);
			log.debug("savedFileName : {}", savedFileName);
			
			// Board 객체에 원래 파일 이름과 서버에 저장된 파일 이름 설정
			board.setOriginalFile(file.getOriginalFilename());
			board.setSavedFile(savedFileName);
		}
		log.debug("Board: {}",board);
		bService.boardWirte(board);
		return REDIRECT_LIST+"?id="+board.getBoardType();
	}
	
	//글보기
	@GetMapping("notice/boardRead")
	public String boardRead(int boardNum, Model model) {
		log.debug("boardRead() 실행");
		bService.addBoardViewCount(boardNum);
		Board board = bService.boardSelect(boardNum);
		log.debug("Board: {}",board);
		model.addAttribute(board);
		return "board/boardRead";
	}
	//글 삭제
	@GetMapping("notice/boardDelete")
	public String boardDelete(int boardNum) {
		log.debug("boardDelete() 실행");
		String id = bService.boardSelect(boardNum).getBoardType();
		bService.boardDelete(boardNum);
		return REDIRECT_LIST+"?id="+id;
	}
	// 수정화면 보여주기
	@GetMapping("notice/boardUpdate")
	public String boardUpdate(int boardNum, Model model) {
		log.debug("boardUpdate() 실행");
		Board board = bService.boardSelect(boardNum);
		log.debug("Board: {}",board);
		model.addAttribute(board);
		return "board/boardUpdate";
	}
	
	// 글 수정
	@PostMapping("notice/boardUpdate")
	public String boardUpdate(Board board,@RequestParam MultipartFile file) {
		log.debug("boardUpdate() 실행");
		log.debug("File : {}", file.getOriginalFilename());
		// file이 있으면 저장하고 아니면 건너뛰자...
		if (file.isEmpty() == false) { // 파일이 비어있지 않다면
			// FileService 사용해서 파일 저장
			String savedFileName = FileService.saveFile(file, uploadPath);
			log.debug("savedFileName : {}", savedFileName);

			// Board 객체에 원래 파일 이름과 서버에 저장된 파일 이름 설정
			board.setOriginalFile(file.getOriginalFilename());
			board.setSavedFile(savedFileName);
		}
		log.debug("Board : {}", board);
		bService.boardUpdate(board);
		return REDIRECT_LIST + "?id=" + board.getBoardType();

	}
	
	// 파일 다운로드 받기
	@GetMapping("notice/download")
	public String downloadFile(int boardNum, HttpServletResponse response) {
		log.debug("BoardNum : {}",boardNum);
		// 글 정보 조회
		Board board = bService.boardSelect(boardNum);
		
		// 원래 파일
		String originalFile = board.getOriginalFile();
		
		// 응답 객체 준비
		try {
			response.setHeader("Content-Disposition",
								"attachment;filename=" + URLEncoder.encode(originalFile,"UTF-8"));
		} catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		// 저장된 파일 가져오기
		String fullPath = uploadPath + "/" + board.getSavedFile();
		
		// 파일을 읽을 파일 스트림하고 클라이언트에 전달할 출력스트림
		FileInputStream fis = null;
		ServletOutputStream sos = null;
		
		try {
			// 서버에 저장된 파일 주소에 있는 파일을 입력스트림에 읽어 옴
			fis =  new FileInputStream(fullPath);
			// 응답 객체의 출력스트림 설정
			sos = response.getOutputStream();
			
			// Spring이 제공하는 파일관련 유틸 사용해서 입력 -> 출력 스트림으로 파일 이동
			FileCopyUtils.copy(fis, sos);
			
			// 사용이 끝난 스트림은 닫는다
			fis.close();
			sos.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return "redirect:/list"; // 얘는 동작안함
	}
	
	@GetMapping("notice/display")
	public ResponseEntity<Resource> display(int boardNum){
		log.debug("display()");
		log.debug("boardNum : {}",boardNum);
		
		Board board = bService.boardSelect(boardNum);
		
		log.debug("Board : {}",board);
		// 저장된 파일 가져오기
		String fullPath = uploadPath + "/" + board.getSavedFile();
		
		Resource resource = new FileSystemResource(fullPath);
		
		// 리소스가 없으면 NOT_FIND error 발생
		if(!resource.exists()) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		
		HttpHeaders header = new HttpHeaders();
		
		Path filePath = null;
		
		try {
			filePath = Paths.get(fullPath);
			header.add("Content-type", Files.probeContentType(filePath));
		} catch(IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource,header,HttpStatus.OK);
	}
}
