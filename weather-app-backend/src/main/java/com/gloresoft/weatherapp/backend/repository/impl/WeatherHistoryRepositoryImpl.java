package com.gloresoft.weatherapp.backend.repository.impl;

import com.gloresoft.weatherapp.backend.entities.HistoryId;
import com.gloresoft.weatherapp.backend.entities.User;
import com.gloresoft.weatherapp.backend.entities.WeatherHistory;
import com.gloresoft.weatherapp.backend.repository.WeatherHistoryRepository;
import com.gloresoft.weatherapp.backend.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
@Slf4j
public class WeatherHistoryRepositoryImpl extends BaseRepository implements WeatherHistoryRepository {

    @Override
    public WeatherHistory findByCity(String city, User user) {
        HistoryId historyId = new HistoryId(city, user);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.byId(WeatherHistory.class).load(historyId);
        }
    }

    @Override
    public List<WeatherHistory> getAllRecords() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<WeatherHistory> cq = cb.createQuery(WeatherHistory.class);
            Root<WeatherHistory> rootEntry = cq.from(WeatherHistory.class);
            CriteriaQuery<WeatherHistory> all = cq.select(rootEntry);

            Query<WeatherHistory> allQuery = session.createQuery(all);
            return allQuery.getResultList();
        } catch (Exception e) {
            log.error("Exception while fetching all records", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<WeatherHistory> getAllRecordsForUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<WeatherHistory> cq = cb.createQuery(WeatherHistory.class);
            Root<WeatherHistory> rootEntry = cq.from(WeatherHistory.class);
            Path<User> userEntry = rootEntry.get("user");
            cq.select(rootEntry);
            cq.where(cb.equal(userEntry.get("username"), user.getUsername()));

            Query<WeatherHistory> query = session.createQuery(cq);
            return query.getResultList();
        } catch (Exception e) {
            log.error("Exception while fetching all records", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAllForUser(List<WeatherHistory> entities) {
        if (entities == null) {
            log.warn("Entity list is empty! No entries will be deleted");
            return;
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            entities.forEach(entity -> session.delete(entity));
            transaction.commit();
            log.debug("All entries deleted successfully");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            log.error("Exception while bulk deleting entities. Transaction rolled back", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveOrUpdate(WeatherHistory weatherHistory) {
        super.saveOrUpdate(weatherHistory);
    }

    @Override
    public void update(WeatherHistory weatherHistory) {
        super.update(weatherHistory);
    }

    @Override
    public void delete(WeatherHistory weatherHistory) {
        super.delete(weatherHistory);
    }

}
