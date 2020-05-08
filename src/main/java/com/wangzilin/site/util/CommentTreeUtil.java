package com.***REMOVED***.site.util;

import com.***REMOVED***.site.model.blog.CommentTree;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ***REMOVED***n@gmail.com
 * @Description:
 * @Date: Created in 11:37 PM 5/8/2020
 * @Modified By:***REMOVED***n@gmail.com
 */
public class CommentTreeUtil {
    public static <T> List<CommentTree<T>> build(List<CommentTree<T>> nodes) {
        if (nodes == null) {
            return new ArrayList<>();
        }

        List<CommentTree<T>> CommentTree = new ArrayList<>();
        nodes.forEach(node -> {
            Long pId = node.getPId();
            if (pId == null || pId.equals(0L)) {
                //父节点
                CommentTree.add(node);
                return;
            }
            for (CommentTree<T> c : nodes) {
                Long id = c.getId();
                if (id != null && id.equals(pId)) {
                    c.getChildren().add(node);
                    return;
                }
            }
        });
        return CommentTree;
    }
}
