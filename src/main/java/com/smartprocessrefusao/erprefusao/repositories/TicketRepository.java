package com.smartprocessrefusao.erprefusao.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartprocessrefusao.erprefusao.entities.Ticket;

import jakarta.persistence.LockModeType;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	Optional<Ticket> findByNumTicketAndIdNot(Long numTicket, Long id);

	Optional<Ticket> findByNumTicket(Long numTicket);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT t FROM Ticket t WHERE t.numTicket = :numTicket")
	Optional<Ticket> findByNumTicketForUpdate(@Param("numTicket") Long numTicket);

}
