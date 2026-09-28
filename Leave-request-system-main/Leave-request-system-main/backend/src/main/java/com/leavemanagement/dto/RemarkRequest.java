package com.leavemanagement.dto;

import lombok.*;

/**
 * DTO for admin remark when approving or rejecting a leave request.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RemarkRequest {

    private String remark;  // Admin's remark/comment
}
