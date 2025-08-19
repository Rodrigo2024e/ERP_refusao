package com.smartprocessrefusao.erprefusao.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
	Optional<Ticket> findByNumTicket(Integer numTicket);
	
}
