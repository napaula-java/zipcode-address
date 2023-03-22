package com.example.postof.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.postof.model.Address;

public interface AddressRepository extends JpaRepository<Address, String> {

}
