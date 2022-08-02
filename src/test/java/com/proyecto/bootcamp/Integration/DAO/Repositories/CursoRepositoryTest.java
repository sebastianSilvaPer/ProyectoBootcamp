package com.proyecto.bootcamp.Integration.DAO.Repositories;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.proyecto.bootcamp.DAO.Models.Curso;
import com.proyecto.bootcamp.DAO.Repositories.CursoRepository;
import com.proyecto.bootcamp.Integration.IT.PostgresContainerTest;


@SpringBootTest
@Testcontainers
class CursoRepositoryTest extends PostgresContainerTest {
    @Autowired
    CursoRepository cursoRepository;

    Curso curso = new Curso();
    List<Curso> cursolist = new ArrayList<Curso>();

    @BeforeEach
    void setUp() {
        initDataTest();
    }

    private void initDataTest() {
        curso.setNombre("Curso de Java");
        curso.setDescripcion("Curso de Java");
        for (int i = 0; i < 5; i++) {
            cursolist.add(curso);    
        }
    }

    @AfterEach
    void tearDown() {
        if(curso.getId() != null) {
            cursoRepository.deleteById(curso.getId());
        }
    }

    @Test
    void save_ReturnId_True(){
        curso = cursoRepository.save(curso);
        assertTrue(()->curso.getId() != null);
    }

    @Test
    void saveAll_ReturnIds_True(){
        Iterable<Curso> returned = cursoRepository.saveAll((Iterable<Curso>) cursolist);
        
        StreamSupport.stream(returned.spliterator(), true)
            .allMatch(curso->curso.getId() != null);
    }

    @Test
    void findById_ReturnSameObject_True(){
        curso = cursoRepository.save(curso);
        Curso returned = cursoRepository.findById(curso.getId()).get();
        assertTrue(()->returned.getId().equals(curso.getId()));
        assertTrue(()->returned.getNombre().equals(curso.getNombre()));
        assertTrue(()->returned.getDescripcion().equals(curso.getDescripcion()));
    }

    @Test
    void findAll_ReturnQuantitySizeEqualCount_True(){
        Iterable<Curso> returned = cursoRepository.findAll();
        assertTrue(()->returned.spliterator().getExactSizeIfKnown() == cursoRepository.count());
    }

    @Test
    void findAllById_ReturnSizeEqualCount_True(){
        Iterable<Curso> allCursos = cursoRepository.findAll();
        Iterable<UUID> ids =   StreamSupport.stream(allCursos.spliterator(), true)
                                .map(mapper->mapper.getId()).toList();
        
        Iterable<Curso> returned = cursoRepository.findAllById(ids);

        assertTrue(()->returned.spliterator().getExactSizeIfKnown() == cursoRepository.count());
    }

    @Test
    void existsById_AfterSave_ReturnTrue(){
        curso = cursoRepository.save(curso);
        assertTrue(()->cursoRepository.existsById(curso.getId()));
    }

    @Test
    void count_ReturnEqualFindAllSize_True(){
        assertTrue((()->cursoRepository.count() == cursoRepository.findAll().spliterator().getExactSizeIfKnown()));
    }

    @Test
    void findAllPaginated_Return3Page1_true(){
        assertTrue((()->cursoRepository.findAllPaginated(3, 0).spliterator().getExactSizeIfKnown() == 3));
    }

    @Test
    void update_ReturnModifiedObject_true(){
        curso = cursoRepository.save(curso);
        curso.setNombre("Curso de Java 2");
        curso.setDescripcion("Curso de Java 2");
        Curso returned = cursoRepository.update(curso);

        assertTrue(()->returned.getNombre().equals("Curso de Java 2"));
        assertTrue(()->returned.getDescripcion().equals("Curso de Java 2"));
    }

    @Test
	void delete_ReturnEmptyOptional_AfterDelete_True(){
        curso = cursoRepository.save(curso);
        cursoRepository.delete(curso);
        Optional<Curso> findCurso = cursoRepository.findById(curso.getId());
        assertTrue(findCurso.isEmpty()); 
    }

    @Test
    void deleteById_ReturnEmptyOptional_AfterDelete_True(){
        curso = cursoRepository.save(curso);
        cursoRepository.deleteById(curso.getId());
        Optional<Curso> findCurso = cursoRepository.findById(curso.getId());
        assertTrue(findCurso.isEmpty());
    }

    @Test
    void ShouldreturnEmptyCursos_AfterSaveAndDeleteListIds() {

        Iterable<Curso> saved = cursoRepository.saveAll(cursolist);
        
        List<UUID> listIds = StreamSupport.stream(saved.spliterator(), true)
                        .map(curso->curso.getId()).toList();

        listIds.forEach(id->{
            assertFalse(()->cursoRepository.findById(id).isEmpty());
        });

        cursoRepository.deleteAllById(listIds);
        
        listIds.forEach(id->{
            assertTrue(()->cursoRepository.findById(id).isEmpty());
        });
    }

    @Test
	void ShouldreturnEmptyCursos_AfterSaveAndDeleteList(){
        Iterable<Curso> saveAll = cursoRepository.saveAll(cursolist);
        saveAll.forEach(curso->{
            assertFalse(()->cursoRepository.findById(curso.getId()).isEmpty());
        });
        cursoRepository.deleteAll(saveAll);
        saveAll.forEach(curso->{
            assertTrue(()->cursoRepository.findById(curso.getId()).isEmpty());
        });
    }

    @Test
	void deleteAll_ReturnCountEqual0_True(){
        cursoRepository.deleteAll();
        assertTrue(()->cursoRepository.count() == 0);
    }
}