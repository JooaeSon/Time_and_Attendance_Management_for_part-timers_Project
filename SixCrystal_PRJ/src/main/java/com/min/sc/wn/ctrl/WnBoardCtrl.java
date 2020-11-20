package com.min.sc.wn.ctrl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.min.sc.comm.dtos.BoardFileDTO;
import com.min.sc.comm.dtos.HitDTO;
import com.min.sc.comm.dtos.PagingDTO;
import com.min.sc.comm.model.IService_Comm;
import com.min.sc.user.dtos.UserInfoDTO;
import com.min.sc.user.dtos.UserLoginDTO;
import com.min.sc.wn.dtos.WNBoardDTO;
import com.min.sc.wn.model.IService_Wn;

@Controller
public class WnBoardCtrl {


	@Autowired
	private IService_Wn service;

	@Autowired
	private IService_Comm cservice;

	/**
	 * 웹 공지 게시판으로 들어갈 때 관리자와 일반회원을 분기한다.
	 * @param session
	 */
	@RequestMapping(value = "wnBoardList.do",method = RequestMethod.GET)
	public String wnboardList(HttpSession session) {
		if (session.getAttribute("user") instanceof UserInfoDTO) {
			return "redirect:uesrWnBoard.do";
		}else {
			return "redirect:adminWnBoard.do";
		}
		
	}
	
	/**
	 * 유저가 웹 공지 게시판으로 들어가는 분기
	 * @param session
	 * @param model
	 * @return auth(user)
	 */
	@RequestMapping(value = "uesrWnBoard.do", method = RequestMethod.GET)
	public String wnBoard(HttpSession session, Model model) {
		PagingDTO pdto = null;
		
		if (session.getAttribute("page")==null) {
			pdto = new PagingDTO();
		}else {
			pdto = (PagingDTO) session.getAttribute("page");
		}
		pdto.setTotal(service.wnboardListTotal());
		UserInfoDTO dto = (UserInfoDTO) session.getAttribute("user");
		List<WNBoardDTO> lists = service.wnboardListPage(pdto);
		int count =pdto.getCount();
		model.addAttribute("page", pdto);
		model.addAttribute("lists", lists);
		model.addAttribute("auth", "user");
		model.addAttribute("count",count);
		return "wn/wnBoardList";
	}
	
	/**
	 * 관리자가 웹 공지 게시판으로 들어가는 분기
	 * @param session
	 * @param model
	 * @return auth(admin)
	 */
	@RequestMapping(value = "adminWnBoard.do",method = RequestMethod.GET)
	public String adminWnBoard(HttpSession session, Model model) {
		PagingDTO pdto = null;
		
		if (session.getAttribute("page")==null) {
			pdto = new PagingDTO();
			
		}else {
			pdto = (PagingDTO) session.getAttribute("page");
		}
		pdto.setTotal(service.wnboardListTotal());
		UserLoginDTO dto = (UserLoginDTO) session.getAttribute("user");
		List<WNBoardDTO> lists = service.wnboardListPage(pdto);
		
		model.addAttribute("page", pdto);
		model.addAttribute("auth","admin");
		model.addAttribute("lists", lists);
		return "wn/wnBoardList";
	}


	/**
	 * 일반유저가 게시글 상세조회를 하는 메소드 파일리스트와 게시판으 컬럼값들을 가지고 온다.
	 * @param wn_seq
	 * @param model
	 * @param session
	 * @param hdto
	 * @return 상세글 조회 페이지
	 */
	@RequestMapping(value = "userWnBoardDetail.do", method = RequestMethod.GET)
	public String wnBoardDetail(String wn_seq, Model model, HttpSession session, HitDTO hdto) {

		UserInfoDTO udto = (UserInfoDTO) session.getAttribute("user");
		String bc="WN";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("board_code", bc);
		map.put("wn_seq", wn_seq);
		List<HitDTO> hlist = cservice.hitSelect(map);
		//조회수를 증가시키는 로직
		if (hlist.size() > 0) {
			boolean chk = false;
			String hit_id = "";
			for (int i = 0; i < hlist.size(); i++) {
				System.out.println(hlist.get(i).getHit_ikoiko().indexOf(udto.getUser_id()));
				if (hlist.get(i).getHit_ikoiko().indexOf(udto.getUser_id())  >-1) {
					chk = true;
				}
				if (chk == false) {
					if (hlist.get(i).getHit_ikoiko().length() > 1500) {
						hdto.setBoard_code(bc);
						hdto.setBoard_seq(Integer.parseInt(wn_seq));
						hdto.setHit_ikoiko(udto.getUser_id());
						cservice.hitInsert(hdto);
						service.wnBoardHitCount(Integer.parseInt(wn_seq));
					} else {
						hit_id = hlist.get(i).getHit_ikoiko() + "," + udto.getUser_id();
						Map<String, Object> hmap = new HashMap<String, Object>();
						hmap.put("hit_seq", String.valueOf(hlist.get(i).getHit_seq()));
						hmap.put("hit_ikoiko", hit_id);
						cservice.hitUpdate(hmap);
						service.wnBoardHitCount(Integer.parseInt(wn_seq));
					}
				}
			}
		} else {
			hdto.setBoard_code(bc);
			hdto.setHit_ikoiko(udto.getUser_id());
			hdto.setBoard_seq(Integer.parseInt(wn_seq));
			cservice.hitInsert(hdto);
			service.wnBoardHitCount(Integer.parseInt(wn_seq));
		}
		List<BoardFileDTO> flist = cservice.boardFileSelect(map);
		List<WNBoardDTO> list = service.wnBoardSelect(wn_seq);
		WNBoardDTO dto = list.get(0);
		model.addAttribute("flist",flist);
		model.addAttribute("wndto", dto);
		model.addAttribute("auth", "user");
		return "wn/wnBoardDetail";
	}
	
