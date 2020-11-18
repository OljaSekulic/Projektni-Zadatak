package xwsagent.wallservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xwsagent.wallservice.domain.Rate;
import xwsagent.wallservice.dto.RateDTO;
import xwsagent.wallservice.repository.RateRepository;
import xwsagent.wallservice.service.RateService;

@RestController
@RequestMapping(value="/api/rate", produces = MediaType.APPLICATION_JSON_VALUE)
public class RateController {

	@Autowired
	RateRepository rateRepository;
	
	@Autowired
	RateService rateService;
	
	@PostMapping(value = "/add/{id}")
	public ResponseEntity<?> add(@PathVariable("id")Long id, @RequestBody RateDTO rateDTO) {
		Rate rate = rateService.addRate(rateDTO, id);
		return new ResponseEntity<>(rate, HttpStatus.OK);
	}
	
	@GetMapping(value="/avg/{id}")
	public ResponseEntity<?> getAvg(@PathVariable("id") Long id){
		Float rez = rateService.getAvg(id);
		return new ResponseEntity<>(rez, HttpStatus.OK);
	}
}
