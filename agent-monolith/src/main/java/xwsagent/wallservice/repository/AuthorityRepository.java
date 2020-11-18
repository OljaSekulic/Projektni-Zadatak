package xwsagent.wallservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xwsagent.wallservice.domain.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long>{
	Authority findByName(String name);
}
