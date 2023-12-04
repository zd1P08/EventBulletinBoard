package com.example.app.mapper;

import java.util.List;

import com.example.app.domain.Level;

public interface LevelMapper {

	List<Level> selectAll() throws Exception;
	
}
