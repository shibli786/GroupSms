package com.example.shibli.toolbartoolbar;

/**
 * Created by shibli on 5/11/2016.
 */
public class Group  implements  Comparable{
    public String getGroupName() {
        return groupName;
    }

    String groupName;

    public Group(String groupName, int groupId) {
        this.groupName = groupName;
        this.groupId=groupId;

    }

    int  groupId;


    @Override
    public int compareTo(Object another) {
        Group g=(Group)another;
        return this.groupName.compareTo(g.groupName);
    }
}
