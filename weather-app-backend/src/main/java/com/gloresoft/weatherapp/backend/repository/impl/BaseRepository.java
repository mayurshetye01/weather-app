package com.gloresoft.weatherapp.backend.repository.impl;

import com.gloresoft.weatherapp.backend.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Slf4j
public abstract class BaseRepository {

    public void save(Object entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            log.info("Entry saved successfully");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            log.error("Exception while saving entity. Transaction rolled back", e);
            throw new RuntimeException(e);
        }
    }

    public void saveOrUpdate(Object entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(entity);
            transaction.commit();
            log.info("Entry saved/updated successfully");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            log.error("Exception while save/update of entity. Transaction rolled back", e);
            throw new RuntimeException(e);
        }
    }

    public void update(Object entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
            log.info("Entry updated successfully");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            log.error("Exception while updating entity. Transaction rolled back", e);
            throw new RuntimeException(e);
        }
    }

    public void delete(Object entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
            log.info("Entry deleted successfully");
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            log.error("Exception while deleting entity. Transaction rolled back", e);
            throw new RuntimeException(e);
        }
    }
}
