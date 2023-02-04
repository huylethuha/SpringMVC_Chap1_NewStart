package stackjava.com.springmvcjdbc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import stackjava.com.springmvcjdbc.entities.Ksp;

@Repository
@Transactional
public class KspDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Ksp ksp){
        String sql = "INSERT INTO ksp (email, password) VALUE(?,?)";
        jdbcTemplate.update(sql,ksp.getEmail(),ksp.getPassword());
    }
}

