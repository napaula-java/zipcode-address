package com.example.postof.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.example.postof.PostofApplication;
import com.example.postof.infra.exception.NoContentException;
import com.example.postof.infra.exception.NotReadyException;
import com.example.postof.model.Address;
import com.example.postof.model.AddressStatus;
import com.example.postof.model.Status;
import com.example.postof.repository.AddressRepository;
import com.example.postof.repository.AddressStatusRepository;
import com.example.postof.repository.SetupRepository;

@Service
public class PostService {
	
	private static Logger logger = LoggerFactory.getLogger(PostService.class);

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private AddressStatusRepository addressStatusRepository;
	
	@Autowired
	private SetupRepository setupRepository;
	
	public Status getStatus() {
		return this.addressStatusRepository.findById(AddressStatus.DEFAULT_ID)
				.orElse(AddressStatus.builder().status(Status.NEED_SETUP).build())
				.getStatus();
	}
	
	public Address getAddressByZipcode(String zipcode) throws NoContentException, NotReadyException {
		if (!this.getStatus().equals(Status.READY))
			throw new NotReadyException();
		return addressRepository.findById(zipcode)
				.orElseThrow(NoContentException::new);
	}
	
	
	private void saveStatus(Status status) {
		this.addressStatusRepository.save(AddressStatus.builder()
				.id(AddressStatus.DEFAULT_ID).status(status).build());
	}
	
	@EventListener(ApplicationStartedEvent.class)
	protected void setupStartUp() {
		try {
			this.setup();
		} catch (Exception e) {
			PostofApplication.close(888);
			logger.error(".setupStartup() - Exception", e);
		}
			
		}
	
	public void setup() throws Exception {
		logger.info("----");
		logger.info("----");
		logger.info("---- SETUP RUNNING ");
		logger.info("----");
		logger.info("----");
		
		if (this.getStatus().equals(Status.NEED_SETUP)) {
			this.saveStatus(Status.SETUP_RUNNING);
			try {
			this.addressRepository.saveAll(this.setupRepository.getFromOrigin());
	} catch(Exception e) {
		this.saveStatus(Status.NEED_SETUP);
		throw e;
	}
			this.saveStatus(Status.READY);	
}
		
		logger.info("----");
		logger.info("----");
		logger.info("----  SERVICE READY");
		logger.info("----");
		logger.info("----");
		
	}
}