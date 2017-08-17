package br.com.concrete.mock.generic.model;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Endpoint implements Comparable<Endpoint> {

	private final Optional<String> id;
	private final Request request;
	private final Response response;

	@JsonCreator
	public Endpoint(@JsonProperty("id") Optional<String> id, @JsonProperty("request") Request request,
			@JsonProperty("response") Response response) {
		this.id = id;
		this.request = request;
		this.response = response;
	}

	@JsonIgnore
	public Optional<String> getId() {
		return id;
	}

	@JsonProperty
	public Request getRequest() {
		return request;
	}

	@JsonProperty
	public Response getResponse() {
		return response;
	}

	@Override
	public int compareTo(Endpoint o) {
		return this.request.compareTo(o.request);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Endpoint endpoint = (Endpoint) o;
		return Objects.equals(id, endpoint.id) &&
				Objects.equals(request, endpoint.request) &&
				Objects.equals(response, endpoint.response);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, request, response);
	}

	public static class Builder {

		private Optional<String> id;
		private Request request;
		private Response response;

		public Builder(Request request, Response response) {
			this.request = request;
			this.response = response;
		}

		public Builder(final Endpoint endpoint) {
			this.id = endpoint.id;
			this.request = endpoint.request;
			this.response = endpoint.response;
		}

		public Builder withId(final Optional<String> id) {
			this.id = id;
			return this;
		}

		public Builder withId(final String id) {
			return withId(Optional.ofNullable(id));
		}

		public Builder(Request request, ResponseEntity<String> responseEntity) {
			this(request, new Response(responseEntity.getBody(), Optional.ofNullable(responseEntity.getStatusCode())));
		}

		public Builder(RequestMethod method, String url, Response response) {
			withEmptyRequest(method, url);
			this.response = response;
		}

		public Builder withRequest(Optional<Map<String, String>> requestQuery, Optional<String> requestBody) {
			return withRequest(new Request.Builder(this.request).withQuery(requestQuery).withBody(requestBody).build());
		}

		public Builder withRequest(Request request) {
			this.request = request;
			return this;
		}

		public Builder withEmptyRequest(RequestMethod method, String url) {
			return withRequest(new Request.Builder(method, url).build());
		}

		public Endpoint build() {
			return new Endpoint(id, request, response);
		}

	}

}
