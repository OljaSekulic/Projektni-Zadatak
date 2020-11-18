package xwsagent.wallservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xwsagent.wallservice.domain.Post;
import xwsagent.wallservice.domain.Rate;
import xwsagent.wallservice.dto.RateDTO;
import xwsagent.wallservice.repository.RateRepository;

@Service
public class RateService {

	@Autowired
	RateRepository rateRepository;
	
	@Autowired
	PostService postService;
	
	public Rate addRate(RateDTO dto, Long id) {
		Post post = postService.findById(id);
		Rate rate = new Rate();
		rate.setRate(dto.getRate());
		rate.setUserName(dto.getUserName());
		rate.setPost(post.getId());
		rateRepository.save(rate);
		return rate;
	}
	
	public Rate findById(Long id){
		Rate rate = rateRepository.findById(id).orElseGet(null);
		return rate;
	}
	
	public Float getAvg(Long id) {
		int sum = 0;
		float rez = 0;
		List<Rate> rates = rateRepository.findAll();
		List<Rate> list = new ArrayList<Rate>();
		for(Rate r : rates) {
			if(r.getPost() == id) {
				list.add(r);
			}
		}
		for(Rate rate : list) {
			sum += rate.getRate();
		}
		rez = sum / list.size();
		return rez;
	}
}
