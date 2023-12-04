package com.example.app.mapper;

import java.util.List;

import com.example.app.domain.Location;

public interface LocationMapper {

	List<Location> selectAll() throws Exception;
	
}
