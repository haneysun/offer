package com.example.offer.service;

import com.example.offer.dao.UserMapper;
import com.example.offer.entity.User;

import java.util.List;

public interface ILoginService {


    User loginfill(User User);

    User findAction(User User);

    UserMapper getUserMapper();

    List<User> findAllUser();

    void initAllPwd(List<User> Users);

}
