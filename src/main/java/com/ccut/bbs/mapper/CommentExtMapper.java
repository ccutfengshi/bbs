package com.ccut.bbs.mapper;

import com.ccut.bbs.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}