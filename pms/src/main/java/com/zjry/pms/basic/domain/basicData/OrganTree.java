package com.zjry.pms.basic.domain.basicData;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zjry.common.core.domain.entity.SysMenu;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 单位科室树对象
 *
 * @author cyq
 * @date 2021-07-19
 */
@Data
public class OrganTree implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long tagId;
    private String label;
    private String tag;
    private boolean hasChildren;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<OrganTree> children;

    public OrganTree() {
    }

    public OrganTree(BasicdataDeptVo dept) {
        this.tag = "0";
        this.id = dept.getDeptId();
        this.tagId = dept.getDeptId();
        this.label = dept.getDeptName();
        this.hasChildren = dept.isHasChildren();
        this.children = (List)dept.getChildren().stream().map(OrganTree::new).collect(Collectors.toList());
    }

    public OrganTree(SysMenu menu) {
        this.id = menu.getMenuId();
        this.label = menu.getMenuName();
        this.children = (List)menu.getChildren().stream().map(OrganTree::new).collect(Collectors.toList());
    }

    public OrganTree(BasicdataDeptRoomVo basicdataDeptRoom) {
        this.tag = "1";
        this.id = basicdataDeptRoom.getDeptRoomId();
        this.tagId = basicdataDeptRoom.getDeptRoomId();
        this.label = basicdataDeptRoom.getDeptRoomName();
        this.hasChildren = basicdataDeptRoom.isHasChildren();
        this.children = (List)basicdataDeptRoom.getChildren().stream().map(OrganTree::new).collect(Collectors.toList());
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

    public List<OrganTree> getChildren() {
        return this.children;
    }

    public void setChildren(List<OrganTree> children) {
        this.children = children;
    }

    public boolean isHasChildren() {
        return this.hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }
}
