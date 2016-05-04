/*
Navicat MySQL Data Transfer

Source Server         : metis
Source Server Version : 50524
Source Host           : 56d6b10631055.sh.cdb.myqcloud.com:4003
Source Database       : test2

Target Server Type    : MYSQL
Target Server Version : 50524
File Encoding         : 65001

Date: 2016-04-19 10:38:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `d_answer`
-- ----------------------------
DROP TABLE IF EXISTS `d_answer`;
CREATE TABLE `d_answer` (
  `answer_id` int(11) NOT NULL AUTO_INCREMENT,
  `enroll_num` int(11) NOT NULL,
  `en_question_id` int(11) DEFAULT NULL,
  `en_answer` text,
  `pro_question_id` int(11) DEFAULT NULL,
  `pro_answer` text,
  `peo_question_id` int(11) DEFAULT NULL,
  `peo_answer` text,
  PRIMARY KEY (`answer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=149 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of d_answer
-- ----------------------------
INSERT INTO `d_answer` VALUES ('134', '1517', '1', null, '6', null, '7', null);
INSERT INTO `d_answer` VALUES ('135', '1519', '3', null, '5', null, '8', null);
INSERT INTO `d_answer` VALUES ('136', '1518', '2', null, '6', null, '9', null);
INSERT INTO `d_answer` VALUES ('145', '1573', '22', null, '6', null, '21', null);
INSERT INTO `d_answer` VALUES ('146', '1571', '23', null, '5', null, '24', null);
INSERT INTO `d_answer` VALUES ('147', '1570', '22', null, '6', null, '25', null);
INSERT INTO `d_answer` VALUES ('148', '1572', '23', null, '6', null, '26', null);

-- ----------------------------
-- Table structure for `d_enroll`
-- ----------------------------
DROP TABLE IF EXISTS `d_enroll`;
CREATE TABLE `d_enroll` (
  `enroll_num` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `test_id` int(11) NOT NULL,
  `awards` varchar(255) DEFAULT NULL,
  `enroll_status` int(11) NOT NULL,
  `school_id` int(11) DEFAULT NULL,
  `test_num` int(11) DEFAULT NULL,
  PRIMARY KEY (`enroll_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of d_enroll
-- ----------------------------
INSERT INTO `d_enroll` VALUES ('1566', '44', '4', '中国好声音第一名', '2', '20', '2147483647');
INSERT INTO `d_enroll` VALUES ('1567', '48', '4', '2016年nba状元秀', '2', '20', '2147483647');
INSERT INTO `d_enroll` VALUES ('1568', '49', '4', '少管所围棋大赛一等奖', '2', '20', '2147483647');
INSERT INTO `d_enroll` VALUES ('1569', '50', '4', '公安局技巧大赛一等奖', '2', '20', '2147483647');
INSERT INTO `d_enroll` VALUES ('1570', '51', '4', '2016年nba状元秀', '2', '20', '2147483647');
INSERT INTO `d_enroll` VALUES ('1571', '52', '4', '少管所围棋大赛一等奖', '2', '20', '2147483647');
INSERT INTO `d_enroll` VALUES ('1572', '53', '4', '公安局技巧大赛一等奖', '2', '20', '2147483647');
INSERT INTO `d_enroll` VALUES ('1573', '54', '4', '中国好声音制作人', '2', '20', '2147483647');

-- ----------------------------
-- Table structure for `d_mark`
-- ----------------------------
DROP TABLE IF EXISTS `d_mark`;
CREATE TABLE `d_mark` (
  `enroll_num` int(11) NOT NULL,
  `subject_id` int(11) NOT NULL,
  `expert_id` int(11) NOT NULL,
  `impression_mark` int(5) DEFAULT NULL,
  `mark` int(5) DEFAULT NULL,
  `retest_en_mark` int(5) DEFAULT NULL,
  `retest_pro_mark` int(5) DEFAULT NULL,
  `retest_peo_mark` int(5) DEFAULT NULL,
  `createTime` varchar(255) DEFAULT NULL,
  `test_temp_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of d_mark
-- ----------------------------
INSERT INTO `d_mark` VALUES ('1570', '13', '1', '7', '50', '19', '36', '26', '2016-04-18 23:00:33', '3');
INSERT INTO `d_mark` VALUES ('1570', '14', '2', '8', '64', '19', '36', '26', '2016-04-19 10:27:37', '3');
INSERT INTO `d_mark` VALUES ('1571', '13', '1', '2', '40', '29', '30', '18', '2016-04-18 23:00:33', '4');
INSERT INTO `d_mark` VALUES ('1571', '14', '2', '6', '63', '29', '30', '18', '2016-04-19 10:27:37', '2');
INSERT INTO `d_mark` VALUES ('1572', '13', '1', '6', '70', '19', '9', '23', '2016-04-18 23:00:33', '1');
INSERT INTO `d_mark` VALUES ('1572', '14', '2', '9', '60', '19', '9', '23', '2016-04-19 10:27:37', '4');
INSERT INTO `d_mark` VALUES ('1573', '13', '1', '9', '55', '0', '0', '30', '2016-04-18 23:00:33', '2');
INSERT INTO `d_mark` VALUES ('1573', '14', '2', '7', '63', '0', '0', '30', '2016-04-19 10:27:37', '1');

-- ----------------------------
-- Table structure for `d_save_status`
-- ----------------------------
DROP TABLE IF EXISTS `d_save_status`;
CREATE TABLE `d_save_status` (
  `save_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `save_type_name` varchar(255) DEFAULT NULL,
  `is_complete` int(1) DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`save_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of d_save_status
-- ----------------------------
INSERT INTO `d_save_status` VALUES ('1', 'mark', '1', '2016-04-19 10:28:17');
INSERT INTO `d_save_status` VALUES ('2', 'dataSync', '0', '2016-04-18 23:32:01');
INSERT INTO `d_save_status` VALUES ('3', 'downloadQuestion', '1', '2016-04-18 23:17:41');

-- ----------------------------
-- Table structure for `d_test_teacher`
-- ----------------------------
DROP TABLE IF EXISTS `d_test_teacher`;
CREATE TABLE `d_test_teacher` (
  `teacher_id` int(11) NOT NULL,
  `test_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of d_test_teacher
-- ----------------------------
INSERT INTO `d_test_teacher` VALUES ('1', '1');

-- ----------------------------
-- Table structure for `s_admin`
-- ----------------------------
DROP TABLE IF EXISTS `s_admin`;
CREATE TABLE `s_admin` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(255) NOT NULL,
  `admin_password` varchar(255) NOT NULL,
  `idc_number` varchar(255) NOT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_admin
-- ----------------------------
INSERT INTO `s_admin` VALUES ('1', '123123123', 'password', '333333222222221111');
INSERT INTO `s_admin` VALUES ('2', 'admin', '123456', '313546151652135');

-- ----------------------------
-- Table structure for `s_class`
-- ----------------------------
DROP TABLE IF EXISTS `s_class`;
CREATE TABLE `s_class` (
  `class_id` int(11) NOT NULL AUTO_INCREMENT,
  `class_no` varchar(255) NOT NULL,
  `school_id` int(11) NOT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`class_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_class
-- ----------------------------
INSERT INTO `s_class` VALUES ('1', '文史楼303', '1', '0');
INSERT INTO `s_class` VALUES ('2', '理科楼B226', '1', '0');
INSERT INTO `s_class` VALUES ('3', '共青场主席台前跑道', '1', '0');
INSERT INTO `s_class` VALUES ('4', '共青场南沙坑', '1', '0');
INSERT INTO `s_class` VALUES ('5', '图书馆101', '2', '0');
INSERT INTO `s_class` VALUES ('9', '101', '20', '0');
INSERT INTO `s_class` VALUES ('10', '202', '20', '0');
INSERT INTO `s_class` VALUES ('11', '303', '20', '0');
INSERT INTO `s_class` VALUES ('12', '404', '20', '0');
INSERT INTO `s_class` VALUES ('14', '天上人间海天盛筵（周则宙最爱）', '20', '1');

-- ----------------------------
-- Table structure for `s_expert`
-- ----------------------------
DROP TABLE IF EXISTS `s_expert`;
CREATE TABLE `s_expert` (
  `expert_id` int(11) NOT NULL AUTO_INCREMENT,
  `expert_name` varchar(255) NOT NULL,
  `expert_idc` int(11) DEFAULT NULL,
  `subject_id` varchar(255) NOT NULL,
  `expert_phone` varchar(255) DEFAULT NULL,
  `expert_mobilephone` varchar(255) DEFAULT NULL,
  `expert_postcode` varchar(255) DEFAULT NULL,
  `expert_address` varchar(255) DEFAULT NULL,
  `expert_username` varchar(255) NOT NULL,
  `expert_password` varchar(255) NOT NULL,
  PRIMARY KEY (`expert_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_expert
-- ----------------------------
INSERT INTO `s_expert` VALUES ('1', '姜宁康', null, '13', '', '133333333333', '', '', 'jnk', '123456');
INSERT INTO `s_expert` VALUES ('2', '孙海英', null, '14', '', '144444444444', '', '', 'shy', '123456');
INSERT INTO `s_expert` VALUES ('3', '孙海英2', null, '15', '', '144444444444', '', '', 'shy2', '123456');
INSERT INTO `s_expert` VALUES ('4', '孙海英3', null, '16', '', '144444444444', '', '', 'shy3', '123456');
INSERT INTO `s_expert` VALUES ('5', '姜宁康2', null, '17', '', '133333333333', '', '', 'jnk2', '123456');
INSERT INTO `s_expert` VALUES ('6', '孙海英4', null, '18', '', '144444444444', '', '', 'shy4', '123456');
INSERT INTO `s_expert` VALUES ('7', '姜宁康3', null, '19', '', '133333333333', '', '', 'jnk3', '123456');
INSERT INTO `s_expert` VALUES ('8', '孙海英5', null, '20', '', '144444444444', '', '', 'shy5', '123456');
INSERT INTO `s_expert` VALUES ('9', '姜宁康4', null, '21', '', '133333333333', '', '', 'jnk', '123456');
INSERT INTO `s_expert` VALUES ('10', '姜宁康5', null, '22', '', '133333333333', '', '', 'jnk', '123456');

-- ----------------------------
-- Table structure for `s_question`
-- ----------------------------
DROP TABLE IF EXISTS `s_question`;
CREATE TABLE `s_question` (
  `question_id` int(11) NOT NULL AUTO_INCREMENT,
  `question_type_id` int(11) NOT NULL,
  `sub_type_id` int(11) DEFAULT NULL,
  `question_content` text,
  PRIMARY KEY (`question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_question
-- ----------------------------
INSERT INTO `s_question` VALUES ('4', '2', '5', '你是什么专业');
INSERT INTO `s_question` VALUES ('5', '2', '4', '唱歌怎么唱？');
INSERT INTO `s_question` VALUES ('6', '2', '4', '你唱的多好？');
INSERT INTO `s_question` VALUES ('13', '2', '2', '科比在2016年世界杯上一共进了多少个球？');
INSERT INTO `s_question` VALUES ('14', '1', '3', '你认为科比是不是本世纪优秀的足球运动员？');
INSERT INTO `s_question` VALUES ('17', '2', '1', '你100米跑多快？');
INSERT INTO `s_question` VALUES ('18', '2', '2', '你的护球像亨利吗？');
INSERT INTO `s_question` VALUES ('20', '1', '1', 'What is your name?');
INSERT INTO `s_question` VALUES ('21', '3', '4', 'fdefwf');
INSERT INTO `s_question` VALUES ('22', '1', '4', 'Are you awesome?');
INSERT INTO `s_question` VALUES ('23', '1', '4', 'Do you live in a house? or a flat?');
INSERT INTO `s_question` VALUES ('24', '3', '4', '你如何看待女权主义？');
INSERT INTO `s_question` VALUES ('25', '3', '4', '你如何看待哲学？');
INSERT INTO `s_question` VALUES ('26', '3', '4', '你是不是要去买泡面？');

-- ----------------------------
-- Table structure for `s_question_type`
-- ----------------------------
DROP TABLE IF EXISTS `s_question_type`;
CREATE TABLE `s_question_type` (
  `question_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `question_type_name` varchar(255) NOT NULL,
  PRIMARY KEY (`question_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_question_type
-- ----------------------------

-- ----------------------------
-- Table structure for `s_school`
-- ----------------------------
DROP TABLE IF EXISTS `s_school`;
CREATE TABLE `s_school` (
  `school_id` int(11) NOT NULL AUTO_INCREMENT,
  `school_name` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `school_address` varchar(255) NOT NULL,
  `province_name` varchar(255) NOT NULL,
  `city_name` varchar(255) NOT NULL,
  `district_name` varchar(255) NOT NULL,
  PRIMARY KEY (`school_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_school
-- ----------------------------
INSERT INTO `s_school` VALUES ('1', '华东师范大学中山北路校区', '0', '中山北路3663号', '上海', '上海', '长宁区');
INSERT INTO `s_school` VALUES ('2', '华东师范大学闵行校区', '0', '东川路500号', '上海', '上海', '闵行取');
INSERT INTO `s_school` VALUES ('20', '大连市二十四中', '0', '大连市中山区32号', '辽宁', '大连', '中山区');

-- ----------------------------
-- Table structure for `s_student`
-- ----------------------------
DROP TABLE IF EXISTS `s_student`;
CREATE TABLE `s_student` (
  `student_id` int(11) NOT NULL AUTO_INCREMENT,
  `idc_number` varchar(255) NOT NULL,
  `student_password` varchar(255) NOT NULL,
  `cee_number` varchar(255) NOT NULL,
  `art_number` varchar(255) DEFAULT NULL,
  `student_name` varchar(255) NOT NULL,
  `student_type_id` int(11) NOT NULL,
  `student_type_name` varchar(255) NOT NULL,
  `student_sex` varchar(255) NOT NULL,
  `student_nation` varchar(255) NOT NULL,
  `student_birthday` varchar(255) NOT NULL,
  `student_address` varchar(255) NOT NULL,
  `student_school` varchar(255) NOT NULL,
  `student_email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_student
-- ----------------------------
INSERT INTO `s_student` VALUES ('1', '310105199606121035', '', '98501137912', '3210212', '红星华', '1', '网上报名自荐', '男', '汉族', '1996/06/12', '上海市闵行区都市路5001号', '上海中学', 'hxh1996@hotmail.com');
INSERT INTO `s_student` VALUES ('20', '146541841651651465', '', '16498416554', '4634525', '朱力霄', '2', '现场报名自荐', '男', '汉族', '1994-07-02', '五舍426', '北郊中学', '866664864@qq.com');
INSERT INTO `s_student` VALUES ('21', '161461465161651611', '', '16451651652', '2432577', '周则宙', '2', '现场报名自荐', '男', '汉族', '1993-11-11', '五舍528', '台州公安局', '421564165@163.com');
INSERT INTO `s_student` VALUES ('22', '162161561116516161', '', '16516151611', '5436377', '周湘杰', '2', '现场报名自荐', '男', '汉族', '1994-08-25', '五舍528', '大连二十四中', '15616461@gmail.com');
INSERT INTO `s_student` VALUES ('23', '320522199401121614', '', '20160324023', '7956743', '刘阳', '1', '网上报名自荐', '男', '汉族', '1994-01-12', '华东师范大学中山北路校区第五宿舍', '湘北中学', 'yangliu_ecnusei@163.com');
INSERT INTO `s_student` VALUES ('25', '420982199105082851', '', '21415427554', '3781636', '黄洪果', '1', '网上报名自荐', '男', '汉族', '1995-03-10', '世博大道1368号4区B层', '大同中学', 'yellow_apple@126.com');
INSERT INTO `s_student` VALUES ('27', '130625197802216876', '', '65859847336', '2356732', '习帅成', '1', '网上报名自荐', '男', '汉族', '1995-03-07', '西藏北路166号', '海凌坉中学', 'iamhandsome@sina.com');
INSERT INTO `s_student` VALUES ('41', '320402199407024013', '', '315164', '', '朱力霄', '2', '现场报名自荐', '男', '汉', '34517', '常州市新北区府翰苑', '常州市北郊中学', '863716623@qq.com');
INSERT INTO `s_student` VALUES ('42', '331315199312111316', '', '356489', '', '周则宙', '2', '现场报名自荐', '男', '汉', '34315', '台州市公安局监狱101床位', '台州市少管所', '111111111@qq.com');
INSERT INTO `s_student` VALUES ('43', '313315199405051645', '', '346848', '', '周湘杰', '2', '现场报名自荐', '男', '汉', '34551', '大连市公安局监狱', '大连市二十四中', '222222222@qq.com');
INSERT INTO `s_student` VALUES ('44', '19931208', '', 'da', 'ddasdasd', 'zhaowenhao', '1', '网上报名自荐', '男', 'dsd', '2016-04-07', 'dasda', 'dasdasd', 'asd@sadsa.dasd');
INSERT INTO `s_student` VALUES ('45', '320402199407024013', '', '315164', '88797', '朱力霄', '2', '现场报名自荐', '男', '汉', '34517', '常州市新北区府翰苑', '常州市北郊中学', '863716623@qq.com');
INSERT INTO `s_student` VALUES ('46', '331315199312111316', '', '356489', '88799', '周则宙', '2', '现场报名自荐', '男', '汉', '34315', '台州市公安局监狱101床位', '台州市少管所', '111111111@qq.com');
INSERT INTO `s_student` VALUES ('47', '313315199405051645', '', '346848', '87978', '周湘杰', '2', '现场报名自荐', '男', '汉', '34551', '大连市公安局监狱', '大连市二十四中', '222222222@qq.com');
INSERT INTO `s_student` VALUES ('48', '320402199407024013', '', '315164', '88797', '朱力霄', '2', '现场报名自荐', '男', '汉', '34517', '常州市新北区府翰苑', '常州市北郊中学', '863716623@qq.com');
INSERT INTO `s_student` VALUES ('49', '331315199312111316', '', '356489', '88799', '周则宙', '2', '现场报名自荐', '男', '汉', '34315', '台州市公安局监狱101床位', '台州市少管所', '111111111@qq.com');
INSERT INTO `s_student` VALUES ('50', '313315199405051645', '', '346848', '87978', '周湘杰', '2', '现场报名自荐', '男', '汉', '34551', '大连市公安局监狱', '大连市二十四中', '222222222@qq.com');
INSERT INTO `s_student` VALUES ('51', '320402199407024013', '', '315164', '88797', '朱力霄', '2', '现场报名自荐', '男', '汉', '34517', '常州市新北区府翰苑', '常州市北郊中学', '863716623@qq.com');
INSERT INTO `s_student` VALUES ('52', '331315199312111316', '', '356489', '88799', '周则宙', '2', '现场报名自荐', '男', '汉', '34315', '台州市公安局监狱101床位', '台州市少管所', '111111111@qq.com');
INSERT INTO `s_student` VALUES ('53', '313315199405051645', '', '346848', '87978', '周湘杰', '2', '现场报名自荐', '男', '汉', '34551', '大连市公安局监狱', '大连市二十四中', '222222222@qq.com');
INSERT INTO `s_student` VALUES ('54', '19931208', '', 'dasasd', 'asd', 'zhaowenhao', '1', '网上报名自荐', '男', 'ads', '2016-04-28', 'asda', 'dasd', 'ads2asdsad@sad.asd');

-- ----------------------------
-- Table structure for `s_student_type`
-- ----------------------------
DROP TABLE IF EXISTS `s_student_type`;
CREATE TABLE `s_student_type` (
  `student_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_type_name` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`student_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_student_type
-- ----------------------------
INSERT INTO `s_student_type` VALUES ('1', '网上报名自荐', '0');
INSERT INTO `s_student_type` VALUES ('2', '现场报名自荐', '0');
INSERT INTO `s_student_type` VALUES ('3', '校荐', '0');

-- ----------------------------
-- Table structure for `s_sub_type`
-- ----------------------------
DROP TABLE IF EXISTS `s_sub_type`;
CREATE TABLE `s_sub_type` (
  `sub_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `sub_type_name` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `type_id` int(11) NOT NULL,
  PRIMARY KEY (`sub_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_sub_type
-- ----------------------------
INSERT INTO `s_sub_type` VALUES ('1', '田径', '0', '2');
INSERT INTO `s_sub_type` VALUES ('2', '足球', '0', '2');
INSERT INTO `s_sub_type` VALUES ('3', '篮球', '0', '2');
INSERT INTO `s_sub_type` VALUES ('4', '声乐', '0', '1');
INSERT INTO `s_sub_type` VALUES ('5', '舞蹈', '0', '1');
INSERT INTO `s_sub_type` VALUES ('6', '绘画', '0', '1');
INSERT INTO `s_sub_type` VALUES ('7', '发明创造', '0', '3');
INSERT INTO `s_sub_type` VALUES ('8', '信息技术', '0', '3');
INSERT INTO `s_sub_type` VALUES ('13', '四驱车', '0', '2');

-- ----------------------------
-- Table structure for `s_subject`
-- ----------------------------
DROP TABLE IF EXISTS `s_subject`;
CREATE TABLE `s_subject` (
  `subject_id` int(11) NOT NULL AUTO_INCREMENT,
  `subject_name` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `sub_type_id` int(11) NOT NULL,
  `subject_max_diff` int(11) DEFAULT NULL,
  PRIMARY KEY (`subject_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_subject
-- ----------------------------
INSERT INTO `s_subject` VALUES ('1', '100米', '0', '1', null);
INSERT INTO `s_subject` VALUES ('2', '800米', '0', '1', null);
INSERT INTO `s_subject` VALUES ('3', '推铅球', '0', '1', null);
INSERT INTO `s_subject` VALUES ('4', '立定跳远', '0', '1', null);
INSERT INTO `s_subject` VALUES ('5', '过杆射门', '0', '2', null);
INSERT INTO `s_subject` VALUES ('6', '踢准', '0', '2', null);
INSERT INTO `s_subject` VALUES ('7', '折返跑', '0', '2', null);
INSERT INTO `s_subject` VALUES ('8', '比赛', '0', '2', null);
INSERT INTO `s_subject` VALUES ('9', '助跑摸高', '0', '3', null);
INSERT INTO `s_subject` VALUES ('10', '投篮', '0', '3', null);
INSERT INTO `s_subject` VALUES ('11', '多向运球上篮', '0', '3', null);
INSERT INTO `s_subject` VALUES ('12', '比赛', '0', '3', null);
INSERT INTO `s_subject` VALUES ('13', '演唱', '0', '4', '30');
INSERT INTO `s_subject` VALUES ('14', '听音', '0', '4', '35');
INSERT INTO `s_subject` VALUES ('17', '身体基本形态', '0', '5', '30');
INSERT INTO `s_subject` VALUES ('18', '成品舞蹈', '0', '5', '35');
INSERT INTO `s_subject` VALUES ('19', '软开度与基本功', '0', '5', '35');
INSERT INTO `s_subject` VALUES ('20', '素描', '0', '6', '30');
INSERT INTO `s_subject` VALUES ('21', '速写', '0', '6', '25');
INSERT INTO `s_subject` VALUES ('22', '色彩', '0', '6', '30');
INSERT INTO `s_subject` VALUES ('23', '科技常识测试', '0', '7', null);
INSERT INTO `s_subject` VALUES ('24', '项目展示', '0', '7', null);
INSERT INTO `s_subject` VALUES ('25', '理论测试', '0', '8', null);
INSERT INTO `s_subject` VALUES ('26', '上机测试', '0', '8', null);
INSERT INTO `s_subject` VALUES ('27', '呵呵', '0', '1', null);

-- ----------------------------
-- Table structure for `s_teacher`
-- ----------------------------
DROP TABLE IF EXISTS `s_teacher`;
CREATE TABLE `s_teacher` (
  `teacher_id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_name` varchar(255) NOT NULL,
  `teacher_password` varchar(255) NOT NULL,
  `idc_number` varchar(255) NOT NULL,
  PRIMARY KEY (`teacher_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_teacher
-- ----------------------------
INSERT INTO `s_teacher` VALUES ('1', 'ncsun', 'password', '1111112222222223333');

-- ----------------------------
-- Table structure for `s_test`
-- ----------------------------
DROP TABLE IF EXISTS `s_test`;
CREATE TABLE `s_test` (
  `test_id` int(11) NOT NULL AUTO_INCREMENT,
  `sub_type_id` int(11) NOT NULL,
  `test_name` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`test_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_test
-- ----------------------------
INSERT INTO `s_test` VALUES ('1', '1', '2015年度春季自主招生体育特长生田径考试', '0');
INSERT INTO `s_test` VALUES ('2', '2', '2015年度春季自主招生体育特长生足球考试', '0');
INSERT INTO `s_test` VALUES ('3', '3', '2015年度春季自主招生体育特长生篮球考试', '0');
INSERT INTO `s_test` VALUES ('4', '4', '2015年度春季自主招生艺术特长生声乐考试', '0');
INSERT INTO `s_test` VALUES ('5', '5', '2015年度春季自主招生艺术特长生舞蹈考试', '0');
INSERT INTO `s_test` VALUES ('6', '6', '2015年度春季自主招生艺术特长生绘画考试', '0');
INSERT INTO `s_test` VALUES ('7', '7', '2015年度春季自主招生科技特长生发明创造考试', '0');
INSERT INTO `s_test` VALUES ('8', '8', '2015年度春季自主招生科技特长生信息技术考试', '0');

-- ----------------------------
-- Table structure for `s_test_school`
-- ----------------------------
DROP TABLE IF EXISTS `s_test_school`;
CREATE TABLE `s_test_school` (
  `test_id` int(11) NOT NULL,
  `school_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_test_school
-- ----------------------------
INSERT INTO `s_test_school` VALUES ('1', '2');
INSERT INTO `s_test_school` VALUES ('1', '1');
INSERT INTO `s_test_school` VALUES ('2', '1');
INSERT INTO `s_test_school` VALUES ('2', '2');
INSERT INTO `s_test_school` VALUES ('4', '20');

-- ----------------------------
-- Table structure for `s_test_school_subject_class`
-- ----------------------------
DROP TABLE IF EXISTS `s_test_school_subject_class`;
CREATE TABLE `s_test_school_subject_class` (
  `test_id` int(11) NOT NULL,
  `school_id` int(11) NOT NULL,
  `subject_id` int(11) NOT NULL,
  `class_id` int(11) NOT NULL,
  `Date` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_test_school_subject_class
-- ----------------------------
INSERT INTO `s_test_school_subject_class` VALUES ('4', '20', '13', '9', '2015-2-2');
INSERT INTO `s_test_school_subject_class` VALUES ('4', '20', '14', '10', '2015-2-2');

-- ----------------------------
-- Table structure for `s_type`
-- ----------------------------
DROP TABLE IF EXISTS `s_type`;
CREATE TABLE `s_type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of s_type
-- ----------------------------
INSERT INTO `s_type` VALUES ('1', '艺术', '0');
INSERT INTO `s_type` VALUES ('2', '体育', '0');
INSERT INTO `s_type` VALUES ('3', '科技', '0');

-- ----------------------------
-- Table structure for `t_test_school_retest`
-- ----------------------------
DROP TABLE IF EXISTS `t_test_school_retest`;
CREATE TABLE `t_test_school_retest` (
  `test_id` int(11) DEFAULT NULL,
  `school_id` int(11) DEFAULT NULL,
  `retest_classroom_id` int(11) DEFAULT NULL,
  `start_time` varchar(255) DEFAULT NULL,
  `end_time` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_test_school_retest
-- ----------------------------
INSERT INTO `t_test_school_retest` VALUES ('4', '20', '9', '2016-5-5', '2016-5-6');
