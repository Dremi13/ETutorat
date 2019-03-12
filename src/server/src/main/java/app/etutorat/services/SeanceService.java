package app.etutorat.services;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.etutorat.dao.SeanceRepository;
import app.etutorat.models.Seance;
import app.etutorat.models.Tuteur;
import app.etutorat.models.Tutore;


@Service
@Transactional
public class SeanceService {
	
	@Autowired
	private SeanceRepository seanceR;

	
	public List<Seance> findSeanceByTuteur(Tuteur tuteur)
	{
		return seanceR.findByTuteur(tuteur);
	}
	
	public List<Seance> findSeanceByTutore(Tutore tutore)
	{
		return seanceR.findByTutores(tutore);
	}
	

}
