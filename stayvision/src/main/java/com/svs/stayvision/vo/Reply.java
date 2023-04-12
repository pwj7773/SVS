package com.svs.stayvision.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
	private int replyNum;
	private String replyText;
	private int boardNum;
	private String userId;
	private String inputDate;

}
