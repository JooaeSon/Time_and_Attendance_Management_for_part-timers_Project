package com.min.sc.comm.dtos;

public class BoardFileDTO {

	private int bf_seq;
	private String board_code;
	private String bf_filename;
	private int board_seq;
	public BoardFileDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BoardFileDTO(int bf_seq, String board_code, String bf_filename, int board_seq) {
		super();
		this.bf_seq = bf_seq;
		this.board_code = board_code;
		this.bf_filename = bf_filename;
		this.board_seq = board_seq;
	}
	public int getBf_seq() {
		return bf_seq;
	}
	public void setBf_seq(int bf_seq) {
		this.bf_seq = bf_seq;
	}
	public String getBoard_code() {
		return board_code;
	}
	public void setBoard_code(String board_code) {
		this.board_code = board_code;
	}
	public String getBf_filename() {
		return bf_filename;
	}
	public void setBf_filename(String bf_filename) {
		this.bf_filename = bf_filename;
	}
	public int getBoard_seq() {
		return board_seq;
	}
	public void setBoard_seq(int board_seq) {
		this.board_seq = board_seq;
	}
	@Override
	public String toString() {
		return "BoardFileDto [bf_seq=" + bf_seq + ", board_code=" + board_code + ", bf_filename=" + bf_filename
				+ ", board_seq=" + board_seq + "]";
	}

}
