package com.aip.dao;

import com.aip.model.Candidate;
import com.aip.model.ResultRow;
import com.aip.model.Vote;
import com.aip.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.*;

/** Hibernate data access for candidates and votes. */
public class VoteDAO {

    public List<Candidate> candidates() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("from Candidate order by name", Candidate.class).list();
        }
    }

    public void addCandidate(String name, String party) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            s.persist(new Candidate(name, party));
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public void deleteCandidate(Long id) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            s.createQuery("delete from Vote where candidateId = :id").setParameter("id", id).executeUpdate();
            s.createQuery("delete from Candidate where id = :id").setParameter("id", id).executeUpdate();
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public boolean hasVoted(String username) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Long n = s.createQuery("select count(v) from Vote v where v.voter = :u", Long.class)
                    .setParameter("u", username).uniqueResult();
            return n != null && n > 0;
        }
    }

    /** @return true if the vote was recorded, false if the user had already voted or the candidate is invalid */
    public boolean castVote(String username, Long candidateId) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            if (s.get(Candidate.class, candidateId) == null) { tx.rollback(); return false; }
            Long n = s.createQuery("select count(v) from Vote v where v.voter = :u", Long.class)
                    .setParameter("u", username).uniqueResult();
            if (n != null && n > 0) { tx.rollback(); return false; }
            s.persist(new Vote(username, candidateId));
            tx.commit(); // UNIQUE(voter) rejects a duplicate that slipped past the check above
            return true;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            return false;
        }
    }

    /** Candidates with vote counts, highest first. */
    public List<ResultRow> results() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Map<Long, Long> counts = new HashMap<>();
            for (Object[] row : s.createQuery(
                    "select v.candidateId, count(v) from Vote v group by v.candidateId", Object[].class).list()) {
                counts.put((Long) row[0], (Long) row[1]);
            }
            List<ResultRow> out = new ArrayList<>();
            for (Candidate c : s.createQuery("from Candidate", Candidate.class).list()) {
                out.add(new ResultRow(c.getId(), c.getName(), c.getParty(), counts.getOrDefault(c.getId(), 0L)));
            }
            out.sort((a, b) -> Long.compare(b.getVotes(), a.getVotes()));
            return out;
        }
    }
}
