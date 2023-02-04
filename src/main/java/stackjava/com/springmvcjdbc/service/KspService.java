package stackjava.com.springmvcjdbc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stackjava.com.springmvcjdbc.dao.KspDao;
import stackjava.com.springmvcjdbc.entities.Ksp;

@Service
@Transactional
public class KspService {
    @Autowired
    private KspDao kspDao;

    public void save(Ksp ksp){
        kspDao.save(ksp);
    }
}
