package com.svs.stayvision.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	private int boardNum;
	private String userId;
	private String title;
	private String content;
	private String inputDate;
	private int viewCount;
	private int recommend;
	private int replyCount;
	private String boardType;
	private String originalfile; 
	private String savedfile; 
}
