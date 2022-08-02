package com.proyecto.bootcamp.Integration.DAO.Repositories;

import com.proyecto.bootcamp.DAO.Models.Materia;
import com.proyecto.bootcamp.DAO.Repositories.CursoRepository;
import com.proyecto.bootcamp.DAO.Repositories.MateriaRepository;
import com.proyecto.bootcamp.Integration.IT.PostgresContainerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class MateriaRepositoryTest extends PostgresContainerTest {

    @Autowired
    MateriaRepository materiaRepository;

    @Autowired
    CursoRepository cursoRepository;

    Materia materiaTest = new Materia();
    List<Materia> materiaList = new ArrayList<Materia>();

    @BeforeEach
    void setUp() {
        initDataTest();
    }

    private void initDataTest() {
        materiaTest.setDia("lunes");
        materiaTest.setFechafin(Date.valueOf("2023-03-03"));
        materiaTest.setFechainicio(Date.valueOf("2020-03-03"));
        materiaTest.setHora(8);
        
        cursoRepository.findAllPaginated(1, 0).forEach(curso -> {
            materiaTest.setCurso(curso.getId());
        });

        for (int i = 0; i < 3; i++) {
            materiaList.add(materiaTest);
        }
    }

    @Test
    void save_ReturnId_True(){
        Materia save = materiaRepository.save(materiaTest);
        assertTrue(save.getId() != null);
        assertTrue(materiaRepository.existsById(save.getId()));
}

    @Test
    void saveAll_ReturnIds_True_And_exist(){
        Iterable<Materia> returned = materiaRepository.saveAll(materiaList);
        assertTrue(
            StreamSupport
                    .stream(returned.spliterator(), true)
                    .allMatch(materia->materia.getId() != null)
        );
        
        assertTrue(
        StreamSupport.stream(returned.spliterator(), false)
                    .allMatch(materia->materiaRepository.existsById(materia.getId()))
        );
    }

    @Test
    void findById_ReturnSameObject_True(){
        Materia saved = materiaRepository.save(materiaTest);
        Optional<Materia> found = materiaRepository.findById(saved.getId());
        assertFalse(found.isEmpty());
        assertMateria(found.get(), saved);
    }

    @Test
    void findAll_ReturnQuantitySizeEqualCount_True(){
        Iterable<Materia> returned = materiaRepository.findAll();
        assertTrue(()->returned.spliterator().getExactSizeIfKnown() == materiaRepository.count());
    }

    @Test
    void findAllById_ReturnSizeEqualCount_True(){
        Iterable<Materia> allMateria = materiaRepository.findAll();
        Iterable<UUID> ids = StreamSupport.stream(allMateria.spliterator(), true)
                                            .map(materia->materia.getId()).toList();

        Iterable<Materia> allById = materiaRepository.findAllById(ids);

        assertTrue(()->allMateria.spliterator().getExactSizeIfKnown() == allById.spliterator().getExactSizeIfKnown());
    }

    @Test
    void existsById_AfterSave_ReturnTrue(){
        Materia save = materiaRepository.save(materiaTest);
        assertTrue(()->materiaRepository.existsById(save.getId()));
    }

    @Test
    void count_ReturnEqualFindAllSize_True(){
        assertTrue((()->materiaRepository.count() == materiaRepository.findAll().spliterator().getExactSizeIfKnown()));
    }

    private void assertMateria(Materia materia,Materia compare) {
        assertAll(()->{
            assertEquals(materia.getId(), compare.getId());
            assertEquals(materia.getDia(), compare.getDia());
            assertEquals(materia.getHora(), compare.getHora());
            assertEquals(materia.getCurso(), compare.getCurso());
        });
    }

    @Test
    void findAllPaginated_Return3Page1_true(){
        assertTrue((()->materiaRepository.findAllPaginated(3, 0).spliterator().getExactSizeIfKnown() == 3));
    }

    @Test
    void update_ReturnModifiedObject_true(){
        Materia save = materiaRepository.save(materiaTest);
        save.setDia("martes");
        save.setHora(11);

        Materia updated = materiaRepository.update(save);
        assertEquals(updated.getHora(), save.getHora());
        assertEquals(updated.getDia(), save.getDia());
    }

    @Test
    void delete_ReturnEmptyOptional_AfterDelete_True(){
        Materia save = materiaRepository.save(materiaTest);
        assertTrue(()->materiaRepository.findById(save.getId()).isPresent());
        materiaRepository.delete(save);
        assertFalse(()->materiaRepository.findById(save.getId()).isPresent());
    }

    @Test
    void deleteById_ReturnEmptyOptional_AfterDelete_True(){
        Materia save = materiaRepository.save(materiaTest);
        assertTrue(()->materiaRepository.findById(save.getId()).isPresent());
        materiaRepository.deleteById(save.getId());
        assertFalse(()->materiaRepository.findById(save.getId()).isPresent());
    }

    @Test
    void ShouldreturnEmptyCursos_AfterSaveAndDeleteListIds() {
        Iterable<Materia> returned = materiaRepository.saveAll(materiaList);
        List<UUID> listIds =   StreamSupport.stream(returned.spliterator(), true)
                                            .map(materia->materia.getId()).toList();

        listIds.forEach(id->{
            assertFalse(()->materiaRepository.findById(id).isEmpty());
        });

        materiaRepository.deleteAllById(listIds);

        listIds.forEach(id->{
            assertTrue(()->cursoRepository.findById(id).isEmpty());
        });
    }

    @Test
    void ShouldreturnEmptyCursos_AfterSaveAndDeleteList(){
        Iterable<Materia> returned = materiaRepository.saveAll(materiaList);
        
        returned.forEach(materia->{
            assertFalse(()->materiaRepository.findById(materia.getId()).isEmpty());
        });

        materiaRepository.deleteAll(returned);
        returned.forEach(materia->{
            assertTrue(()->materiaRepository.findById(materia.getId()).isEmpty());
        });
    }

    @Test
    void deleteAll_ReturnCountEqual0_True(){
        materiaRepository.deleteAll();
        assertTrue(()->materiaRepository.count() == 0);
    }
}