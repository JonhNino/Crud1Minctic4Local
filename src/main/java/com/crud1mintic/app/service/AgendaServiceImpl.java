package com.crud1mintic.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crud1mintic.app.entity.Agenda;
import com.crud1mintic.app.repository.IAgendaRepository;

@Service
public class AgendaServiceImpl implements IAgendaService {

	@Autowired
	private IAgendaRepository agendaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Agenda> findAll() {
		

		return (List<Agenda>) agendaDao.findAll();
	}

	@Override
	@Transactional
	public void save(Agenda agenda) {
		agendaDao.save(agenda);

		
	}

	@Override
	@Transactional
	public void delete(Long id) {

		agendaDao.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Agenda findOne(Long id) {
		
		
		return agendaDao.findById(id).orElse(null);
	}

}
