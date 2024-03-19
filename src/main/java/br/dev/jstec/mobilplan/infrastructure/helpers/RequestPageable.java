package br.dev.jstec.mobilplan.infrastructure.helpers;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RequestPageable {

    private Integer size;
    private Integer totalElements;
    private Integer page;
    private Integer totalPages;
    private String sortBy;
    private SortDirection sortDirection;

    public enum SortDirection {
        ASC,
        DESC;
    }
    ;
}
