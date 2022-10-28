package com.orient.CrudOperation.dto;

import com.orient.CrudOperation.model.Account;

public class AccountDTO {
    private Long id;
    private String userName;
    private String firstName;


    public AccountDTO(Account account){
        this.id=account.getId();
        this.firstName=account.getFirstName();
    }

    public AccountDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


}
