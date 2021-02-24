package com.app.vaccnow.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.app.vaccnow.config.Constants;
import com.app.vaccnow.domain.AppConfiguration;
import com.app.vaccnow.domain.Schedule;
import com.app.vaccnow.domain.Timeslot;
import com.app.vaccnow.repository.AppConfigurationRepository;
import com.app.vaccnow.repository.ScheduleRepository;
import com.app.vaccnow.repository.TimeslotRepository;
import com.app.vaccnow.service.ScheduleService;
import com.app.vaccnow.service.dto.ScheduleDTO;
import com.app.vaccnow.service.mapper.ScheduleMapper;
import com.app.vaccnow.util.GeneralUtil;

/**
 * Service Implementation for managing {@link Schedule}.
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final Logger log = LoggerFactory.getLogger(ScheduleServiceImpl.class);

    private final ScheduleRepository scheduleRepository;

    private final ScheduleMapper scheduleMapper;

    private final TimeslotRepository timeslotRepository;
    
    private final AppConfigurationRepository appConfigurationRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, ScheduleMapper scheduleMapper, TimeslotRepository timeslotRepository, AppConfigurationRepository appConfigurationRepository) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
        this.timeslotRepository = timeslotRepository;
        this.appConfigurationRepository = appConfigurationRepository;
    }

    @Override
    public ScheduleDTO save(ScheduleDTO scheduleDTO) {
        log.debug("Request to save Schedule : {}", scheduleDTO);
        Schedule schedule = scheduleMapper.toEntity(scheduleDTO);
        schedule = scheduleRepository.save(schedule);
        
        AppConfiguration startConfig = appConfigurationRepository.findByKey(Constants.START_WORKING_HOURS_KEY);
        String[] startHourAndMins = startConfig.getValue().split(":");
        Integer startHour = Integer.valueOf(startHourAndMins[0]);
        Integer startMins = Integer.valueOf(startHourAndMins[1]);
        LocalDateTime start = schedule.getDate().atTime(startHour, startMins);
        
        AppConfiguration endConfig = appConfigurationRepository.findByKey(Constants.END_WORKING_HOURS_KEY);
        String[] endHourAndMins = endConfig.getValue().split(":");
        Integer endHour = Integer.valueOf(endHourAndMins[0]);
        Integer endMins = Integer.valueOf(endHourAndMins[1]);
        LocalDateTime stop = schedule.getDate().atTime(endHour, endMins);
        
        AppConfiguration timeUnitConfg = appConfigurationRepository.findByKey(Constants.TIMESLOT_UNIT_KEY);
        String timeUnit = timeUnitConfg.getValue();
        
        AppConfiguration timeDurationConfg = appConfigurationRepository.findByKey(Constants.TIMESLOT_DURATION);
        Integer timeDuration = Integer.valueOf(timeDurationConfg.getValue());
        
        Timeslot timeslot = new Timeslot();
        timeslot.setFrom(GeneralUtil.toZonedDateTime(start));
        timeslot.setStatus(Constants.DEFAULT_TIMESLOT_STATUS);
        LocalDateTime to = timeUnit.equalsIgnoreCase(Constants.TIMESLOT_UNIT_HOUR) ? start.plusHours(timeDuration) : start.plusMinutes(timeDuration);
        timeslot.setTo(GeneralUtil.toZonedDateTime(to));
        timeslot.setSchedule(schedule);
        timeslotRepository.save(timeslot);
        
        HashSet<Timeslot> timeslots = new HashSet<>();
        timeslots.add(timeslot);
        
        while(start.isBefore(stop) && !start.isAfter(stop) && !to.isAfter(stop)) {
        	timeslot = new Timeslot();
        	timeslot.setStatus(Constants.DEFAULT_TIMESLOT_STATUS);
        	timeslot.setSchedule(schedule);
        	
            start = timeUnit.equalsIgnoreCase(Constants.TIMESLOT_UNIT_HOUR) ? start.plusHours(timeDuration) : start.plusMinutes(timeDuration);
            timeslot.setFrom(GeneralUtil.toZonedDateTime(start));
            
            to = timeUnit.equalsIgnoreCase(Constants.TIMESLOT_UNIT_HOUR) ? start.plusHours(timeDuration) : start.plusMinutes(timeDuration);
            timeslot.setTo(GeneralUtil.toZonedDateTime(to));

            timeslotRepository.save(timeslot);
            timeslots.add(timeslot);
        }
        schedule.setTimeslots(timeslots);
        schedule = scheduleRepository.save(schedule);
        return scheduleMapper.toDto(schedule);
        
    }

    @Override
    public List<ScheduleDTO> findAll() {
        log.debug("Request to get all Schedules");
        return scheduleRepository.findAll().stream()
            .map(scheduleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    @Override
    public List<ScheduleDTO> findAllAvailableSchedules() {
        log.debug("Request to get all Schedules");
        List<Timeslot> timeslots = timeslotRepository.findByFromAfterAndStatus(ZonedDateTime.now(), Constants.DEFAULT_TIMESLOT_STATUS);
        List<Schedule> schedules = new ArrayList<Schedule>();
        for (Timeslot timeslot : timeslots) {
        	schedules.add(timeslot.getSchedule());
		}
        return schedules.stream()
            .map(scheduleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    @Override
    public List<ScheduleDTO> findAllAvailSchedulesBetween(LocalDateTime from, LocalDateTime to) {
        log.debug("Request to get all Schedules");
        List<Timeslot> timeslots = timeslotRepository.findByFromAfterAndToBeforeAndStatus(GeneralUtil.toZonedDateTime(from), GeneralUtil.toZonedDateTime(to),Constants.DEFAULT_TIMESLOT_STATUS);
        List<Schedule> schedules = new ArrayList<Schedule>();
        for (Timeslot timeslot : timeslots) {
        	schedules.add(timeslot.getSchedule());
		}
        return schedules.stream()
            .map(scheduleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    @Override
    public List<ScheduleDTO> findAllAppliedSchedules() {
        log.debug("Request to get all Schedules");
        List<Timeslot> timeslots = timeslotRepository.findByFromAfterAndStatus(ZonedDateTime.now(), Constants.APPROVED_TIMESLOT_STATUS);
        List<Schedule> schedules = new ArrayList<Schedule>();
        for (Timeslot timeslot : timeslots) {
        	schedules.add(timeslot.getSchedule());
		}
        return schedules.stream()
            .map(scheduleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    @Override
    public List<ScheduleDTO> findAllAppliedSchedulesBetween(LocalDateTime from, LocalDateTime to) {
        log.debug("Request to get all Schedules");
        List<Timeslot> timeslots = timeslotRepository.findByFromAfterAndToBeforeAndStatus(GeneralUtil.toZonedDateTime(from), GeneralUtil.toZonedDateTime(to),Constants.APPROVED_TIMESLOT_STATUS);
        List<Schedule> schedules = new ArrayList<Schedule>();
        for (Timeslot timeslot : timeslots) {
        	schedules.add(timeslot.getSchedule());
		}
        return schedules.stream()
            .map(scheduleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    public Optional<ScheduleDTO> findOne(String id) {
        log.debug("Request to get Schedule : {}", id);
        return scheduleRepository.findById(id)
            .map(scheduleMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Schedule : {}", id);
        scheduleRepository.deleteById(id);
    }
    
//    public static void main(String[] args) {
//		try {
//			Schedule schedule = new Schedule();
//			//AppConfiguration startConfig = appConfigurationRepository.findByKey(Constants.START_WORKING_HOURS_KEY);
//			LocalDate today = LocalDate.now();
//	        String[] startHourAndMins = "08:00".split(":");
//	        Integer startHour = Integer.valueOf(startHourAndMins[0]);
//	        Integer startMins = Integer.valueOf(startHourAndMins[1]);
//	        LocalDateTime start = today.atTime(startHour, startMins);
//	        
//	        //AppConfiguration endConfig = appConfigurationRepository.findByKey(Constants.END_WORKING_HOURS_KEY);
//	        String[] endHourAndMins = "10:00".split(":");
//	        Integer endHour = Integer.valueOf(endHourAndMins[0]);
//	        Integer endMins = Integer.valueOf(endHourAndMins[1]);
//	        LocalDateTime stop = today.atTime(endHour, endMins);
//	        
//	        //AppConfiguration timeUnitConfg = appConfigurationRepository.findByKey(Constants.TIMESLOT_UNIT_KEY);
//	        String timeUnit = "M";//timeUnitConfg.getValue();
//	        
//	        //AppConfiguration timeDurationConfg = appConfigurationRepository.findByKey(Constants.TIMESLOT_DURATION);
//	        Integer timeDuration = 15;//Integer.valueOf(timeDurationConfg.getValue());
//	        
//	        Timeslot timeslot = new Timeslot();
//	        timeslot.setFrom(GeneralUtil.toZonedDateTime(start));
//	        timeslot.setStatus(Constants.DEFAULT_TIMESLOT_STATUS);
//	        LocalDateTime to = timeUnit.equalsIgnoreCase(Constants.TIMESLOT_UNIT_HOUR) ? start.plusHours(timeDuration) : start.plusMinutes(timeDuration);
//	        timeslot.setTo(GeneralUtil.toZonedDateTime(to));
//	        timeslot.setSchedule(schedule);
//	        //timeslotRepository.save(timeslot);
//	        	        
//	        HashSet<Timeslot> timeslots = new HashSet<>();
//	        timeslots.add(timeslot);
//	        
//	        while(start.isBefore(stop) && !start.isEqual(stop) && !to.isAfter(stop)) {
//	        	timeslot = new Timeslot();
//	        	timeslot.setStatus(Constants.DEFAULT_TIMESLOT_STATUS);
//	        	timeslot.setSchedule(schedule);
//	        	
//	            start = timeUnit.equalsIgnoreCase(Constants.TIMESLOT_UNIT_HOUR) ? start.plusHours(timeDuration) : start.plusMinutes(timeDuration);
//	            timeslot.setFrom(GeneralUtil.toZonedDateTime(start));
//	            
//	            to = timeUnit.equalsIgnoreCase(Constants.TIMESLOT_UNIT_HOUR) ? start.plusHours(timeDuration) : start.plusMinutes(timeDuration);
//	            timeslot.setTo(GeneralUtil.toZonedDateTime(to));
//
//	            //timeslotRepository.save(timeslot);
//	            timeslots.add(timeslot);
//	        }
//	        schedule.setTimeslots(timeslots);
//	        //schedule = scheduleRepository.save(schedule);
//	        System.out.println(schedule);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
