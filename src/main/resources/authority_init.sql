USE ${database};

SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE tb_sys_menu;
TRUNCATE TABLE tb_area;
TRUNCATE TABLE tb_org;
TRUNCATE TABLE tb_sys_role;
TRUNCATE TABLE tb_sys_role_menu;
TRUNCATE TABLE tb_sys_user;
TRUNCATE TABLE tb_login_log;
TRUNCATE TABLE tb_system_config;
TRUNCATE TABLE tb_sys_log;

INSERT INTO tb_area(id, `name`) VALUES(NULL, '根节点');

INSERT INTO tb_org(id, `name`) VALUES(NULL, '根节点');

INSERT INTO tb_sys_menu(id, display, menu_name) VALUES(NULL, 0, '根节点');
INSERT INTO tb_sys_menu(id, display, icon_cls, menu_name, sort, parent_id)
VALUES(NULL, 1, 'Hui-iconfont Hui-iconfont-manage', '系统管理', 1, 1);
INSERT INTO tb_sys_menu(id, display, icon_cls, menu_name, menu_url, sort, parent_id)
VALUES(NULL, 1, 'Hui-iconfont Hui-iconfont-unordered-list', '菜单管理', '/admin/menu', 1, 2);
INSERT INTO tb_sys_menu(id, display, icon_cls, menu_name, menu_url, sort, parent_id)
VALUES(NULL, 1, 'Hui-iconfont Hui-iconfont-home', '区域管理', '/admin/area', 2, 2);
INSERT INTO tb_sys_menu(id, display, icon_cls, menu_name, menu_url, sort, parent_id)
VALUES(NULL, 1, 'Hui-iconfont Hui-iconfont-gongsi', '机构管理', '/admin/org', 3, 2);
INSERT INTO tb_sys_menu(id, display, icon_cls, menu_name, menu_url, sort, parent_id)
VALUES(NULL, 1, 'Hui-iconfont Hui-iconfont-zhizhao', '角色管理', '/admin/role', 4, 2);
INSERT INTO tb_sys_menu(id, display, icon_cls, menu_name, menu_url, sort, parent_id)
VALUES(NULL, 1, 'Hui-iconfont Hui-iconfont-user', '用户管理', '/admin/user', 5, 2);
INSERT INTO tb_sys_menu(id, display, icon_cls, menu_name, sort, parent_id)
VALUES(NULL, 1, 'Hui-iconfont Hui-iconfont-tongji-bing', '查询统计', 1, 1);
INSERT INTO tb_sys_menu(id, display, icon_cls, menu_name, menu_url, sort, parent_id)
VALUES(NULL, 1, 'Hui-iconfont Hui-iconfont-log', '系统登录日志', '/admin/log/login', 1, 8);
INSERT INTO tb_sys_menu(id, display, icon_cls, menu_name, menu_url, sort, parent_id)
VALUES(NULL, 1, 'Hui-iconfont Hui-iconfont-feedback2', '系统日志', '/admin/log/sys', 2, 8);

INSERT INTO tb_org(id, `name`) VALUES(NULL, '根节点');

INSERT INTO tb_sys_role(id, `name`) VALUES(NULL, '超级管理员');

INSERT INTO tb_sys_role_menu(role_id, menu_id) VALUES(1, 2);
INSERT INTO tb_sys_role_menu(role_id, menu_id) VALUES(1, 3);
INSERT INTO tb_sys_role_menu(role_id, menu_id) VALUES(1, 4);
INSERT INTO tb_sys_role_menu(role_id, menu_id) VALUES(1, 5);
INSERT INTO tb_sys_role_menu(role_id, menu_id) VALUES(1, 6);
INSERT INTO tb_sys_role_menu(role_id, menu_id) VALUES(1, 7);

INSERT INTO tb_sys_user(id, `password`, `status`, super_admin, username, role_id)
VALUES(NULL, MD5('111111'), 0, 1, 'admin', 1);

INSERT INTO tb_system_config(id, `code`, `value`, description)
VALUES(NULL, 'authority_init_completed', 'false', '权限模块是否初始化完成');

SET FOREIGN_KEY_CHECKS = 1;