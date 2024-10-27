package com.lucas.consumerOne.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@ToString
@Getter
@Setter
public class TransactionDTO {

    private Long id;
    private String name;
    private String type;
//    private LocalDateTime localDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public LocalDateTime getLocalDateTime() {
//        return localDateTime;
//    }
//
//    public void setLocalDateTime(LocalDateTime localDateTime) {
//        this.localDateTime = localDateTime;
//    }



    @Override
    public String toString() {
        return "TransactionDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
//                ", localDateTime=" + localDateTime +
                '}';
    }
}
