package br.com.elementasource.mock.infra.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class DefaultHeader implements Serializable {

    private static final long serialVersionUID = 6319619000946506689L;

    private String headerName;
    private List<String> headerValues;

    public DefaultHeader(String headerName, List<String> headerValues) {
        this.headerName = headerName;
        this.headerValues = headerValues;
    }

    public DefaultHeader() {

    }
    // String headerName
    // String... headerValues;

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public void setHeaderValues(List<String> headerValues) {
        this.headerValues = headerValues;
    }

    public String getHeaderName() {
        return headerName;
    }

    public List<String> getHeaderValues() {
        return headerValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultHeader that = (DefaultHeader) o;
        return Objects.equals(headerName, that.headerName) &&
                Objects.equals(headerValues, that.headerValues);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headerName, headerValues);
    }
}
