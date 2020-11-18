package xwsagent.wallservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xwsagent.wallservice.domain.Rate;


@Repository
public interface RateRepository extends JpaRepository<Rate, Long>{
	List<Rate> findAll();
}
