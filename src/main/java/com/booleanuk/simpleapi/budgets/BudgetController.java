package com.booleanuk.simpleapi.budgets;

import com.booleanuk.simpleapi.response.BudgetListResponse;
import com.booleanuk.simpleapi.response.BudgetResponse;
import com.booleanuk.simpleapi.response.ErrorResponse;
import com.booleanuk.simpleapi.response.Response;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("budgets")
public class BudgetController {

    @Autowired
    private final BudgetRepository budgetRepository;

    public BudgetController(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @GetMapping
    public ResponseEntity<BudgetListResponse> getAll() {
        BudgetListResponse budgetListResponse = new BudgetListResponse();
        budgetListResponse.set(this.budgetRepository.findAll());
        return ResponseEntity.ok(budgetListResponse);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody Budget budget) {
        BudgetResponse budgetResponse = new BudgetResponse();
        try {
            budgetResponse.set(this.budgetRepository.save(budget));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request - cannot create budget");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(budgetResponse, HttpStatus.CREATED);
    }
}
