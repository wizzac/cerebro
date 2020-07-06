package com.ar.cerebro.cerebro;

import com.ar.cerebro.cerebro.controller.MutantController;
import com.ar.cerebro.cerebro.controller.StatsController;
import com.ar.cerebro.cerebro.dto.MutantDto;
import com.ar.cerebro.cerebro.dto.StatsDto;
import com.ar.cerebro.cerebro.security.CustomForbiddenException;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CerebroApplicationTests {

	@Rule
	public ExpectedException thrown= ExpectedException.none();


	private MutantDto createMutantValid(){
		MutantDto dto =new MutantDto();
		List<String> dna = new ArrayList<>();
		dna.add("aaab");
		dna.add("aaab");
		dna.add("aaab");
		dna.add("aaab");
		dto.setDna(dna);
		return dto;
	}

	private MutantDto createMutantInvalid(){
		MutantDto dto =new MutantDto();
		List<String> dna = new ArrayList<>();
		dna.add("aajb");
		dna.add("aaab");
		dna.add("aaab");
		dto.setDna(dna);
		return dto;
	}

	@Test
	void testInsertMutant() throws Exception {
		MutantController mutantController=new MutantController();
		assert mutantController.isMutant(createMutantValid());
	}

	@Test
	void testInsertMutantTwice() throws Exception {
		MutantController mutantController=new MutantController();
		mutantController.isMutant(createMutantValid());
		assert mutantController.isMutant(createMutantValid());
	}

	@Test
	void testInsertMutantInvalid() throws Exception {
		thrown.expect(CustomForbiddenException.class);
		MutantController mutantController=new MutantController();
		assert !mutantController.isMutant(createMutantInvalid());
	}

	@Test
	void testMutantEmpty() throws Exception {
		thrown.expect(CustomForbiddenException.class);
		MutantController mutantController=new MutantController();
		MutantDto dto =new MutantDto();
		mutantController.isMutant(dto);
	}

	@Test
	void testStats() throws Exception {
		StatsController statsController=new StatsController();
		StatsDto dto=statsController.getStat();
		assert dto!=null;
	}

	@Test
	void testStatsCount() throws Exception {
		StatsController statsController=new StatsController();
		StatsDto dto=statsController.getStat();
		assert dto.getCountMutantDna() > 0;

	}

	@Test
	void testStatsCountHuman() throws Exception {
		StatsController statsController=new StatsController();
		StatsDto dto=statsController.getStat();
		assert dto.getCountHumanDna() > 0;

	}


}
