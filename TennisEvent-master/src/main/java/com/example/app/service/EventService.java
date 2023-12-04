package com.example.app.service;

import com.example.app.domain.Event;
import com.example.app.domain.Level;
import com.example.app.domain.Location;

import java.util.List;

public interface EventService {

	List<Event> getEventList() throws Exception;

	Event getEventById(Integer eventId) throws Exception;

	void addEvent(Event event) throws Exception;

	void editEvent(Event event) throws Exception;

	void deleteEvent(Integer eventId) throws Exception;

	List<Level> getLevelList() throws Exception;

	List<Location> getLocationList() throws Exception;

	// ページ分割機能用
	int getTotalPages(int numPerPage) throws Exception;
	List<Event> getEventListByPage(int userId, int offset, int numPerPage) throws Exception;

	public List<Event> getEventsByUserId(int userId, int offset, int numPerPage) throws Exception;

	public int getTotalPagesByUserId(int userId, int numPerPage) throws Exception;

	public int countEventJoinedUser(int eventId);

	boolean isUserAlreadyJoined(int userId, int eventId) throws Exception;

	void joinEvent(int userId, int eventId) throws Exception;

	List<Event> listJoinedEvents(Integer userId) throws Exception;

	void cancelEvent(Integer eventId, Integer userId) throws Exception;

}
