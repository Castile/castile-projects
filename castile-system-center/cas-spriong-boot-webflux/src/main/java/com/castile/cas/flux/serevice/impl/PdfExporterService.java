package com.castile.cas.flux.serevice.impl;

import com.castile.cas.flux.serevice.ExporterService;
import com.castile.common.extension.Extension;
import org.springframework.stereotype.Service;

/**
 * @author castile
 * @date 2024-12-12 22:54
 */
@Extension(bizName = "pdf")
@Service
public class PdfExporterService implements ExporterService {
    @Override
    public String export(String fileName) {
        return "export PDF: " + fileName;
    }
}
