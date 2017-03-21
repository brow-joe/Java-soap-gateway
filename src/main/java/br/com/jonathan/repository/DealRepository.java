package br.com.jonathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jonathan.domain.DealDomain;

public interface DealRepository extends JpaRepository<DealDomain, Long> {

}