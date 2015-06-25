package com.mezzalira.web.comparator;

import com.mezzalira.web.util.Termo;

import java.util.Comparator;

/**
 * Created by cezar on 05/06/15.
 */
public class TermoComparator implements Comparator<Termo> {

    @Override
    public int compare(Termo termo1, Termo termo2) {
        return termo1.getTermo().compareTo(termo2.getTermo());
    }


}
