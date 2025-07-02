package com.ian.finance.domain.repository;

import com.ian.finance.domain.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsByTicker(String ticker);

    @Query("SELECT c.name FROM COMPANY c")
    Page<String> findAllCompanyName(Pageable pageable);

    Optional<Company> findByTicker(String ticker);
}