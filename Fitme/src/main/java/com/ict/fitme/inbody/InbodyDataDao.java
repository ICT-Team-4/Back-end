package com.ict.fitme.inbody;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InbodyDataDao {

    private final JdbcTemplate jdbcTemplate;

    public InbodyDataDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JSONArray getInbodyData() {
        String sql = "SELECT ACCOUNT_NO, POST_DATE, HEIGHT, WEIGHT, SKELETAL_MUSCLE, BODY_FAT_MASS, BODY_FAT_PERCENTAGE, ABDOMINAL_FAT_RATE, BASAL_METABOLIC_RATE FROM INBODY";
        List<JSONObject> list = jdbcTemplate.query(sql, new InbodyDataRowMapper());
        return new JSONArray(list);
    }

    public JSONArray getInbodyDataByAccountNo(String accountNo) {
        String sql = "SELECT ACCOUNT_NO, POST_DATE, HEIGHT, WEIGHT, SKELETAL_MUSCLE, BODY_FAT_MASS, BODY_FAT_PERCENTAGE, ABDOMINAL_FAT_RATE, BASAL_METABOLIC_RATE FROM INBODY WHERE ACCOUNT_NO = ?";
        List<JSONObject> list = jdbcTemplate.query(sql, new InbodyDataRowMapper(), accountNo);
        return new JSONArray(list);
    }

    private static class InbodyDataRowMapper implements RowMapper<JSONObject> {
        @Override
        public JSONObject mapRow(ResultSet rs, int rowNum) throws SQLException {
            JSONObject obj = new JSONObject();
            obj.put("ACCOUNT_NO", rs.getString("ACCOUNT_NO"));
            obj.put("POST_DATE", rs.getDate("POST_DATE"));
            obj.put("HEIGHT", rs.getFloat("HEIGHT"));
            obj.put("WEIGHT", rs.getFloat("WEIGHT"));
            obj.put("SKELETAL_MUSCLE", rs.getFloat("SKELETAL_MUSCLE"));
            obj.put("BODY_FAT_MASS", rs.getFloat("BODY_FAT_MASS"));
            obj.put("BODY_FAT_PERCENTAGE", rs.getFloat("BODY_FAT_PERCENTAGE"));
            obj.put("ABDOMINAL_FAT_RATE", rs.getFloat("ABDOMINAL_FAT_RATE"));
            obj.put("BASAL_METABOLIC_RATE", rs.getFloat("BASAL_METABOLIC_RATE"));
            return obj;
        }
    }
}