	/**
	 * 관리자가 게시글 상세조회를 하는 메소드 파일리스트와 게시판으 컬럼값들을 가지고 온다. 관리자는 조회수를 카운트 하지 않는다.
	 * @param wn_seq
	 * @param model
	 * @param session
	 * @param hdto
	 * @return 상세글 조회 페이지
	 */
	@RequestMapping(value = "adminWnBoardDetail.do", method = RequestMethod.GET)
	public String adminwnBoardDetail(String wn_seq, Model model, HttpSession session, HitDTO hdto) {
		UserLoginDTO udto = (UserLoginDTO) session.getAttribute("user");
		String bc="WN";

		
		
		
		List<WNBoardDTO> list = service.wnBoardSelect(wn_seq);
		WNBoardDTO dto = list.get(0);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("wn_seq",wn_seq);
		map.put("board_code", bc);
		List<BoardFileDTO> flist = cservice.boardFileSelect(map);
		model.addAttribute("wndto", dto);
		model.addAttribute("flist", flist);
		model.addAttribute("auth", "admin");

		

		return "wn/wnBoardDetail";
	}
	
	
	/**
	 * 글작성 form으로 들어가는 분기
	 * @return wnWriteForm.jsp
	 */
	@RequestMapping(value = "adminWnWrite.do",method = RequestMethod.GET)
	public String wnWriteForm() {
		
		
		return "wn/wnWriteForm";
	}
	
	/**
	 * 글을 작성하고 db에 게시글 데이터를 입력하는 메소드
	 * @param dto
	 * @param board_file
	 * @param bdto
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "insertWnBoard.do",method = RequestMethod.POST)
	public String insertWnBoard(WNBoardDTO dto, MultipartFile[] board_file, BoardFileDTO bdto, HttpSession session) {
		UserLoginDTO udto = (UserLoginDTO) session.getAttribute("user");
		dto.setUser_id(udto.getUser_id());
		service.wnBoardInsert(dto);
		
		System.out.println(dto.getWn_content());
		System.out.println(board_file);
		String filepath ="C:\\Users\\rlghd\\git\\Yuksu\\SixCrystal_PRJ\\src\\main\\webapp\\file";
		
		for (int i = 0; i < board_file.length; i++) {
			String fileName = board_file[i].getOriginalFilename();
			String originalFileExtension = fileName.substring(fileName.lastIndexOf("."));
			
			String storedFileName = UUID.randomUUID().toString().replaceAll("-", "") + originalFileExtension;
			
			File file = new File(filepath,storedFileName);
			
			try {
				board_file[i].transferTo(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			bdto.setBf_filename(storedFileName);
			bdto.setBoard_code("WN");
			cservice.boardFileInsert(bdto);
		}
		
		return "redirect:adminWnBoard.do";
	}
	
	/**
	 * 게시글을 삭제하는 기능
	 * @param wn_seq
	 * @return
	 */
	@RequestMapping(value = "adminDelWnBoard.do",method = RequestMethod.GET)
	public String deleteWnBoard(int wn_seq) {
		
		service.wnBoardDelete(wn_seq);
		//삭제할 때 파일도 같이 삭제하게 처리를 해야한다.
		
		return "redirect:adminWnBoard.do";
		
	}
	
	/**
	 * 게시글을 수정하는 기능
	 * @param dto
	 * @return 웹 공지 게시판으로 이동
	 */
	@RequestMapping(value = "modify.do",method = RequestMethod.POST)
	public String modifyUp(WNBoardDTO dto) {
		
		service.wnBoardUpdate(dto);
		return "redirect:adminWnBoard.do";
	}
	

}
