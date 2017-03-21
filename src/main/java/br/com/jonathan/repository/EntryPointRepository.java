package br.com.jonathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jonathan.domain.EntryPointDomain;

public interface EntryPointRepository extends JpaRepository<EntryPointDomain, Long> {

}