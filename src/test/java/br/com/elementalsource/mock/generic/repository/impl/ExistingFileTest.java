package br.com.elementalsource.mock.generic.repository.impl;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExistingFileTest {

	@Test
	public void shouldMatchesSamePath(){
		String path = "/some/path";
		ExistingFile existingFile = new ExistingFile(path);

		ExistingFile.PathParamExtractor paramExtractor = existingFile.extract(path);

		assertTrue(paramExtractor.matches());
		assertEquals(0, paramExtractor.getParameters().size());

	}

	@Test
	public void shouldMatchesAPathWithAVariable(){
		String filePath = "/some/#{{variable}}/path";
		String urlPath = "/some/value/path";

		ExistingFile existingFile = new ExistingFile(filePath);

		ExistingFile.PathParamExtractor paramExtractor = existingFile.extract(urlPath);

		assertTrue(paramExtractor.matches());
		assertEquals(1, paramExtractor.getParameters().size());
		assertEquals("value", paramExtractor.getParameters().get("variable"));

	}

	@Test
	public void shouldMatchesAPathWithMultipleVariables(){
		String filePath = "/some/#{{variable1}}/path/#{{variable2}}";
		String urlPath = "/some/value/path/another";

		ExistingFile existingFile = new ExistingFile(filePath);

		ExistingFile.PathParamExtractor paramExtractor = existingFile.extract(urlPath);

		assertTrue(paramExtractor.matches());
		assertEquals(2, paramExtractor.getParameters().size());
		assertEquals("value", paramExtractor.getParameters().get("variable1"));
		assertEquals("another", paramExtractor.getParameters().get("variable2"));

	}

}