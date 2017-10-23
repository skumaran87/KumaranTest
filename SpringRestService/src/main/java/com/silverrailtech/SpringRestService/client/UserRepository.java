package com.silverrailtech.SpringRestService.client;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource
public interface UserRepository extends CrudRepository<Users, Long>{

	List<Users> findByToken(String token);
	
	List<Users> findByTokenAndFirstName(String token, String firstName);
	

    @Transactional
	@Modifying
    @Query("update Users u SET u.state=:state WHERE u.token=:token")
    public void updateState(@Param("state") String state, @Param("token") String token);
}
