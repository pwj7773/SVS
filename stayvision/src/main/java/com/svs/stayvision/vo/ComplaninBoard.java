package com.svs.stayvision.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplaninBoard {
	private int boardNum;
	private String title;
	private String content;
	private String inputDate;
	private String userId;
	private int checked;
}
