package com.example.home_service.service.impl;

import com.example.home_service.entity.Wallet;
import com.example.home_service.repository.WalletRepository;
import com.example.home_service.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    WalletRepository repository;

    @Override
    public Wallet createWallet() {
        Wallet wallet = new Wallet();
        wallet.setBalance(0D);
        return repository.save(wallet);
    }
}
