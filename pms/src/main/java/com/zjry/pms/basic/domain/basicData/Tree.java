package com.zjry.pms.basic.domain.basicData;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 树对象
 *
 * @author cyq
 * @date 2021-07-19
 */
public class Tree implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String label;
    private boolean hasChildren;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Tree> children;

    public Tree() {
    }

    public Tree(BasicdataDeptVo dept) {
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        this.hasChildren = dept.isHasChildren();
        this.children = (List)dept.getChildren().stream().map(Tree::new).collect(Collectors.toList());
    }

    public Tree(BasicdataMenu menu) {
        this.id = menu.getMenuId();
        this.label = menu.getMenuName();
        this.children = (List)menu.getChildren().stream().map(Tree::new).collect(Collectors.toList());
    }

    public Tree(BasicdataDeptRoomVo basicdataDeptRoom) {
        this.id = basicdataDeptRoom.getDeptRoomId();
        this.label = basicdataDeptRoom.getDeptRoomName();
        this.hasChildren = basicdataDeptRoom.isHasChildren();
        this.children = (List)basicdataDeptRoom.getChildren().stream().map(Tree::new).collect(Collectors.toList());
    }

    public Tree(DictDataTreeSelectVo dictDataTreeSelectVo) {
        this.id = dictDataTreeSelectVo.getDictCode();
        this.label = dictDataTreeSelectVo.getDictLabel();
        this.hasChildren = dictDataTreeSelectVo.isHasChildren();
        if(dictDataTreeSelectVo.getChildren() !=null){
            this.children = dictDataTreeSelectVo.getChildren().stream().map(Tree::new).collect(Collectors.toList());
        }
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Tree> getChildren() {
        return this.children;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }

    public boolean isHasChildren() {
        return this.hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }
}
