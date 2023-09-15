package com.example.home_service.repository;

import com.example.home_service.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository
        extends JpaRepository<Wallet,Long> {
}
