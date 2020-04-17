package com.wejoyclass.itops.local.adapter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Macros {
    // 宏名称
    private String macro;
    // 宏值
    private String value;

    @Override
    public String toString() {
        return "Macros{" +
                "macro='" + macro + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
