package stackjava.com.springmvcjdbc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import stackjava.com.springmvcjdbc.entities.User;
@Repository
@Transactional
public class UserDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void save(User user){
        String sql = "INSERT INTO user (username, email) VALUE(?,?)";
        jdbcTemplate.update(sql,user.getUsername(),user.getEmail());
    }

}
