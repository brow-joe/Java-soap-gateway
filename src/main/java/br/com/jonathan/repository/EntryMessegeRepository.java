package br.com.jonathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jonathan.domain.EntryMessegeRequestDomain;

public interface EntryMessegeRepository extends JpaRepository<EntryMessegeRequestDomain, Long> {

}