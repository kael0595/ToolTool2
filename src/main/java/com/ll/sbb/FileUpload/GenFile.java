//package com.ll.sbb.FileUpload;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;
//import lombok.*;
//import lombok.experimental.SuperBuilder;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedDate;
//
//import java.time.LocalDateTime;
//
//import static jakarta.persistence.GenerationType.IDENTITY;
//
//@Entity
//@Setter
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//@SuperBuilder
//@ToString(callSuper = true)
//public class GenFile {
//    private String relTypeCode;
//    private int relId;
//    private String typeCode;
//    private String type2Code;
//    private String fileExtTypeCode;
//    private String fileExtType2Code;
//    private int fileSize;
//    private int fileNo;
//    private String fileExt;
//    private String fileDir;
//    private String originFileName;
//    @Id
//    @GeneratedValue(strategy = IDENTITY)
//    private Long id;
//    @CreatedDate
//    private LocalDateTime createDate;
//    @LastModifiedDate
//    private LocalDateTime modifyDate;
//
//    @JsonIgnore
//    public String getFilePath() {
//        return GenFileService.GEN_FILE_DIR_PATH + getBaseFileUri();
//    }
//
//    @JsonIgnore
//    public String getBaseFileUri() {
//        return "/" + relTypeCode + "/" + fileDir + "/" + getFileName();
//    }
//
//    public String getFileName() {
//        return getId() + "." + fileExt;
//    }
//}