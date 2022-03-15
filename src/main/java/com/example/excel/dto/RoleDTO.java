package com.example.excel.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleDTO {
    private String id;
    private String name;
    private String code;
}
