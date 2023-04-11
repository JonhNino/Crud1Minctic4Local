package com.crud1mintic.app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import javax.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.crud1mintic.app.entity.Agenda;
import com.crud1mintic.app.service.IAgendaService;




@Controller
@SessionAttributes("agenda")
public class AgendaController {
	
	@Autowired
	private IAgendaService agendaDao;
	
	
	@RequestMapping(value="/listar",method = RequestMethod.GET)
	public String llamarListar(Model model)
	{
		

		model.addAttribute("agenda",agendaDao.findAll());
		return "listar";
	}
	
	
	
	@RequestMapping(value={"/form","/"},method = RequestMethod.GET)
	public String llamarformulario(Map<String,Object> model)
	{
		Agenda agenda = new Agenda();
		

		model.put("agenda", agenda);
		return "form";
	}
	
	
	
	@RequestMapping(value={"/form"},method = RequestMethod.POST)
	public String guardar(@Valid Agenda agenda,BindingResult result, Model model, @RequestParam("file") MultipartFile foto,SessionStatus status)
	{
		if (result.hasErrors())
	{
		
		return "form";
	}
if (!foto.isEmpty()) {
			
			Path directorioRecursos = Paths.get("src//main//resources//static//assets//uploads");
			String rootPath = directorioRecursos.toFile().getAbsolutePath();

			try {

				byte[] bytes = foto.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename());
				Files.write(rutaCompleta, bytes);
				

				agenda.setFoto(foto.getOriginalFilename());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	{
		agendaDao.save(agenda);
		return "redirect:listar";
	}
	}
	
	
	@RequestMapping(value={"/eliminar/{id}"})
	public String eliminar (@PathVariable(value="id") Long id)
	{
		if (id > 0) {
			agendaDao.delete(id);
		}
		return "redirect:/listar";
	}
	
	
	@RequestMapping(value={"/form/{id}"})
	public String editar (@PathVariable(value="id") Long id, Map<String,Object> model)
	{
		Agenda agenda=null;
		if (id > 0) {
			agenda = agendaDao.findOne(id);
			
			} else {
				return "redirect:listar";
			}
		

		    model.put("agenda", agenda);
			return "form";
		}
		
	
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Agenda agenda = agendaDao.findOne(id);
		if (agenda == null) {
			
			return "redirect:/listar";
		}

		model.put("agenda", agenda);
		model.put("titulo", "Detalle cliente: " + agenda.getNombre());
		return "ver";
	}
	
	
}
