package com.booleanuk.simpleapi.companies;

import com.booleanuk.simpleapi.response.CompanyListResponse;
import com.booleanuk.simpleapi.response.CompanyResponse;
import com.booleanuk.simpleapi.response.ErrorResponse;
import com.booleanuk.simpleapi.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins= "http://localhost:4200")
@RestController
@RequestMapping("companies")
public class CompanyController {

    @Autowired
    private final CompanyRepository repository;

    public CompanyController(CompanyRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<CompanyListResponse> getAll() {
        CompanyListResponse companyListResponse = new CompanyListResponse();
        companyListResponse.set(this.repository.findAll());
        return ResponseEntity.ok(companyListResponse);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        try {
            companyResponse.set(this.repository.save(company));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request - cannot create company");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(companyResponse, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateCompany(@PathVariable int id, @RequestBody Company company) {
        Company companyToUpdate = null;
        try {
            companyToUpdate = this.repository.findById(id).orElse(null);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request - could not successfully find company");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        if (companyToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Company not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        companyToUpdate.setName(company.getName());
        companyToUpdate.setEmail(company.getEmail());
        companyToUpdate.setPhone(company.getPhone());
        companyToUpdate= this.repository.save(companyToUpdate);
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.set(companyToUpdate);
        return new ResponseEntity<>(companyResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteACompany(@PathVariable int id) {
        Company companyToDelete = this.repository.findById(id).orElse(null);
        if (companyToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Company not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.repository.delete(companyToDelete);
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.set(companyToDelete);
        return ResponseEntity.ok(companyResponse);
    }
}
