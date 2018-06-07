package com.intern.student.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.intern.student.dao.StudentDao;
import com.intern.student.modal.Student;

@Service("studentService")
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDao studentDao;
	
	@Override
	public String findStudent() {

		Map<String, Map<String, List<Student>>> rangeMap = new HashMap<>();
		
		List<Student> studentList = studentDao.findStudentByCriteria();
		
		rangeMap.put("10-20", findByCity(studentList.stream()
													//.map(Student.class::cast)
													.filter(s -> s.getAge()>=10 && s.getAge()<20)
													.collect(Collectors.toList())));
		
		rangeMap.put("20-30", findByCity(studentList.stream()
													//.map(Student.class::cast)
													.filter(s -> s.getAge()>=20 && s.getAge()<30)
													.collect(Collectors.toList())));
		
		rangeMap.put("30-60", findByCity(studentList.stream()
													//.map(Student.class::cast)
													.filter(s -> s.getAge()>=30 && s.getAge()<=60)
													.collect(Collectors.toList())));
		
		return new Gson().toJson(rangeMap);
		
	}
	
	
	/* Divide Each Student According To Their City */
	private Map<String, List<Student>> findByCity(List<Student> objectList){
		Map<String, List<Student>> studentMap = new HashMap<>();
		objectList.forEach(student -> {
			//Student student = (Student) o;
			String city = student.getCity();
			if(studentMap.containsKey(city)) {
				List<Student> cityStudent = studentMap.get(city);
				cityStudent.add(student);
				studentMap.remove(city);
				studentMap.put(city, cityStudent);
			} else {
				List<Student> cityStudent = new ArrayList<>();
				cityStudent.add(student);
				studentMap.put(city, cityStudent);
			}
		});
		return studentMap;
	}


	
}
