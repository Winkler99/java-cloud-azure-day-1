package com.booleanuk.simpleapi.budgets;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {
}
