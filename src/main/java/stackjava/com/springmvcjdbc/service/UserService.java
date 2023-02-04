package stackjava.com.springmvcjdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stackjava.com.springmvcjdbc.dao.UserDAO;
import stackjava.com.springmvcjdbc.entities.User;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public void save(User user){
        userDAO.save(user);
    }
}
