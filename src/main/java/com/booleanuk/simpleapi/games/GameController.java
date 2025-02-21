package com.booleanuk.simpleapi.games;

import com.booleanuk.simpleapi.budgets.Budget;
import com.booleanuk.simpleapi.budgets.BudgetRepository;
import com.booleanuk.simpleapi.companies.Company;
import com.booleanuk.simpleapi.companies.CompanyRepository;
import com.booleanuk.simpleapi.response.ErrorResponse;
import com.booleanuk.simpleapi.response.GameListResponse;
import com.booleanuk.simpleapi.response.GameResponse;
import com.booleanuk.simpleapi.response.Response;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("games")
public class GameController {

    @Autowired
    private final GameRepository gameRepository;
    @Autowired
    private final CompanyRepository companyRepository;
    @Autowired
    private final BudgetRepository budgetRepository;

    public GameController(GameRepository gameRepository, CompanyRepository companyRepository, BudgetRepository budgetRepository) {
        this.gameRepository = gameRepository;
        this.companyRepository = companyRepository;
        this.budgetRepository = budgetRepository;
    }

    @GetMapping
    public ResponseEntity<GameListResponse> getAll() {
        GameListResponse gameListResponse = new GameListResponse();
        gameListResponse.set(this.gameRepository.findAll());
        return ResponseEntity.ok(gameListResponse);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @RequestMapping("/company/{cId}/budget/{bId}")
    public ResponseEntity<Response<?>> create(@RequestBody Game game, @PathVariable("cId") int cId, @PathVariable("bId") int bId) {
        Company theCompany = this.companyRepository.findById(cId).orElse(null);
        if (theCompany == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Company not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        game.setCompany(theCompany);
        Budget theBudget = this.budgetRepository.findById(bId).orElse(null);
        if (theBudget == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Budget not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        game.setBudget(theBudget);
        GameResponse gameResponse = new GameResponse();
        try {
            gameResponse.set(this.gameRepository.save(game));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(gameResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteAGame(@PathVariable int id) {
        Game gameToDelete = this.gameRepository.findById(id).orElse(null);
        if (gameToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Game not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.gameRepository.delete(gameToDelete);
        GameResponse gameResponse = new GameResponse();
        gameResponse.set(gameToDelete);
        return ResponseEntity.ok(gameResponse);
    }
}
