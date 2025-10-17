package com.eco.alert.ecoAlert.service;

import com.eco.alert.ecoAlert.entity.UserEntity;
import com.eco.alert.ecoAlert.dao.UserDao;
import com.eco.alert.ecoAlert.exception.EmailDuplicataException;
import com.eco.alert.ecoAlert.exception.LoginException;
import com.ecoalert.model.*;
import com.ecoalert.model.Error;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserService {

    @Autowired
    private final UserDao userDao;

    public UserService(UserDao utenteRepository) {
        this.userDao = utenteRepository;
    }


    public UtenteOutput creaUtente(UtenteInput input) {
        log.info("signIn api");
        UserEntity utenteConStessaMail = userDao.findByEmail(input.getEmail());
        if(utenteConStessaMail!= null){
            throw new EmailDuplicataException();
        }

        UserEntity utente = new UserEntity();
        utente.setNome(input.getNome());
        utente.setCognome(input.getCognome());
        utente.setEmail(input.getEmail());
        utente.setPassword(input.getPassword());

        UserEntity salvato = userDao.save(utente);

        UtenteOutput output = new UtenteOutput();
        output.setId(salvato.getId());
        output.setNome(salvato.getNome());
        output.setCognome(salvato.getCognome());
        output.setEmail(salvato.getEmail());
        return output;
    }

    public LoginOutput login (LoginInput loginInput){
        LoginOutput loginOutput = null;
        UserEntity utenteByEmail = userDao.findByEmail(loginInput.getEmail());
        if(utenteByEmail != null){
            if(utenteByEmail.getPassword().equals(loginInput.getPassword())){
                loginOutput = new LoginOutput();
                loginOutput.setUserId(utenteByEmail.getId());
                loginOutput.setToken("TOKEN");
            }else{
                throw new LoginException("Password Errata");
            }

        }else {
            throw new LoginException("Email non presente nel database");
        }

        return loginOutput;
    }
}
