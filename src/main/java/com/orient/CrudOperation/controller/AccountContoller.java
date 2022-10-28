package com.orient.CrudOperation.controller;

import com.orient.CrudOperation.dto.AccountDTO;
import com.orient.CrudOperation.dto.ResponseDTO;
import com.orient.CrudOperation.model.Account;
import com.orient.CrudOperation.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/account")
public class AccountContoller {
    @Autowired
    AccountService accountService;

    @GetMapping(value="/all")
    public List<Account> findAll(){
       List<Account> accounts=accountService.findAll();
        return accounts;
    }
    @GetMapping(value="/allDto")
    public ResponseEntity<ResponseDTO> findAllDto(){
        List<Account> accounts=accountService.findAll();
        List<AccountDTO> accountDTOS=new ArrayList<>();
        for(int i=0;i<accounts.size();i++){
            Account account=accounts.get(i);
            accountDTOS.add(new AccountDTO(account));
        }


        return ResponseEntity.ok(ResponseDTO.of(accountDTOS));

    }

    @GetMapping("/accountsDto/{id}")
    public ResponseEntity<ResponseDTO> getUserDto(@PathVariable("id") Long id){
        Account account=accountService.findById(id);
        return ResponseEntity.ok(ResponseDTO.of(new AccountDTO(account)));
    }

    @GetMapping(value="/{id}")
    public Account findById(@PathVariable Long id){
        Account account=accountService.findById(id);
        return account;
    }

    public static BCryptPasswordEncoder crypt= new BCryptPasswordEncoder();
    @PostMapping(value="/create")
    public void create(@RequestBody Account account){
        account.setPassword(crypt.encode(account.getPassword()));
        accountService.create(account);
    }

    @PostMapping("/createDto")
    public ResponseEntity<ResponseDTO> createDto(@RequestBody Account account){
        account.setPassword(crypt.encode(account.getPassword()));
        accountService.create(account);
        return ResponseEntity.ok(ResponseDTO.of(new AccountDTO(account),"Successfuly Created"));
    }

    @PutMapping(value="/update/{id}")
    public void update(@PathVariable Long id,@RequestBody Account account){
        accountService.update(id,account);
    }

    @DeleteMapping("/deleteDto/{id}")
    public ResponseEntity<ResponseDTO> deleteDto(@PathVariable("id") Long id){
        Account account=accountService.findById(id);
        accountService.delete(id);
        return ResponseEntity.ok(ResponseDTO.of(new AccountDTO(account),"Successfuly Deleted"));
    }

    @DeleteMapping(value="/delete/{id}")
    public void delete(@PathVariable Long id){
        accountService.delete(id);
    }
    

}
