package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.app.domain.Event;

public interface EventMapper {

	List<Event> selectAll() throws Exception;

	Event selectByEventId(Integer eventId) throws Exception;

	void insert(Event event) throws Exception;

	void update(Event event) throws Exception;

	void delete(Integer eventId) throws Exception;

	// ページ分割機能用
	Long count() throws Exception;
	List<Event> selectLimited(int userId, int offset, int numPerPage);

	List<Event> selectEventsByUserId(int userId, int offset, int numPerPage);

	int countEventsByUserId(int userId);

	int countEventJoinedUser(int eventId);

	int isUserAlreadyJoined(int userId, int eventId);

	void joinEvent(int userId, int eventId);

	List<Event> listJoinedEvents(int userId);

	void cancel(@Param("eventId") Integer eventId, @Param("userId") Integer userId);
}
