package com.ssafy.trip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.model.Neighbor;
import com.ssafy.trip.repository.NeighborRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/neighbor")
public class NeighborController {
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private NeighborRepository neighborRepository;

	@PostMapping
	public ResponseEntity<String> addNeighbor(@RequestBody Neighbor neighbor) {
		neighborRepository.findByUsernumPaging(neighbor.getUserNum(), neighbor.getNeighborNum());

		return ResponseEntity.ok(SUCCESS);
	}

	@GetMapping("/{userNum}")
	public List<Neighbor> getNeighborByUserNum(@PathVariable(value = "userNum") Long userNnum) {
		List<Neighbor> list = neighborRepository.findByUserNumOrderByNeighborNum(userNnum);
		return list;
	}

	@DeleteMapping("/{userNum}/{neighborNum}")
	public ResponseEntity<String> deleteNeighborByUserNum(@PathVariable(value = "userNum") Long userNnum,
			@PathVariable(value = "neighborNum") Long neighborNum) {
		neighborRepository.deleteByUserNumAndNeighborNum(userNnum, neighborNum);

		return ResponseEntity.ok(SUCCESS);
	}
}
