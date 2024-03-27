package com.santander.consumer.westernhub.customer.repository;

import com.santander.consumer.westernhub.customer.model.IvrCodification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Request Repository
 *
 */
@Repository
@Transactional
@org.springframework.transaction.annotation.Transactional
public interface IvrCodificationRepository extends JpaRepository<IvrCodification, Integer> { }
