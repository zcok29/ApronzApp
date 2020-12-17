package com.example.macExplore;

import java.util.Comparator;
import java.util.List;

public class CommentComparator implements Comparator<Comment> {

    @Override
    public int compare(Comment c1, Comment c2) {
        return -Integer.compare(Integer.parseInt(c1.getTimestamp()),Integer.parseInt(c2.getTimestamp()));
    }
}
