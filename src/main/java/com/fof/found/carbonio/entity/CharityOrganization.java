package com.fof.found.carbonio.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

@Data
@Document(indexName = "charityorg",createIndex = true)
public class CharityOrganization {
    private String id;
    private String name;
    @Field(type = Text)
    private String description;
    // help to calculate much money it needs for reducing each kilo of carbon
    private float moneyPerKilo;
    private float targetMoney;
    private float currentMoney;
}
