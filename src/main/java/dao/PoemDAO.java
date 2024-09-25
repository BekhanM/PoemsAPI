package dao;

import entities.Poem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;

import java.util.List;


public class PoemDAO {
    EntityManagerFactory emf;

    public PoemDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void create(Poem poem) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(poem);
            em.getTransaction().commit();
        }
    }

    public List<Poem> getAllPoems() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Poem> query = em.createNamedQuery("Poem.getAllPoems", Poem.class);
            List<Poem> poemList = query.getResultList();
            System.out.println(poemList);
            return poemList;
        }
    }

    public Poem findPoemById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Poem foundPoem = em.find(Poem.class, id);
            System.out.println(foundPoem);
            return foundPoem;
        }
    }

    public Poem updatePoem(Poem poem) {
        try (EntityManager em = emf.createEntityManager()) {
            Poem found = em.find(Poem.class, poem.getId());
            if (found == null) {
                throw new EntityNotFoundException("No entity with that id");
            }

            em.getTransaction().begin();
            if (poem.getPoem() != null) {
                found.setPoem(poem.getPoem());
            }
            if (poem.getType() != null) {
                found.setType(poem.getType());
            }
            if (poem.getAuthor() != null) {
                found.setAuthor(poem.getAuthor());
            }
            if (poem.getTitle() != null) {
                found.setTitle(poem.getTitle());
            }

            em.getTransaction().commit();
            return found;
        }
    }

    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Poem killPoem = findPoemById(id);
            em.remove(killPoem);
            em.getTransaction().commit();
        }
    }

}
