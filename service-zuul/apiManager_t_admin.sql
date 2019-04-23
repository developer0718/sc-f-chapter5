CREATE TABLE apiManager.t_admin
(
    userId varchar(32) PRIMARY KEY NOT NULL COMMENT '用户id',
    username varchar(32) NOT NULL COMMENT '用户名',
    password varchar(32) NOT NULL COMMENT '密码'
);
INSERT INTO apiManager.t_admin (userId, username, password) VALUES ('1', 'tanzj', 'tanzj');
CREATE TABLE apiManager.t_permission
(
    serviceId varchar(32) NOT NULL COMMENT '服务id',
    permissionId varchar(32) PRIMARY KEY NOT NULL COMMENT '权限id',
    permissionType varchar(10) NOT NULL COMMENT 'menu-菜单
other-其他',
    permissionName varchar(50) NOT NULL COMMENT '资源名称',
    resource text COMMENT '权限资源',
    parentId varchar(32) COMMENT '父目录资源',
    state int(11) COMMENT '1-有效2-禁用',
    createTime datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    createUser varchar(32) COMMENT '创建人',
    updateTime datetime COMMENT '修改时间',
    updateUser varchar(32) COMMENT '创建人'
);
INSERT INTO apiManager.t_permission (serviceId, permissionId, permissionType, permissionName, resource, parentId, state, createTime, createUser, updateTime, updateUser) VALUES ('1', '1', 'other', '测试接口', '/api/test', null, 1, '2019-04-11 18:28:04', 'tanzj', null, null);
INSERT INTO apiManager.t_permission (serviceId, permissionId, permissionType, permissionName, resource, parentId, state, createTime, createUser, updateTime, updateUser) VALUES ('1', '3434', 'other', '测试接口', '/test/test/api', '', 1, '2019-04-11 19:26:38', 'tanzjjj', null, null);
INSERT INTO apiManager.t_permission (serviceId, permissionId, permissionType, permissionName, resource, parentId, state, createTime, createUser, updateTime, updateUser) VALUES ('1', 'as5ds6', 'other', '测试接口2', '/test/test/apisdsdsdsdsd', null, 1, '2019-04-11 19:42:51', 'tanzjjj', null, null);
CREATE TABLE apiManager.t_role
(
    roleId varchar(32) PRIMARY KEY NOT NULL COMMENT '角色id',
    roleName varchar(50) NOT NULL COMMENT '角色名',
    state int(11) NOT NULL COMMENT '1-有效 2-禁用 3-删除',
    roleDetail text COMMENT '角色描述',
    createTime datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    createUser varchar(32) NOT NULL COMMENT '创建人',
    updateUser varchar(32) COMMENT '更新人',
    updatetime datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间'
);
INSERT INTO apiManager.t_role (roleId, roleName, state, roleDetail, createTime, createUser, updateUser, updatetime) VALUES ('1', '新角色修改', 1, '这是一条描述', '2019-04-11 19:11:21', 'tanzj', 'tanzj', '2019-04-12 16:11:19');
INSERT INTO apiManager.t_role (roleId, roleName, state, roleDetail, createTime, createUser, updateUser, updatetime) VALUES ('34b963e8a46c482f8a361f43b9759f59', '测试角色', 1, '这是一个角色', '2019-04-12 16:14:18', 'tanzj', null, '2019-04-12 16:14:18');
INSERT INTO apiManager.t_role (roleId, roleName, state, roleDetail, createTime, createUser, updateUser, updatetime) VALUES ('92178f101056421b8af072b574ff9b0e', '测试角色', 1, '这是一个角色', '2019-04-12 16:50:51', 'tanzj', null, '2019-04-12 16:50:51');
INSERT INTO apiManager.t_role (roleId, roleName, state, roleDetail, createTime, createUser, updateUser, updatetime) VALUES ('999b029354cc4a808450bdbd825dac0b', '测试角色', 1, '这是一个角色', '2019-04-12 16:12:57', 'tanzj', null, '2019-04-12 16:12:57');
INSERT INTO apiManager.t_role (roleId, roleName, state, roleDetail, createTime, createUser, updateUser, updatetime) VALUES ('ab19303625e747d09e96b4d5ad4c1ec6', '测试角色222222', 3, '这是一个角色', '2019-04-12 15:22:47', 'tanzj', null, '2019-04-12 16:12:57');
INSERT INTO apiManager.t_role (roleId, roleName, state, roleDetail, createTime, createUser, updateUser, updatetime) VALUES ('f2fbbe163670426681d3dd608f87842a', '测试角色', 1, '这是一个角色', '2019-04-12 15:45:13', 'tanzj', null, '2019-04-12 16:06:38');
INSERT INTO apiManager.t_role (roleId, roleName, state, roleDetail, createTime, createUser, updateUser, updatetime) VALUES ('f3b726bcc7b745c58471eca5843a33df', '测试角色', 1, '这是一个角色', '2019-04-12 17:06:16', 'tanzj', null, '2019-04-12 17:06:16');
CREATE TABLE apiManager.t_role_permission
(
    id int(11) PRIMARY KEY NOT NULL COMMENT '默认id' AUTO_INCREMENT,
    roleId varchar(32) NOT NULL COMMENT '角色id',
    permissionId varchar(32) NOT NULL COMMENT '权限id'
);
INSERT INTO apiManager.t_role_permission (id, roleId, permissionId) VALUES (1, '1', 'as5ds6');
CREATE TABLE apiManager.t_service
(
    serviceId varchar(32) PRIMARY KEY NOT NULL COMMENT '服务id',
    serviceName varchar(15) NOT NULL COMMENT '服务名',
    sign varchar(50) NOT NULL COMMENT '签名密码',
    state int(11) NOT NULL COMMENT '1-有效 2-禁用 3-删除',
    createTime datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    createUser varchar(32) NOT NULL COMMENT '创建人',
    updateTime datetime COMMENT '更新时间',
    updateUser varchar(32) COMMENT '更新人'
);
CREATE UNIQUE INDEX t_service_serviceName_uindex ON apiManager.t_service (serviceName);
INSERT INTO apiManager.t_service (serviceId, serviceName, sign, state, createTime, createUser, updateTime, updateUser) VALUES ('1', 'tanzj', 'tanzj', 1, '2019-04-11 17:17:42', 'tanzj', null, null);
INSERT INTO apiManager.t_service (serviceId, serviceName, sign, state, createTime, createUser, updateTime, updateUser) VALUES ('1111', 'test', '123', 0, '2019-04-12 11:48:32', 'tanzj', null, null);
INSERT INTO apiManager.t_service (serviceId, serviceName, sign, state, createTime, createUser, updateTime, updateUser) VALUES ('12d68079841848e38b5e637ac8806da1', 'tes22t', '123', 0, '2019-04-12 11:57:40', 'tanzj', null, null);
INSERT INTO apiManager.t_service (serviceId, serviceName, sign, state, createTime, createUser, updateTime, updateUser) VALUES ('2e0c5ebba5d8472bb961d84a763f3ede', '5ce1899152fb40', '123', 0, '2019-04-12 16:12:57', 'tanzj', null, null);
INSERT INTO apiManager.t_service (serviceId, serviceName, sign, state, createTime, createUser, updateTime, updateUser) VALUES ('65f69bf54ee54c5b8da156d107c9967e', '0ec7b70c835f45', '123', 0, '2019-04-12 14:09:40', 'tanzj', null, null);
INSERT INTO apiManager.t_service (serviceId, serviceName, sign, state, createTime, createUser, updateTime, updateUser) VALUES ('a79a5c14f9cb4b7482de1f737bfe3f4c', 'b84b556ae4e14e', '123', 0, '2019-04-12 16:14:18', 'tanzj', null, null);
INSERT INTO apiManager.t_service (serviceId, serviceName, sign, state, createTime, createUser, updateTime, updateUser) VALUES ('b60aa4a02463410fae990240804012a5', 'dfd4423f3b7c4e', '123', 0, '2019-04-12 12:01:58', 'tanzj', null, null);
INSERT INTO apiManager.t_service (serviceId, serviceName, sign, state, createTime, createUser, updateTime, updateUser) VALUES ('c194e10b369a4188b4e32c2ab306e290', '20d7773ef4254f', '123', 0, '2019-04-12 17:06:16', 'tanzj', null, null);
INSERT INTO apiManager.t_service (serviceId, serviceName, sign, state, createTime, createUser, updateTime, updateUser) VALUES ('cc36b1fff1af4ec1895385dcdf648108', 'eb27212b5e2247', '123', 0, '2019-04-12 15:45:13', 'tanzj', null, null);
INSERT INTO apiManager.t_service (serviceId, serviceName, sign, state, createTime, createUser, updateTime, updateUser) VALUES ('f798f2ff81af45fe969c3b5e40af85b7', '4786ffb9573d42', '123', 0, '2019-04-12 16:50:51', 'tanzj', null, null);
CREATE TABLE apiManager.t_service_role
(
    id int(11) PRIMARY KEY NOT NULL COMMENT '默认id' AUTO_INCREMENT,
    serviceId varchar(32) NOT NULL COMMENT '服务id',
    roleId varchar(32) NOT NULL COMMENT '角色id'
);
INSERT INTO apiManager.t_service_role (id, serviceId, roleId) VALUES (3, '1', '1');
INSERT INTO apiManager.t_service_role (id, serviceId, roleId) VALUES (4, '1', '1');
INSERT INTO apiManager.t_service_role (id, serviceId, roleId) VALUES (5, '1', '1');
INSERT INTO apiManager.t_service_role (id, serviceId, roleId) VALUES (6, '1', '1');
INSERT INTO apiManager.t_service_role (id, serviceId, roleId) VALUES (7, '1', '1');
INSERT INTO apiManager.t_service_role (id, serviceId, roleId) VALUES (8, '1', '1');