package com.sangbill.netty.domain.vo;

import com.sangbill.netty.domain.entity.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class GroupVO {
    private Integer id;
    private String groupName;

    public GroupVO(Group group) {
        this.id = group.getId();
        this.groupName = group.getGroupName();
    }
}
