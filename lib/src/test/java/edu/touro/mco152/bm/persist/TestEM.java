package edu.touro.mco152.bm.persist;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestEM {
    EntityManager em;
    @BeforeEach
     void setUp(){
        em = EM.getEntityManager();
    }

    /**
     * CrossChecking(C from BICEP) that I get the same value with each get.
     */
    @Test
    void crossCheck(){
        EntityManager em2 = EM.getEntityManager();

        assertEquals(em, em2);
    }
    @Test
    void getEntityManager() {
        assertNotNull(em);
    }


}