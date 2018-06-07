package com.intern.student.dao;

import java.util.List;

import com.intern.student.modal.Student;


public interface StudentDao {

	public List<Student> findStudentByCriteria();
}
