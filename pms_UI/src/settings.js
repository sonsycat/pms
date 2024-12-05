module.exports = {
  /**
   * 侧边栏主题 深色主题theme-dark，浅色主题theme-light
   */
  sideTheme: 'theme-dark',

  /**
   * 是否系统布局配置
   */
  showSettings: false,

  /**
   * 是否显示顶部导航
   */
  topNav: true,

  /**
   * 是否显示 tagsView
   */
  tagsView: true,

  /**
   * 是否固定头部
   */
  fixedHeader: true,

  /**
   * 是否显示logo
   */
  sidebarLogo: true,

  /**
   * 是否显示动态标题
   */
  dynamicTitle: false,
  /**
   * 是否显示动态表格边框
   */
  sysTabeleBorder: true,
  /**
   * table 每页显示条数
   */
  pageSize: 30,
  /**
   * pop table 每页显示条数
   */
  popPageSize:10,
  /** 表格高度 ***/
   tableHeight: 18 + 45 + 84 + 40 + 10 +40,

  /**
   * @type {string | array} 'production' | ['production', 'development']
   * @description Need show err logs component.
   * The default is only used in the production env
   * If you want to also use it in dev, you can pass ['production', 'development']
   */
  errorLog: 'production',
  // casloginUrl: 'http://192.168.0.151:8082/ssoserver_hrp/login?service=http://192.168.5.222:5863/ssologin',
  // caslogoutUrl: 'http://192.168.0.151:8082/ssoserver_hrp/logout?service=http://192.168.5.222:5863/ssologin',
  // processUrl:"http://192.168.0.151:5555/process",
  // // 导航栏消息提醒 配置
  // sysMessageWSUrl:""
}
