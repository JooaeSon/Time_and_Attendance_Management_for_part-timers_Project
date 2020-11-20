package com.min.sc.comm.dtos;

public class HitDTO {

	private int hit_seq   ;
	private String board_code;
	private int board_seq ;
	private String hit_ikoiko;
	public HitDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getHit_seq() {
		return hit_seq;
	}
	public void setHit_seq(int hit_seq) {
		this.hit_seq = hit_seq;
	}
	public String getBoard_code() {
		return board_code;
	}
	public void setBoard_code(String board_code) {
		this.board_code = board_code;
	}
	public int getBoard_seq() {
		return board_seq;
	}
	public void setBoard_seq(int board_seq) {
		this.board_seq = board_seq;
	}
	public String getHit_ikoiko() {
		return hit_ikoiko;
	}
	public void setHit_ikoiko(String hit_ikoiko) {
		this.hit_ikoiko = hit_ikoiko;
	}
	public HitDTO(int hit_seq, String board_code, int board_seq, String hit_ikoiko) {
		super();
		this.hit_seq = hit_seq;
		this.board_code = board_code;
		this.board_seq = board_seq;
		this.hit_ikoiko = hit_ikoiko;
	}
	@Override
	public String toString() {
		return "HitDTO [hit_seq=" + hit_seq + ", board_code=" + board_code + ", board_seq=" + board_seq
				+ ", hit_ikoiko=" + hit_ikoiko + "]";
	}
	
}
