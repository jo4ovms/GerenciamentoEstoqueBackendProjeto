package com.cadastro1.demo.service;

import com.cadastro1.demo.model.Log;
import com.cadastro1.demo.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public Log salvar(Log log) {
        return logRepository.save(log);
    }

    public List<Log> listar() {
        return logRepository.findAll();
    }
}
