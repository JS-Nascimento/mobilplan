package br.dev.jstec.mobilplan.infrastructure.helpers;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePageable<T> {

    private List<T> content;
    private int size;
    private long totalElements;
    private int page;
    private int totalPages;
    private boolean first;
    private boolean last;
    private boolean empty;
}
