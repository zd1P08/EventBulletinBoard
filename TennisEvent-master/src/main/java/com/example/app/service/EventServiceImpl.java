package com.example.app.service;

import com.example.app.domain.Event;
import com.example.app.domain.Level;
import com.example.app.domain.Location;
import com.example.app.mapper.EventMapper;
import com.example.app.mapper.LevelMapper;
import com.example.app.mapper.LocationMapper;
import com.example.app.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class EventServiceImpl implements EventService {

	@Autowired
	private EventMapper eventMapper;
	@Autowired
	private LevelMapper levelMapper;
	@Autowired
	private LocationMapper locationMapper;
	@Autowired
	private UserMapper userMapper;

	@Override
	public List<Event> getEventList() throws Exception {
		return eventMapper.selectAll();
	}

	@Override
	public Event getEventById(Integer eventId) throws Exception {
		return eventMapper.selectByEventId(eventId);
	}

	@Override
	public void addEvent(Event event) throws Exception {
		eventMapper.insert(event);
	}

	@Override
	public void editEvent(Event event) throws Exception {
		eventMapper.update(event);
	}

	@Override
	public void deleteEvent(Integer eventId) throws Exception {
		eventMapper.delete(eventId);
	}

	@Override
	public List<Level> getLevelList() throws Exception {
		return levelMapper.selectAll();
	}

	@Override
	public List<Location> getLocationList() throws Exception {
		return locationMapper.selectAll();
	}

	@Override
	public int getTotalPages(int numPerPage) throws Exception {
		double totalNum = (double) eventMapper.count();
		return (int) Math.ceil(totalNum / numPerPage);
	}

	@Override
	public List<Event> getEventListByPage(int userId, int offset, int numPerPage) throws Exception {
		return eventMapper.selectLimited(userId, offset, numPerPage);
	}

	@Override
	public List<Event> getEventsByUserId(int userId, int offset, int numPerPage) throws Exception {
	    return eventMapper.selectEventsByUserId(userId, offset, numPerPage);
	}

	@Override
	public int getTotalPagesByUserId(int userId, int numPerPage) throws Exception {
	    int totalEventsByUser = eventMapper.countEventsByUserId(userId);
	    return (int) Math.ceil((double) totalEventsByUser / numPerPage);
	}

	@Override
	public int countEventJoinedUser(int eventId) {
		return eventMapper.countEventJoinedUser(eventId);
	}
	
	@Override
	public boolean isUserAlreadyJoined(int userId, int eventId) throws Exception {
	    return eventMapper.isUserAlreadyJoined(userId, eventId) > 0;
	}

    @Override
    public void joinEvent(int userId, int eventId) throws Exception {
        eventMapper.joinEvent(userId, eventId);
    }

	@Override
	public List<Event> listJoinedEvents(Integer userId) throws Exception {
		return eventMapper.listJoinedEvents(userId);
	}

	@Override
	public void cancelEvent(Integer eventId, Integer userId) throws Exception {
		eventMapper.cancel(eventId, userId);
	}

}
