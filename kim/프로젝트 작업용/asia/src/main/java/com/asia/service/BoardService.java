package com.asia.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.asia.entity.Board;
import com.asia.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
	private final BoardRepository boardRepository;
	
	//글작성처리
    /*MultipartFile file 추가*//*예외처리*/
    public void saveWrite(Board board, MultipartFile file) throws Exception {
    	/*우리의 프로젝트경로를 담아주게 된다 - 저장할 경로를 지정*/
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        /*식별자 . 랜덤으로 이름 만들어줌*/
        UUID uuid = UUID.randomUUID();

        /*랜덤식별자_원래파일이름 = 저장될 파일이름 지정*/
        String fileName = uuid + "_" + file.getOriginalFilename();

        /*빈 껍데기 생성*/
        /*File을 생성할건데, 이름은 "name" 으로할거고, projectPath 라는 경로에 담긴다는 뜻*/
        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        /*디비에 파일 넣기*/
        board.setFilename(fileName);
        /*저장되는 경로*/
        board.setFilepath("/files/" + fileName); /*저장된파일의이름,저장된파일의경로*/
        
        /*파일 저장*/
    	boardRepository.save(board);
    }
    
    // 게시글 리스트 불러오기
    public List<Board> articleList(){
        return boardRepository.findAll(); //Board라는 class가 담긴 list를 찾아 반환
    }
    
    // 게시글 상세보기 불러오기(특정글 불러오기)
    public Board boardDetail(Long num) {
    	return boardRepository.findById(num).get();    // Long형태의 변수를 불러오기위해 위의 인자로 Long 자료형의 num을 준다. 
    }
    
    // 게시글 삭제하기(특정글 삭제)
    public void articleDelete(Long num) {
    	boardRepository.deleteById(num);
    }
    
}
