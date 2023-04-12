package com.svs.stayvision.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class complainReply {
	private int replyNum;
	private int boardNum;
	private String replyText;
	private String writer;
	private String inputDate;
}
