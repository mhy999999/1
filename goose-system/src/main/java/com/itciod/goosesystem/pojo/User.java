package com.itciod.goosesystem.pojo;

import com.itciod.goosesystem.pojo.Enum.Androidstaue;
import com.itciod.goosesystem.pojo.Enum.Identity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer userId;
    private String username;
    private String password;
    private Identity identity;
    private Androidstaue androidstaue;
    private String androidkey;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", identity=" + (identity != null ? identity.getDescription() : "null") +
                ", androidstaue=" + (androidstaue != null ? androidstaue.getDescription() : "null") +
                ", key='" + androidkey + '\'' +
                '}';
    }
}
