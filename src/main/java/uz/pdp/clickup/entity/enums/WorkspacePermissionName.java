package uz.pdp.clickup.entity.enums;

import java.util.Arrays;
import java.util.List;

public enum WorkspacePermissionName {
    CAN_ADD_MEMBER("CAN_ADD_MEMBER", "Gives User permission to add or remove members to the workspace",
            Arrays.asList(WorkspaceRoleName.ROLE_OWNER,WorkspaceRoleName.ROLE_ADMIN)),
    CAN_REMOVE_MEMBER("CAN_REMOVE_MEMBER", "Gives the user the permission to remove members to the Workspace",
            Arrays.asList(WorkspaceRoleName.ROLE_OWNER, WorkspaceRoleName.ROLE_ADMIN)),
    CAN_EDIT_WORKSPACE("CAN_EDIT_WORKSPACE", "Gives User permission to edit workspace",
            Arrays.asList(WorkspaceRoleName.ROLE_OWNER,WorkspaceRoleName.ROLE_ADMIN)),
    CAN_ADD_GUEST("CAN_ADD_GUEST", "Gives User permission to add guests",
            Arrays.asList(WorkspaceRoleName.ROLE_OWNER,WorkspaceRoleName.ROLE_ADMIN)),
    CAN_SEE_TIME_ESTIMATED("CAN_SEE_TIME_ESTIMATED", "Gives User permission to see time estimated",
            Arrays.asList(WorkspaceRoleName.ROLE_OWNER,WorkspaceRoleName.ROLE_ADMIN)),
    CAN_SEE_TIME_SPENT("CAN_SEE_TIME_SPENT", "Gives User permission to see time spent",
            Arrays.asList(WorkspaceRoleName.ROLE_OWNER,WorkspaceRoleName.ROLE_ADMIN)),
    CAN_CREATE_SPACE("CAN_CREATE_SPACE", "Gives User permission to create space",
            Arrays.asList(WorkspaceRoleName.ROLE_OWNER,WorkspaceRoleName.ROLE_ADMIN)),
    CAN_CREATE_FOLDER("CAN_CREATE_FOLDER", "Gives User permission to create folder",
            Arrays.asList(WorkspaceRoleName.ROLE_OWNER,WorkspaceRoleName.ROLE_ADMIN)),
    CAN_CREATE_LISTS("CAN_CREATE_LISTS", "Gives User permission to create lists",
            Arrays.asList(WorkspaceRoleName.ROLE_OWNER,WorkspaceRoleName.ROLE_ADMIN)),
    CAN_DELETE_COMMENTS("CAN_DELETE_COMMENTS", "Gives User permission to delete comments",
            Arrays.asList(WorkspaceRoleName.ROLE_OWNER,WorkspaceRoleName.ROLE_ADMIN)),
    CAN_DELETE_ITEMS("CAN_DELETE_ITEMS", "Gives User permission to delete items",
            Arrays.asList(WorkspaceRoleName.ROLE_OWNER,WorkspaceRoleName.ROLE_ADMIN)),
    CAN_EDIT_DESCRIPTION("CAN_EDIT_DESCRIPTION", "Gives User permission to edit description",
            Arrays.asList(WorkspaceRoleName.ROLE_OWNER,WorkspaceRoleName.ROLE_ADMIN)),
    CAN_ADD_LIST_STATUSES("CAN_ADD_LIST_STATUSES", "Gives User permission to add list statuses",
            Arrays.asList(WorkspaceRoleName.ROLE_OWNER,WorkspaceRoleName.ROLE_ADMIN)),
    CAN_EDIT_LIST_STATUSES("CAN_EDIT_LIST_STATUSES", "Gives User permission to edit list statuses",
            Arrays.asList(WorkspaceRoleName.ROLE_OWNER,WorkspaceRoleName.ROLE_ADMIN)),
    CAN_EDIT_TEAM("CAN_EDIT_TEAM", "Gives User permission to edit team",
            Arrays.asList(WorkspaceRoleName.ROLE_OWNER,WorkspaceRoleName.ROLE_ADMIN)),
    CAN_EXPORT_TASKS("CAN_EXPORT_TASKS", "Gives User permission to export tasks",
            Arrays.asList(WorkspaceRoleName.ROLE_OWNER,WorkspaceRoleName.ROLE_ADMIN)),
    CAN_EDIT_TEAM_OWNER("CAN_EDIT_TEAM_OWNER", "Gives User permission to edit team owner",
            Arrays.asList(WorkspaceRoleName.ROLE_OWNER,WorkspaceRoleName.ROLE_ADMIN)),
    CAN_MANAGE_TAGS("CAN_MANAGE_TASKS", "Gives User permission to manage tasks",
            Arrays.asList(WorkspaceRoleName.ROLE_OWNER,WorkspaceRoleName.ROLE_ADMIN,WorkspaceRoleName.ROLE_MEMBER)),
    CAN_SHARE("CAN_SHARE", "Gives User permission to share",
            Arrays.asList(WorkspaceRoleName.ROLE_OWNER,WorkspaceRoleName.ROLE_ADMIN,WorkspaceRoleName.ROLE_MEMBER)),
    CAN_MANAGE_STATUSES("CAN_MANAGE_STATUSES", "Gives User permission to manage statuses",
            Arrays.asList(WorkspaceRoleName.ROLE_OWNER,WorkspaceRoleName.ROLE_ADMIN)),
    CAN_SEE_TEAM_MEMBERS("CAN_SEE_TEAM_MEMBERS", "Gives User permission to see team members",
            Arrays.asList(WorkspaceRoleName.ROLE_OWNER,WorkspaceRoleName.ROLE_ADMIN)),
    CAN_ADD_ROLE("CAN_ADD_ROLE", "Gives User permission to add role",
            Arrays.asList(WorkspaceRoleName.ROLE_OWNER,WorkspaceRoleName.ROLE_ADMIN)),
    CAN_CHANGE_PERMISSION("CAN_CHANGE_PERMISSION", "Gives User permission to change permission",
            Arrays.asList(WorkspaceRoleName.ROLE_OWNER,WorkspaceRoleName.ROLE_ADMIN));


    public final String name;
    public final String description;
    public final List<WorkspaceRoleName> workspaceRoleNames;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<WorkspaceRoleName> getWorkspaceRoleNames() {
        return workspaceRoleNames;
    }

    WorkspacePermissionName(String name, String description,List<WorkspaceRoleName> workspaceRoleNames) {
        this.name = name;
        this.description = description;
        this.workspaceRoleNames = workspaceRoleNames;
    }
}
