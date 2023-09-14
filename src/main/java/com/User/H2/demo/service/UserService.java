package com.User.H2.demo.service;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.User.H2.demo.dao.UserRepository;
import com.User.H2.demo.entity.User;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User createUser(User user) {
		return userRepository.save(user);
	}

	public List<User> createUsers(List<User> users) {
		return userRepository.saveAll(users);
	}

	public User getUserById(int id) {
		return userRepository.findById(id).orElse(null);
	}

	public List<User> getUsers() {
		return userRepository.findAll();
	}

	public User updateUser(User user) {
		User oldUser = null;
		Optional<User> optionalUser = userRepository.findById(user.getId());
		if (optionalUser.isPresent()) {
			oldUser = optionalUser.get();
			oldUser.setName(user.getName());
			oldUser.setAddress(user.getAddress());
			userRepository.save(oldUser);
		} else {
			return new User();
		}
		return oldUser;
	}

	public String delUserById(int id) {
		userRepository.deleteById(id);
		return "User got deleted";
	}

	public User getUserByName(String name) {
		return userRepository.findByName(name);
	}

	public List<User> batchAdd() {
		
		
		  List<User> UserLists = new ArrayList<>(); 
		  String name = null;
		  String address = null;
		  String excelFilePath="C:\\Users\\1952175\\Desktop\\Book 2.xlsx";
		  long start = System.currentTimeMillis(); 
		  FileInputStream inputStream; 
		  try {
			  inputStream = new FileInputStream(excelFilePath);
				Workbook workbook=new XSSFWorkbook(inputStream);
				Sheet firstSheet=workbook.getSheetAt(0);
				Iterator<Row> rowIterator=firstSheet.iterator();
				rowIterator.next();
				while(rowIterator.hasNext()) 
				{
					Row nextRow = rowIterator.next();
					Iterator<Cell> cellIterator=nextRow.cellIterator();
					while(cellIterator.hasNext()) 
					{
						Cell nextCell=cellIterator.next();
						int columnIndex=nextCell.getColumnIndex();
						switch (columnIndex)
						{
						case 0:
							name=nextCell.getStringCellValue();
							break;
						case 1:
                            address=nextCell.getStringCellValue();
							break;
						
						}
					}
					User user = new User(name,address);
					UserLists.add(user);
					
				}
				createUsers(UserLists);
				workbook.close();
			  
			 
		     }
		  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
		  return UserLists;
		 
	}
}
