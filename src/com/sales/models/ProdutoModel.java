package com.sales.models;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.sales.entities.Produto;

public class ProdutoModel {

  private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public List<Produto> findAll() {
    List<Produto> produtos = null;
    Session session = null;
    Transaction transaction = null;
    try {
      session = sessionFactory.openSession();
      transaction = session.beginTransaction();
      org.hibernate.query.Query query = session.createQuery("from Produto");
      produtos = query.list();
      transaction.commit();
    }
    catch (Exception e) {
      produtos = null;
      if (transaction != null) {
        transaction.rollback();
      }
    }
    finally {
      session.close();
    }
    return produtos;
  }

  @SuppressWarnings({ "rawtypes" })
  public Produto find(int id) {
    Produto produto = null;
    Session session = null;
    Transaction transaction = null;
    try {
      session = sessionFactory.openSession();
      transaction = session.beginTransaction();
      org.hibernate.query.Query query = session.createQuery("from produto where id = :id");
      query.setParameter("id", id);
      produto = (Produto) query.uniqueResult();
      transaction.commit();
    }
    catch (Exception e) {
      produto = null;
      if (transaction != null) {
        transaction.rollback();
      }
    }
    finally {
      session.close();
    }
    return produto;
  }

  public void saveOrUpdate(Produto produto) {

    Session session = null;
    try {
      // abre a sessao
      session = sessionFactory.openSession();
      // inicia a transacao
      session.beginTransaction();
      // salva na sessao o objeto
      session.saveOrUpdate(produto);
      // executa o commit
      session.getTransaction().commit();
    }
    catch (HibernateException hex) {
      if (session != null && session.getTransaction().isActive()) {
        session.getTransaction().rollback();
      }
      throw hex;
    }
    finally {
      if (session != null && session.isOpen()) {
        session.close();
      }
    }
  }

  // deletar
  public void deletar(Produto produto) {
    Session session = null;
    try {
      // abre a sessao
      session = sessionFactory.openSession();
      // inicia a transacao
      session.beginTransaction();
      // Produto prod = (Produto)session.load(Produto.class, 2);
      // salva na sessao o objeto
      session.delete(produto);
      // executa o commit
      session.getTransaction().commit();
    }
    catch (HibernateException hex) {
      if (session != null && session.getTransaction().isActive()) {
        session.getTransaction().rollback();
      }
      throw hex;
    }
    finally {
      if (session != null && session.isOpen()) {
        session.close();
      }
    }
  }
}