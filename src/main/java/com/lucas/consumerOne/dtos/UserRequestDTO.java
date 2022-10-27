package com.lucas.consumerOne.dtos;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO implements Serializable {

    private Long id;
    private String name;

}
