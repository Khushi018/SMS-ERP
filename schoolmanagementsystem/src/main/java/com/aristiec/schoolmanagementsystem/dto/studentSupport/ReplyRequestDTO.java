package com.aristiec.schoolmanagementsystem.dto.studentSupport;

import com.aristiec.schoolmanagementsystem.constant.enums.QueryStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Data
public class ReplyRequestDTO {
private QueryStatus status;
}
