package com.wejoyclass.itops.local.adapter;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class HostParam {
    // 主机名
    private String hostName;
    // 主机别名
    private String name;
    // ipmi认证方式的用户名
    private String ipmiUsername;

    @Override
    public String toString() {
        return "HostParam{" +
                "hostName='" + hostName + '\'' +
                ", name='" + name + '\'' +
                ", ipmiUsername='" + ipmiUsername + '\'' +
                ", ipmiPassword='" + ipmiPassword + '\'' +
                ", interfacesList=" + interfacesList +
                ", templateList=" + templateList +
                ", templateOldList=" + templateOldList +
                ", macrosList=" + macrosList +
                '}';
    }

    // ipmi认证方式的密码
    private String ipmiPassword;
    // 接口list
    private List<Interfaces> interfacesList = new ArrayList<>();
    // 模板id
    private List<String> templateList;
    // 老模板id
    private List<String> templateOldList;
    // 宏list
    private List<Macros> macrosList = new ArrayList<>();
}
