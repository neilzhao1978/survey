package com.neil.survey.util;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class SortTools {


    public static Sort basicSort(Direction d, String orderField) {
        Sort sort = new Sort(d, orderField);
        return sort;
    }
}
