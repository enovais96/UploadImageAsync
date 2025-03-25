package com.bix.upload.dto;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

public record UploadImageDTO(MultipartFile file, BigDecimal percentNewSize, boolean filterImage) {
}
