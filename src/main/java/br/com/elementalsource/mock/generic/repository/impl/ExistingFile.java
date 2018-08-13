package br.com.elementalsource.mock.generic.repository.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.MoreObjects;

public class ExistingFile implements Comparable<ExistingFile> {

	private final Pattern regex;
	private final String originalPath;

	ExistingFile(String path) {
		originalPath = path;
		regex = Pattern.compile(path.replaceAll("#\\{\\{\\w*\\}\\}", "(.*)"));
	}

	public PathParamExtractor extract(String rawPath) {
		return new PathParamExtractor(rawPath);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("regex", regex)
				.add("originalPath", originalPath)
				.toString();
	}

	@Override
	public int compareTo(final ExistingFile o) {
		return this.originalPath.compareTo(o.originalPath) * -1;
	}

	public class PathParamExtractor {

		private boolean matches;
		private Map<String, String> parameters = new HashMap<>();

		public PathParamExtractor(String rawPath ) {
			Matcher rawPathMatcher = regex.matcher(rawPath);
			Matcher originalPathMatcher = regex.matcher(originalPath);

			matches = rawPathMatcher.matches() && originalPathMatcher.matches();

			if(matches){
				for (int i = 1; i <= rawPathMatcher.groupCount(); i++){
					parameters.put(originalPathMatcher.group(i).replaceAll("\\W", ""),rawPathMatcher.group(i));
				}

			}
		}

		public boolean matches() {
			return matches;
		}

		public Map<String, String> getParameters() {
			return parameters;
		}

		public String getOriginalPath(){
			return originalPath;
		}
	}



}
