package com.example.home_service.service.impl;

import com.example.home_service.entity.Wallet;
import com.example.home_service.repository.WalletRepository;
import com.example.home_service.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository repository;


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
