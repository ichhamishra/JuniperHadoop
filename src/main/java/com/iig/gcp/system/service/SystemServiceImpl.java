package com.iig.gcp.system.service;

import java.sql.SQLException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iig.gcp.extraction.dto.CountryMaster;
import com.iig.gcp.system.dao.SystemDAO;

@Service
public class SystemServiceImpl implements SystemService {
	
	@Autowired
	SystemDAO systemDAO;

	@Override
	public List<CountryMaster> fetchCountries(@Valid String region) {
		return systemDAO.fetchCountries(region);
	}

	@Override
	public int checkEIM(@Valid String system_eim) {
		return systemDAO.checkEIM(system_eim);
	}

	@Override
	public String registerSystem(@Valid String systemEIM, String systemName, String region, String country,
			 String environmentType, String userName) throws ClassNotFoundException, SQLException {
		return systemDAO.registerSystem(systemEIM,systemName,region,country,environmentType,userName);
	}
}
