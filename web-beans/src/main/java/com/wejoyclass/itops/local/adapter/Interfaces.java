package com.wejoyclass.itops.local.adapter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Interfaces {
    // 监控接口协议类型
    private Integer type;
    // 不知道是啥
    private Integer main = 1;
    // 是否使用ip
    private Integer useip = 1;
    // 监控ip地址
    private String ip;
    // dns
    private String dns = "";
    // 端口
    private String port;

    @Override
    public String toString() {
        return "Interfaces{" +
                "type=" + type +
                ", main=" + main +
                ", useip=" + useip +
                ", ip='" + ip + '\'' +
                ", dns='" + dns + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
