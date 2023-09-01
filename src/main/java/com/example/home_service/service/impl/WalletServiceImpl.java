package com.example.home_service.service.impl;

import com.example.home_service.entity.Wallet;
import com.example.home_service.repository.WalletRepository;
import com.example.home_service.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class WalletServiceImpl implements WalletService {
    private final WalletRepository repository;

    @Autowired
    public WalletServiceImpl(WalletRepository repository) {
        this.repository = repository;
    }

    @Override
    public Wallet createWallet() {
        Wallet wallet = new Wallet();
        wallet.setBalance(0D);
        return repository.save(wallet);
    }

    @Override
    public void update(Wallet wallet) {
        repository.save(wallet);
    }
}
