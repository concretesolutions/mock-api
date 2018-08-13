package br.com.elementalsource.mock.generic.repository.impl;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import br.com.elementalsource.mock.infra.property.FileProperty;

@Component
@ApplicationScope
public class ExistingFiles {
	@Autowired @Qualifier("FilePropertyModel")
	private FileProperty fileProperty;
	private List<ExistingFile> existingFiles;

	@PostConstruct
	public void setup() throws IOException {
		String fileBase = fileProperty.getFileBase();
		existingFiles = Files.walk(Paths.get(fileBase))
				.filter(Files::isRegularFile)
				.map(Path::getParent)
				.map(Path::toString)
				.map(ExistingFile::new)
				.sorted()
				.distinct()
				.collect(toList());
	}

	public List<ExistingFile> getExistingFiles(){
		return existingFiles;
	}
}
