package com.gloresoft.weatherapp.backend.repository.impl;

import com.gloresoft.weatherapp.backend.entities.User;
import com.gloresoft.weatherapp.backend.repository.UserRepository;
import com.gloresoft.weatherapp.backend.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserRepositoryImpl extends BaseRepository implements UserRepository {

    @Override
    public void save(User user) {
        super.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.byId(User.class).load(username);
        }
    }
}
