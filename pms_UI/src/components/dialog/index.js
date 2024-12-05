import ElThemeDialog from './src/component';

/* istanbul ignore next */
ElThemeDialog.install = function(Vue) {
  Vue.component(ElThemeDialog.name, ElThemeDialog);
};

export default ElThemeDialog;
