package com.castile.samples.api;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Part;
import jakarta.ws.rs.NotFoundException;
import org.apache.servicecomb.foundation.common.part.FilePart;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static java.io.File.createTempFile;

/**
 * @author castile
 * @date 2025-02-16 下午9:48
 */
//@RestSchema(schemaId = "FileService")
//@RequestMapping("/file")
public class FileController {
    private static final String BASE_PATH = "/Users/castile/Documents/Code/castile-projects/servicecomb-project/cse-provider/src/main/resources/files/";
    @GetMapping("/file")
    public File getFile(String fileName) {
        File file = new File(BASE_PATH + fileName);
        if (file.exists()) {
            return file;
        }
        throw new NotFoundException("File not found: " + fileName);
    }

    @GetMapping(path = "/downloadSpringMVCExample")
    public Part downloadSpringMVCExample(String fileName) throws IOException {
        File file = new File(BASE_PATH + fileName);
        if (!file.exists()) {
            throw new NotFoundException("File not found: " + fileName);
        }
        return new FilePart(null, file)
                .setDeleteAfterFinished(true)
                .setSubmittedFileName("test.bin")
                .contentType("application/octet-stream");
    }

//    @GetMapping(path = "/inputStream")
//    @ApiResponses({
//            @ApiResponse(code = 200, response = File.class, message = ""),
//    })
//    public ResponseEntity<InputStream> download() throws IOException {
//        return ResponseEntity
//                .ok()
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=resource.txt")
//                .body(stream);
//    }

}
