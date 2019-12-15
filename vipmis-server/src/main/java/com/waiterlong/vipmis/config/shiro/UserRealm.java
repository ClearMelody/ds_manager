package com.waiterlong.vipmis.config.shiro;

import com.waiterlong.vipmis.domain.vo.SysUserVo;
import com.waiterlong.vipmis.service.ILoginService;
import com.waiterlong.vipmis.utils.Constants;
import com.waiterlong.vipmis.utils.Md5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2019/12/11
 * \*
 * \* Description
 *
 * @author wyk
 */
public class UserRealm extends AuthorizingRealm {
	private Logger logger = LoggerFactory.getLogger(UserRealm.class);

	@Autowired
	private ILoginService loginService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Session session = SecurityUtils.getSubject().getSession();
		//查询用户的权限
		Set<String> permissions = (Set<String>) session.getAttribute(Constants.SESSION_USER_PERMISSION);
		logger.info("本用户权限为:" + permissions);
		//为当前用户设置角色和权限
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.addStringPermissions(permissions);
		return authorizationInfo;
	}

	/**
	 * 验证当前登录的Subject
	 * LoginController.login()方法中执行Subject.login()时 执行此方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		String userName = (String) authcToken.getPrincipal();
		String password = new String((char[]) authcToken.getCredentials());
		SysUserVo user = loginService.getUser(userName);
		if (user == null) {
			throw new UnknownAccountException("用户名不存在");
		}
		if (!user.getPassword().equals(Md5Utils.encryptToMD5(userName, password))) {
			throw new IncorrectCredentialsException("密码错误");
		}
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				user,
				user.getPassword(),
				ByteSource.Util.bytes(user.getName()),
				getName()
		);
		user.setPassword(null);
		SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_USER_INFO, user);
		return authenticationInfo;
	}
}
