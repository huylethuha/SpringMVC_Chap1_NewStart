package stackjava.com.springmvcjdbc.entities;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KspMapper implements RowMapper<Ksp> {

    @Override
    public Ksp mapRow(ResultSet resultSet, int i) throws SQLException {
        Ksp ksp = new Ksp();
        ksp.setId(resultSet.getInt("id"));
        ksp.setEmail(resultSet.getString("email"));
        ksp.setPassword(resultSet.getString("password"));
        return ksp;
    }
}
