DROP TABLE IF EXISTS `cell`;
CREATE TABLE IF NOT EXISTS `cell` (
  `num` int(3) NOT NULL,
  `row` int(3) NOT NULL,
  `col` int(3) NOT NULL,
  `state` int(1) NOT NULL,
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `config`;
CREATE TABLE IF NOT EXISTS `config` (
  `num` int(3) NOT NULL,
  `delay` int(3) NOT NULL,
  `maxtime` int(3) NOT NULL,
  `width` int(3) NOT NULL,
  `height` int(3) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;