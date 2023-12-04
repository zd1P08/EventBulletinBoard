package com.example.app.domain;

import com.example.app.common.validation.DateRange;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@DateRange(start = "eventStartDate", end = "eventEndDate", message = "終了時間は開始時間よりも後でなければなりません")
@Data
public class Event {

	private Integer eventId;
	private Integer userId;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Future(message = "過去の時間は選択できません")
	private Date eventStartDate;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Future(message = "過去の時間は選択できません")
	private Date eventEndDate;
	@NotNull
	private Integer locationId;
	@NotNull
	private Integer amount;
	@NotNull
	private Integer capacity;
	@NotNull
	private Integer levelId;
	@NotBlank
	private String content;
	private String levelName;
	private String userName;
	private String locationName;
	private Integer joinedUserNum;
}
