package com.svs.stayvision.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Business {
	private int businessNum;
	private String businessName;
	private String businessAddress;
	private int totalRoom;
	private String userId;
	private String phone;
	private String name;
}
