package com.emucoo.dto.modules.task;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Review {
	private Integer reviewResult;
	private Long reviewID;
	private String reviewOpinion;
	private Long reviewTime;
	private Long auditorID;
	private String auditorName;
	private String auditorHeadUrl;
	private List<ImageUrl> reviewImgArr = new ArrayList<ImageUrl>();
}
