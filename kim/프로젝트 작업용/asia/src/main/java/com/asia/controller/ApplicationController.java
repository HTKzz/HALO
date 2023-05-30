package com.asia.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.asia.dto.ApplicationDto;
import com.asia.dto.MainApplicationDto;
import com.asia.entity.Application;
import com.asia.entity.Attach;
import com.asia.repository.AttachRepository;
import com.asia.service.ApplicationService;
import com.asia.service.AttachService;
import com.asia.service.FileService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/board")
@Controller
@RequiredArgsConstructor
public class ApplicationController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ApplicationController.class);

	private final ApplicationService applicationService;
	
	private final AttachService attachService;
	
	private final FileService fileService;
	
	private final AttachRepository attachRepository;

	// 프로그램 신청 페이지 호출
	@GetMapping(value = "/program/apply")
	public String applicationForm(Model model, Principal principal) {
		model.addAttribute("applicationDto", new ApplicationDto());
		return "program/applicationForm";
	}

	// 프로그램 신청 및 저장
	@PostMapping(value = "/program/apply")
	public String programApply(Principal principal, @Valid ApplicationDto applicationDto, BindingResult bindingResult, Model model,
							   @RequestParam("attachFile") List<MultipartFile> attachFileList) {
		
		if (bindingResult.hasErrors()) {   // 상품 등록시 필수 값이 없다면 다시 상품 등록 페이지로 전환한다.
			return "program/applicationForm";
		}
		
		if (attachFileList.get(0).isEmpty() && applicationDto.getNum() == null) {
			model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
			return "program/applicationForm";   // 상품 등록시 첫 번째 이미지가 없다면 에러 메시지와 함께 상품등록 페이지로 전환한다.
		}   // 상품 첫번째 이미지는 메인 페이지에서 보여줄 상품 이미지를 사용하기 위해 필수 값으로 지정한다.
		
		try {
			String name = principal.getName();
			applicationService.saveApplication(applicationDto, attachFileList, name);   // 상품 저장 로직을 호출. 상품정보와 상품이미지정보를 넘긴다.
		} catch (Exception e) {
			model.addAttribute("errorMessage", "프로그램 등록 중 에러가 발생하였습니다.");
			return "program/applicationForm";
		}
		
		return "redirect:/";   // 정상적으로 등록되었다면 메인페이지로 이동한다.
		
	}
	
	// 프로그램 수정 페이지 호출
	@GetMapping(value = "/program/apply/{num}")
	public String applicationModify(@PathVariable("num") Long num, Model model) {
		LOGGER.info("받아온 파라미터 값은 : {}입니다.", num);
		try {
			ApplicationDto applicationDto = applicationService.getApplicationDtl(num);   // 조회한 프로그램 정보를 model에 담아서 뷰로 전달
			model.addAttribute("applicationDto", applicationDto);
		} catch(EntityNotFoundException e) {
			model.addAttribute("errorMessage", "존재하지 않는 프로그램입니다.");
			model.addAttribute("applicationDto", new ApplicationDto());
			return "program/applicationForm";
		}
		
		return "program/applicationForm";
	}
	
	// 프로그램 수정 후 저장
	@PostMapping(value = "/program/apply/{num}")
	public String applicationUpdate(@Valid ApplicationDto applicationDto, BindingResult bindingResult,
									@RequestParam("attachFile") List<MultipartFile> attachFileList, Model model) {
		if(bindingResult.hasErrors()) {
			return "program/applicationForm";
		}
		
		if(attachFileList.get(0).isEmpty() && applicationDto.getNum() == null) {
			model.addAttribute("errorMessage", "첫번째 프로그램 이미지는 필수 입력 값입니다.");
			return "program/applicationForm";
		}
		
		try {
			applicationService.updateApplication(applicationDto, attachFileList);
		} catch (Exception e) {
			model.addAttribute("errorMessage", "프로그램 신청 수정 중 에러가 발생하였습니다.");
			return "program/applicationForm";
		}
		return "redirect:/board/program/application/{num}";
	}
	
	
	// 프로그램 신청 리스트 호출(프로그램 신청 관리페이지 호출)
	// 오름차순(ASC), 내림차순(DESC)
	@GetMapping(value = "/program/applications")
    public String applicationManage(Model model,
    								@PageableDefault(page = 0, size = 10, sort = "num", direction = Sort.Direction.DESC)Pageable pageable,
    								String searchKeyword) {
		
		// 검색기능
        Page<Application> applications = null;
        // searchKeyword = 검색하는 단어
        if(searchKeyword == null){
        	applications = applicationService.applicationList(pageable); // 기존의 리스트보여줌
        }else{
        	applications = applicationService.applicationSearchList(searchKeyword, pageable); // 검색리스트반환
        }

        int nowPage = applications.getPageable().getPageNumber() + 1; //pageable에서 넘어온 현재페이지를 가지고올수있다 * 0부터시작하니까 +1
        int startPage = Math.max(nowPage - 4, 1); //매개변수로 들어온 두 값을 비교해서 큰값을 반환
        int endPage = Math.min(nowPage + 4, applications.getTotalPages());

        model.addAttribute("applications" , applications);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "program/applicationMng";
    }
	

	// 프로그램 신청 상세보기 호출
	@GetMapping(value = "/program/application/{num}")
	public String applicationDtl(Model model, @PathVariable("num") Long num){
		ApplicationDto applicationDto = applicationService.getApplicationDtl(num);
		model.addAttribute("application2", applicationDto);
		return "program/applicationDtl";
	}
	
	// 프로그램 신청 삭제하기 
   	@GetMapping(value="/program/delete/{num}")
	public String applicationDelete(@PathVariable Long num, Model model) throws Exception {
   		attachService.deleteAttach(num);
		applicationService.deleteApplication(num);
   		return "redirect:/board/program/applications";
	}
   	
   	// 프로그램 신청 첨부파일 다운로드
    @GetMapping("/program/download/{num}")
    public ResponseEntity<Object> download(@PathVariable("num") Long num) {
       
       Attach file1 = attachRepository.findByNum(num);
       String fileName = file1.getName();
       String path = "C:/asia/attach/" + fileName;
       
       try {
          Path filePath = Paths.get(path);
          Resource resource = new InputStreamResource(Files.newInputStream(filePath)); // 파일 resource 얻기
          
          File file = new File(path);
          
          HttpHeaders headers = new HttpHeaders();
          // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를 알려주는 헤더
          headers.setContentDisposition(ContentDisposition.builder("attachment").filename(file1.getName()).build());
             
          return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
          
       } catch(Exception e) {
          return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
       }
    }
    
    // 카테고리별 게시판 호출(공연)
    @GetMapping("/program/showlist")
    public String showListView(Model model, Long num, String programCategory,
    						   @PageableDefault(page = 0, size = 6, sort = "num", direction = Sort.Direction.DESC)Pageable pageable) {
    	
    	
            //큰카테고리에 해당하는 상품 가져오기.
    		programCategory = "공연";
//            List<Application> ShowList = applicationService.findByProgramCategory(programCategory);
//            List<Application> applicationDto = applicationService.findByProgramCategory(programCategory);
//            ApplicationDto applicationDto = applicationService.getApplicationDtl(List(num));
//            ApplicationDto applicationDto = applicationService.getShowList(num);
            Page<MainApplicationDto> showapplications = applicationService.showApplicationList(pageable, programCategory);
            
//            Page<Attach> showattachs = attachService.showAttachList(pageable, num);
//            model.addAttribute("application3", ShowList);
            
            int nowPage = showapplications.getPageable().getPageNumber() + 1; //pageable에서 넘어온 현재페이지를 가지고올수있다 * 0부터시작하니까 +1
            int startPage = Math.max(nowPage - 4, 1); //매개변수로 들어온 두 값을 비교해서 큰값을 반환
            int endPage = Math.min(nowPage + 4, showapplications.getTotalPages());
            model.addAttribute("application4", showapplications);
            
            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            
            System.out.println(showapplications.getContent().get(0));
            return "program/showList";
    	
    }
    
 // 카테고리별 게시판 호출(전시)
    @GetMapping("/program/exhibitionlist")
    public String exhibitionListView(Model model, Long num, String programCategory,
    								 @PageableDefault(page = 0, size = 6, sort = "num", direction = Sort.Direction.DESC)Pageable pageable) {
    	
    	
            //큰카테고리에 해당하는 상품 가져오기.
    		programCategory = "전시";
    		
            Page<MainApplicationDto> showapplications = applicationService.showApplicationList(pageable, programCategory);
            
            int nowPage = showapplications.getPageable().getPageNumber() + 1; //pageable에서 넘어온 현재페이지를 가지고올수있다 * 0부터시작하니까 +1
            int startPage = Math.max(nowPage - 4, 1); //매개변수로 들어온 두 값을 비교해서 큰값을 반환
            int endPage = Math.min(nowPage + 4, showapplications.getTotalPages());
            model.addAttribute("application5", showapplications);
            
            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);

            System.out.println(showapplications.getContent());
            System.out.println(nowPage);
            System.out.println(endPage);
            return "program/exhibitionList";
    	
    }
    
    // 카테고리별 게시판 호출(행사)
    @GetMapping("/program/eventlist")
    public String eventListView(Model model, Long num, String programCategory,
    							@PageableDefault(page = 0, size = 6, sort = "num", direction = Sort.Direction.DESC)Pageable pageable) {
    	
            //큰카테고리에 해당하는 상품 가져오기.
    		programCategory = "행사";
    		
            Page<MainApplicationDto> showapplications = applicationService.showApplicationList(pageable, programCategory);
            
            int nowPage = showapplications.getPageable().getPageNumber() + 1; //pageable에서 넘어온 현재페이지를 가지고올수있다 * 0부터시작하니까 +1
            int startPage = Math.max(nowPage - 4, 1); //매개변수로 들어온 두 값을 비교해서 큰값을 반환
            int endPage = Math.min(nowPage + 4, showapplications.getTotalPages());
            model.addAttribute("application6", showapplications);
            
            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            
            System.out.println(showapplications.getContent());
            System.out.println(nowPage);
            System.out.println(endPage);
            return "program/eventList";
    	
    }
    

    
}
