package com.fof.found.carbonio.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "tip")
public class Tip {
    private String id;
    private String content;
    private String catagory;
}
