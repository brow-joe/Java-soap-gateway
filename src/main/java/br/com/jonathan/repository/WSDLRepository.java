package br.com.jonathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jonathan.domain.WSDLDomain;

public interface WSDLRepository extends JpaRepository<WSDLDomain, Long> {

}