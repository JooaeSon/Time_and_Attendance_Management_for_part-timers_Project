package com.min.sc.comm.ctrl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.min.sc.comm.dtos.BoardFileDTO;
import com.min.sc.comm.model.IService_Comm;

@Controller
public class BoardFileCtrl {
	String filepath ="C:\\Users\\rlghd\\git\\Yuksu\\SixCrystal_PRJ\\src\\main\\webapp\\file";
	
	@Autowired
	public IService_Comm service;
	/**
	 * 파일을 다운로드하는 기능
	 * @param bf_seq
	 * @throws IOException
	 */
	@RequestMapping(value = "bfFileDown.do",method = RequestMethod.GET)
	public void fileDownload(String bf_seq, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bf_seq", bf_seq);
		List<BoardFileDTO> lists = service.boardFileSelect(map);
		BoardFileDTO bdto = lists.get(0);
		String filename =bdto.getBf_filename();
		
		File file = new File(filepath,filename);
		
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		String mimeType = URLConnection.guessContentTypeFromStream(inputStream);
		if (mimeType == null) {
			mimeType = "application/octec-stream";
		}
		resp.setContentType(mimeType);
		resp.setContentLength((int) file.length());
		
		resp.setHeader("Content-Disposition", String.format("attachment; fileName=%s", filename));
		
		FileCopyUtils.copy(inputStream, resp.getOutputStream());
	}

}
