package com.oauth.clientserver.repository;


import com.oauth.clientserver.repository.entity.CmnMenuBcEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<CmnMenuBcEntity, Integer> {

}