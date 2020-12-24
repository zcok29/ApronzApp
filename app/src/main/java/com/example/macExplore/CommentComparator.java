package com.example.macExplore;

import java.util.Comparator;
import java.util.List;

public class CommentComparator implements Comparator<Comment> {

    /**
     * A comparator that sorts the comment according to the time stamp from the latest comment to older ones.
     * @param c1
     * @param c2
     * @return
     */
    @Override
    public int compare(Comment c1, Comment c2) {
        return -Long.compare(c1.getTimestamp(),c2.getTimestamp());
    }
}
