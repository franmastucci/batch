package com.santander.consumer.westernhub.customer.repository;

import com.santander.consumer.westernhub.customer.model.Codification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Codification Repository
 *
 */
@Repository
@Transactional
@org.springframework.transaction.annotation.Transactional
public interface CodificationRepository extends JpaRepository<Codification, Integer> { }
