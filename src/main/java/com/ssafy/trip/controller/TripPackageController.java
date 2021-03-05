package com.ssafy.trip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.model.TripPackage;
import com.ssafy.trip.repository.TripPackageRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/tripPackage")
public class TripPackageController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private TripPackageRepository tripPackageRepository;
	
	@PostMapping
	public ResponseEntity<String> addTripPackage(@RequestBody TripPackage tripPackage) {
		tripPackageRepository.save(tripPackage);
		
		return ResponseEntity.ok(SUCCESS);
	}
	
	@PutMapping("/{num}")
	public ResponseEntity<String> modifyTripPackage(@PathVariable(value = "num") Long num,
			@RequestBody TripPackage tripPackage) {
		tripPackageRepository.save(tripPackage);

		return ResponseEntity.ok(SUCCESS);
	}
	
	@GetMapping("/{num}")
	public List<TripPackage> getTripPackage(@PathVariable(value = "num") Long num) {
		List<TripPackage> list = tripPackageRepository.findByUserNum(num);
		
		return list;
	}
	
	@DeleteMapping("/{num}")
	public ResponseEntity<String> deleteNeighborByUserNum(@PathVariable(value = "num") Long num) {
		tripPackageRepository.deleteByNum(num);

		return ResponseEntity.ok(SUCCESS);
	}
}
