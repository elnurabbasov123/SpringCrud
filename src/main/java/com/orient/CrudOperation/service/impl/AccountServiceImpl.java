package com.orient.CrudOperation.service.impl;

import com.orient.CrudOperation.model.Account;
import com.orient.CrudOperation.repository.AccountRepository;
import com.orient.CrudOperation.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> findAll() {
        List<Account> aa=accountRepository.findAll();
        return aa;
    }

    @Override
    public Account findById(Long id) {
        Account account=null;
        try{
            account=accountRepository.findById(id).get();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return account;
    }

    @Override
    public void create(Account account) {

        accountRepository.save(account);
    }

    @Override
    public void update(Long id, Account account) {

        Account oldAccount=findById(id);
        oldAccount.setFirstName(account.getFirstName());
        oldAccount.setLastName(account.getLastName());
        oldAccount.setEmail(account.getEmail());
        oldAccount.setPassword(account.getPassword());
        accountRepository.save(oldAccount);
    }

    @Override
    public void delete(Long id) {

        Account account=findById(id);
        if(account!=null){
            accountRepository.delete(account);
        }
    }

}
