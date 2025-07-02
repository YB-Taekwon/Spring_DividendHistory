package com.ian.finance.domain.repository;

import com.ian.finance.domain.entity.Dividend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DividendRepository extends JpaRepository<Dividend, Long> {
    List<Dividend> findAllByCompanyId(Long companyId);
}
