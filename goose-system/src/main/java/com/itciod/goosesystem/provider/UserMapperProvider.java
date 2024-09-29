package com.itciod.goosesystem.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class UserMapperProvider {

    /**
     * 批量查询
     * @param ids 用户 ID 列表
     * @return SQL 语句
     */
    public String batchSelect(@Param("ids")List<Integer> ids) {
        return new SQL() {{
            SELECT("*");
            FROM("goose_user_table");
            WHERE("user_id IN " + generateInClause(ids));
        }}.toString();
    }

    /**
     * 批量删除
     * @param ids 用户 ID 列表
     * @return SQL 语句
     */
    public String batchDelete(@Param("ids")List<Integer> ids) {
        return new SQL() {{
            DELETE_FROM("goose_user_table");
            WHERE("user_id IN " + generateInClause(ids));
        }}.toString();
    }

/**
 * 生成一个 IN 子句字符串，用于 SQL 查询。
 * @param ids 用户 ID 列表
 * @return 生成的 IN 子句字符串
 */
private String generateInClause(@Param("ids")List<Integer> ids) {
    StringBuilder inClause = new StringBuilder("(");

    // 遍历用户 ID 列表
    for (int i = 0; i < ids.size(); i++) {
        // 如果不是第一个元素，则添加逗号和空格
        if (i > 0) {
            inClause.append(", ");
        }

        // 添加当前用户 ID，使用 #{...} 格式，以便 MyBatis 动态解析
        inClause.append("#{ids[").append(i).append("]}");
    }

    // 添加右括号，完成 IN 子句
    inClause.append(")");

    // 返回生成的 IN 子句字符串
    return inClause.toString();
}

}