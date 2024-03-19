package br.dev.jstec.mobilplan.infrastructure.helpers;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationHelper {

    public static PageRequest convertToPageRequest(RequestPageable pageable) {
        return convertToPageRequest(pageable, null);
    }

    public static PageRequest convertToPageRequest(RequestPageable pageable, String defaultSortBy) {
        PageRequest pageRequest;

        pageable.setPage((pageable.getPage() == null) ? 0 : pageable.getPage());
        pageable.setSize((pageable.getSize() == null) ? 10 : pageable.getSize());

        pageRequest = PageRequest.of(pageable.getPage(), pageable.getSize());

        Sort.Direction dir = Sort.Direction.ASC;
        if (pageable.getSortDirection() != null
                && Sort.Direction.DESC.name().equalsIgnoreCase(pageable.getSortDirection().name())) {
            dir = Sort.Direction.DESC;
        }

        String sortBy = defaultSortBy;
        if (pageable.getSortBy() != null && !pageable.getSortBy().isBlank()) {
            sortBy = pageable.getSortBy();
        }

        if (sortBy != null) {
            pageRequest = pageRequest.withSort(Sort.by(dir, sortBy));
        }

        return pageRequest;
    }

    public static <O, T> ResponsePageable<O> pageToResponsePage(Page<T> page) {
        ResponsePageable<O> responsePageable = new ResponsePageable<>();

        responsePageable.setPage(page.getNumber());
        responsePageable.setFirst(page.isFirst());
        responsePageable.setLast(page.isLast());
        responsePageable.setSize(page.getSize());
        responsePageable.setTotalElements(page.getTotalElements());
        responsePageable.setTotalPages(page.getTotalPages());

        return responsePageable;
    }


    public static <O, T> ResponsePageable<O> pageToResponsePage(Page<T> page, List<O> list) {
        ResponsePageable<O> responsePageable = pageToResponsePage(page);

        responsePageable.setContent(list);

        return responsePageable;
    }

    public static <O, T> ResponsePageable<O> pageToResponsePage(Page<T> page, Function<T, O> function) {
        ResponsePageable<O> responsePageable = pageToResponsePage(page);


        List<O> list = page.getContent().stream().map(function).collect(Collectors.toList());
        responsePageable.setContent(list);

        return responsePageable;
    }

    public static <T> Page<T> listToPage(List<T> list, Pageable pageable) {
        if (pageable.getOffset() > list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size()
                ? list.size() :
                pageable.getOffset() + pageable.getPageSize());
        List<T> subList = list.subList(startIndex, endIndex);
        return new PageImpl<>(subList, pageable, list.size());
    }

    public static <T> ResponsePageable<T> getPaginatedResponse(List<T> list, RequestPageable pageable) {

        var pageRequest = convertToPageRequest(pageable);
        Page<T> pageResponse = listToPage(list, pageRequest);

        return pageToResponsePage(pageResponse, pageResponse.getContent());
    }
}
