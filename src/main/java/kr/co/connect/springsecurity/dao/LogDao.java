package kr.co.connect.springsecurity.dao;

import kr.co.connect.springsecurity.dto.Log;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class LogDao {

    private NamedParameterJdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertAction;

    public LogDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("boost_log") //INSERT에 사용할 테이블 설정
                .usingGeneratedKeyColumns("id"); //boost_log.id 컬럼(PK)을 자동으로 입력하도록 설정
    }

    public Long insert(Log log) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(log);
        return insertAction.executeAndReturnKey(params).longValue(); //자동으로 생성된 INSERT문 실행후 자동으로 생성된 id(PK) 값을 리턴
    }
}