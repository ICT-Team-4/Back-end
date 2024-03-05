package com.security.notification.dto;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Alias("NotificationDto")
public class NotificationDto {
	private String accountNo;
	private String notificationType;
	private String targetNo;
	private Date notificationDate;
	private String message;
}
