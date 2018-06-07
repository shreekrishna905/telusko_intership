package com.intern.student.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intern.student.modal.Student;

@Service("studentDao")
@Repository
@Transactional
public class StudentDaoImpl implements StudentDao {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	@Transactional(readOnly=true)
	public List<Student> findStudentByCriteria() {
		Query query = entityManager.createNativeQuery("SELECT id, name,age,mark,city " + 
				"FROM " + 
				"(SELECT id, name,age,mark,city, " + 
				"@mark_count\\:=IF(@current_city = city, @mark_count + 1, 1) AS mark_count, " + 
				"@current_city\\:=city " + 
				"FROM student " + 
				"ORDER BY mark desc, city" + 
				") counts  " + 
				"WHERE mark_count <= 5", Student.class);
		return query.getResultList();
	}
	
	
	
}
