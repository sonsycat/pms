
const req = require.context('../../assets/icons/svg', false, /\.svg$/)
const requireAll = requireContext => requireContext.keys()

const re = /\.\/(.*)\.svg/

const icons = requireAll(req).map(i => {
  //i = i.lastIndexOf("/")>1?'.'+i.substring(i.lastIndexOf("/")):i;//子目录下路径处理
  return i.match(re)[1]
})

export default icons
