package com.crud1mintic.app.service;

import java.util.List;

import com.crud1mintic.app.entity.Agenda;

public interface IAgendaService {

	public List<Agenda> findAll();
	
	public void save(Agenda agenda);
	
	public void delete(Long id);
	
	public Agenda findOne(Long id);
	
	
}
